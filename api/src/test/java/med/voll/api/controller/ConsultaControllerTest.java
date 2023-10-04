package med.voll.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class ConsultaControllerTest {

  @Autowired
  // Simula requisições usando o padrão MVC
  private MockMvc mvc;

  // O controller não é injetado

  @Test
  @DisplayName("Deveria devolver código HTTP 400 quando informações estão inválidas")
  // Ignorar o Spring Security para que não haja a chamada do token na hora da
  // requisição
  @WithMockUser
  // Simular requisição
  void agendar_cenario1() throws Exception {
    // Testar controller de maneira unitária

    // Perfoma uma requisição na API
    // Dispara um requisição para o endereço sem levar o Body
    var response = mvc.perform(post("/consultas"))
            .andReturn().getResponse();

    // Verifica se retorna o erro 400
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

  }

}
