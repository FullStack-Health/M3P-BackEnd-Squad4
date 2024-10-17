package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaRequest;
import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import br.senai.lab365.LABMedical.entities.Consulta;
import br.senai.lab365.LABMedical.entities.Paciente;
import org.springframework.stereotype.Component;

@Component
public class ConsultaMapper {

    public Consulta toEntity(ConsultaRequest consultaRequest, Paciente paciente) {
        if (consultaRequest == null || paciente == null) {
            return null;
        }
        return new Consulta(
                null,
                consultaRequest.getMotivo(),
                consultaRequest.getDataConsulta(),
                consultaRequest.getHorarioConsulta(),
                consultaRequest.getDescricaoProblema(),
                consultaRequest.getMedicacaoReceitada(),
                consultaRequest.getDosagemPrecaucoes(),
                paciente
        );
    }

    public ConsultaResponse toResponse(Consulta consulta) {
        if (consulta == null) {
            return null;
        }

        return new ConsultaResponse(
                consulta.getId(),
                consulta.getMotivo(),
                consulta.getDataConsulta(),
                consulta.getHorarioConsulta(),
                consulta.getDescricaoProblema(),
                consulta.getMedicacaoReceitada(),
                consulta.getDosagemPrecaucoes(),
                consulta.getPaciente().getId()
        );
    }

    public void atualizaConsultaDesdeRequest(Consulta consulta, ConsultaRequest request, Paciente paciente) {
        if (request.getMotivo() != null) consulta.setMotivo(request.getMotivo());
        if (request.getDataConsulta() != null) consulta.setDataConsulta(request.getDataConsulta());
        if (request.getHorarioConsulta() != null) consulta.setHorarioConsulta(request.getHorarioConsulta());
        if (request.getDescricaoProblema() != null) consulta.setDescricaoProblema(request.getDescricaoProblema());
        if (request.getMedicacaoReceitada() != null) consulta.setMedicacaoReceitada(request.getMedicacaoReceitada());
        if (request.getDosagemPrecaucoes() != null) consulta.setDosagemPrecaucoes(request.getDosagemPrecaucoes());
        if (request.getIdPaciente() != null) paciente.getId();
    }
}
