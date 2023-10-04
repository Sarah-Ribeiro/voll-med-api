package med.voll.api.infra.exception;

import java.nio.file.AccessDeniedException;

import javax.security.sasl.AuthenticationException;

import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.*;
import med.voll.api.domain.consulta.ValidacaoException;

// Indica que é uma classe para tratamento de erros
@RestControllerAdvice
public class TratadorDeErros {

	// Indica em que momento que esse tratemento de erro precisa ser usado
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> tratarErro404() {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException ex) {
		var erros = ex.getFieldErrors();

		// Conversão de uma lista para outra
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> tratarErroBadCredencials() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> tratarErroAuthentication() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autentificação");
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> tratarErroAcessoNegado() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negago");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> tratarErro500(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.getLocalizedMessage());
	}

	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<?> tratarErroRegraDeNegocio(ValidacaoException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	private record DadosErroValidacao(String campo, String mensagem) {
		public DadosErroValidacao(FieldError erro) {
			// Construtor
			this(erro.getField(), erro.getDefaultMessage());
		}
	}

}
