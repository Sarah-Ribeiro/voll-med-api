package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;

// Classe genérica
@Component
// Herdando uma classe spring
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UsuarioRepository repository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    System.out.println("Chamando Filter");
    // Vai ser executada apenas uma vez para cada requisição
    // Necessário para chamar os próximas requisições da aplicação
    var tokenJWT = recuperarToken(request);

    // Se tiver o cabeçalho faz a validação do Token
    if (tokenJWT != null) {
      var subject = tokenService.getSubject(tokenJWT);
      // Recuperação do objeto usuário
      var usuario = repository.findByLogin(subject);
      // Dto do spring
      var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
      // Considerar o usuário está logado
      SecurityContextHolder.getContext().setAuthentication(authentication);
      System.out.println("Logado na requisição");
    }

    filterChain.doFilter(request, response);
  }

  // Recuperação do Token
  private String recuperarToken(HttpServletRequest request) {
    var authorizationHeader = request.getHeader("Authorization");

    // Se não tiver cabeçalho
    if (authorizationHeader != null) {
      // O Spring retorna o token sem o prefixo
      return authorizationHeader.replace("Bearer ", "");
    }

    return null;

  }

}
