package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.PacienteSummaryRequest;
import br.senai.lab365.LABMedical.dtos.prontuario.ProntuarioResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.SummaryResponsePagination;
import br.senai.lab365.LABMedical.entities.Consulta;
import br.senai.lab365.LABMedical.entities.Exame;
import br.senai.lab365.LABMedical.entities.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProntuarioMapper {

    public PacienteSummaryRequest getRequestToResponse(Paciente paciente) {
        PacienteSummaryRequest response = new PacienteSummaryRequest();
        response.setIdPaciente(paciente.getId());
        response.setNome(paciente.getNome());
        response.setConvenio(paciente.getConvenio());
        return response;
    }

    public ProntuarioResponse getPacienteToProntuario(
            Paciente paciente,
            List<Exame> exames,
            List<Consulta> consultas
    ) {
        ProntuarioResponse response = new ProntuarioResponse();
        response.setId(paciente.getId());
        response.setNome(paciente.getNome());
        response.setConvenio(paciente.getConvenio());
        response.setContatoEmergencia(paciente.getContatoEmergencia());
        response.setListaAlergias(paciente.getListaAlergias());
        response.setListaCuidados(paciente.getListaCuidados());

        response.setExames(mapExamesToResponse(exames));
        response.setConsultas(mapConsultasToResponse(consultas));

        return response;
    }

    public SummaryResponsePagination mapToSummaryResponsePagination(
            Page<Paciente> paginaPacientes,
            List<PacienteSummaryRequest> conteudo,
            int numeroPagina,
            int tamanhoPagina
    ) {
        SummaryResponsePagination pagination = new SummaryResponsePagination();
        pagination.setConteudo(conteudo);
        pagination.setTotalPaginas(paginaPacientes.getTotalPages());
        pagination.setTamanhoPagina(tamanhoPagina);
        pagination.setPaginaAtual(numeroPagina);
        pagination.setTotalElementos((int) paginaPacientes.getTotalElements());
        pagination.setUltima(paginaPacientes.isLast());
        return pagination;
    }

    public List<ExameResponse> examesToResponse(List<Exame> exames) {
        return mapExamesToResponse(exames);
    }

    public List<ConsultaResponse> consultasToResponse(List<Consulta> consultas) {
        return mapConsultasToResponse(consultas);
    }

    private List<ExameResponse> mapExamesToResponse(List<Exame> exames) {
        return exames.stream()
                .map(exame -> new ExameResponse(
                        exame.getId(),
                        exame.getNomeExame(),
                        exame.getDataExame(),
                        exame.getHorarioExame(),
                        exame.getTipoExame(),
                        exame.getLaboratorio(),
                        exame.getUrlDocumento(),
                        exame.getResultados(),
                        exame.getPaciente().getId()
                ))
                .collect(Collectors.toList());
    }

    private List<ConsultaResponse> mapConsultasToResponse(List<Consulta> consultas) {
        return consultas.stream()
                .map(consulta -> new ConsultaResponse(
                        consulta.getId(),
                        consulta.getMotivo(),
                        consulta.getDataConsulta(),
                        consulta.getHorarioConsulta(),
                        consulta.getDescricaoProblema(),
                        consulta.getMedicacaoReceitada(),
                        consulta.getDosagemPrecaucoes(),
                        consulta.getPaciente().getId()
                ))
                .collect(Collectors.toList());
    }
}
