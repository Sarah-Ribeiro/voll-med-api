package med.voll.api.domain.medico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

// Testa um interface repository
@DataJpaTest
// Não subtitui as config do DB
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

  @Autowired
  private MedicoRepository medicoRepository;

  @Autowired
  private TestEntityManager em;

  @Test
  // Descrever cenário de teste
  @DisplayName("Deveria devolver Null quando o único médico cadastrado não está disponível na data")
  void escolherMedicoAleatorioLivreNaDataCenario1() {
    
    // 3 etapas do teste
    // Given ou Arrange
    var proximaSegundaAs10 = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        .atTime(10, 0);

    var medico = cadastrarMedico("Médico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
    var paciente = cadastrarPaciente("Paciente", "paciete@voll.med", "000-000-000.00");
    cadastrarConsulta(medico, paciente, proximaSegundaAs10);

    // When ou Act
    var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,
        proximaSegundaAs10);

    // Then ou Assert
    assertThat(medicoLivre).isNull();
  }

  @Test
  // Descrever cenário de teste
  @DisplayName("Deveria devolver médico quando ele estiver disponível na data")
  void escolherMedicoAleatorioLivreNaDataCenario2() {
    var proximaSegundaAs10 = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        .atTime(10, 0);

    var medico = cadastrarMedico("Médico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

    var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,
        proximaSegundaAs10);
    assertThat(medicoLivre).isEqualTo(medico);
  }

  private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
    em.persist(new Consulta(null, medico, paciente, data, null));
  }

  private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
    var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
    em.persist(medico);
    return medico;
  }

  private Paciente cadastrarPaciente(String nome, String email, String cpf) {
    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
    em.persist(paciente);
    return paciente;
  }

  private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
    return new DadosCadastroMedico(
        nome,
        email,
        "61999999999",
        crm,
        especialidade,
        dadosEndereco());
  }

  private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
    return new DadosCadastroPaciente(
        nome,
        email,
        "61999999999",
        cpf,
        dadosEndereco());
  }

  private DadosEndereco dadosEndereco() {
    return new DadosEndereco(
        "rua xpto",
        "bairro",
        "00000000",
        "Brasilia",
        "DF",
        null,
        null);
  }
}