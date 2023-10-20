package med.voll.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

  @Autowired
  // Simula requisições usando o padrão MVC
  private MockMvc mvc;

  // JSON que chega na API
  @Autowired
  private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

  // JSON que a API devolve
  @Autowired
  private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

  @MockBean
  private AgendaDeConsultas agendaDeConsultas;

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

  @Test
  @DisplayName("Deveria devolver código HTTP 200 quando informações estão válidas")
  @WithMockUser
  void agendar_cenario2() throws Exception {
    var data = LocalDateTime.now().plusHours(1);
    var especialidade = Especialidade.CARDIOLOGIA;

    var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);

    when(agendaDeConsultas.agendar(any()))
        .thenReturn(dadosDetalhamento);

    var response = mvc.perform(
        post("/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(dadosAgendamentoConsultaJson.write(
                new DadosAgendamentoConsulta(2l, 5l, data, especialidade))
                // Converte para JSON
                .getJson()))
        .andReturn().getResponse();

    // Retorna código 200
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    var jsonEsperado = dadosDetalhamentoConsultaJson.write(
        dadosDetalhamento).getJson();

    assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
  }

}
