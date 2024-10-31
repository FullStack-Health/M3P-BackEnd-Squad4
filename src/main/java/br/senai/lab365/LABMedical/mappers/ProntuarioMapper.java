package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.PacienteSummaryRequest;
import br.senai.lab365.LABMedical.dtos.prontuario.ProntuarioResponse;
import br.senai.lab365.LABMedical.entities.Consulta;
import br.senai.lab365.LABMedical.entities.Exame;
import br.senai.lab365.LABMedical.entities.Paciente;
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

                List<ExameResponse> exameResponse = exames.stream()
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
                                )
                            )
                        .collect(Collectors.toList());
                response.setExames(exameResponse);

        List<ConsultaResponse> consultaResponse = consultas.stream()
                .map(consulta -> new ConsultaResponse(
                        consulta.getId(),
                        consulta.getMotivo(),
                        consulta.getDataConsulta(),
                        consulta.getHorarioConsulta(),
                        consulta.getDescricaoProblema(),
                        consulta.getMedicacaoReceitada(),
                        consulta.getDosagemPrecaucoes(),
                        consulta.getPaciente().getId()
                        )
                    )
                .collect(Collectors.toList());
        response.setConsultas(consultaResponse);

        return response;

    }
}
