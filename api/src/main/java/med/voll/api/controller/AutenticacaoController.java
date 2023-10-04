package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuthenticationManager manager;
	
	@PostMapping
	public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
//		DTO do Spring 
		var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var authentication = manager.authenticate(authenticationToken);

		var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
		
		// Criação de um DTO Token
		return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
	}
	
}
