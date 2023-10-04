package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;

// Agendamento de consultas
// Service -> Executa as regras de negócio e validações da aplicação
@Service
public class AgendaDeConsultas {

  @Autowired
  private ConsultaRepository consultaRepository;

  @Autowired
  private PacienteRepository pacienteRepository;

  @Autowired
  private MedicoRepository medicoRepository;

  @Autowired
  private List<ValidadorAgendamentoDeConsulta> validadores;

  @Autowired
  private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

  public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
    // Regras de negócio
    // Verifica no DB a existência do ID
    if (!pacienteRepository.existsById(dados.idPaciente())) {
      throw new ValidacaoException("Id do paciente informado não existe");
    }

    // Se o ID do médico não estiver vindo como null no DB, ele retorna o ID
    // Se não ele não retorna
    if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
      throw new ValidacaoException("Id do medico informado não existe");
    }

    // Percorre a lista um por um
    validadores.forEach(v-> v.validar(dados));

    // Carrega o ID pelo DB
    // get() -> pegar o objeto que foi caerregado
    var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
    var medico = escolherMedico(dados);

    if (medico == null) {
      throw new ValidacaoException("Não existe médico disponível nessa data!");
    }

    var consulta = new Consulta(null, medico, paciente, dados.data(), null);

    consultaRepository.save(consulta);

    return new DadosDetalhamentoConsulta(consulta);
  }

  // Método para caso um médico não seja colocado no agendamento
  // Ele retorna um médico aleatório
  private Medico escolherMedico(DadosAgendamentoConsulta dados) {
    if (dados.idMedico() != null) {
      return medicoRepository.getReferenceById(dados.idMedico());
    }

    if (dados.especialidade() == null) {
      throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido");
    }

    return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
  }

  public void cancelar(@Valid DadosCancelamentoConsulta dados) {
    if (!consultaRepository.existsById(dados.idConsulta())) {
      throw new ValidacaoException("ID da consulta informado não existe");
    }

    validadoresCancelamento.forEach(v -> v.validar(dados));

    var consulta = consultaRepository.getReferenceById(dados.idConsulta());
    consulta.cancelar(dados.motivo());
  }

}
