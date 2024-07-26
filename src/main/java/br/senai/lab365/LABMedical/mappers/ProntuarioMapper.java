package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.paciente.PacienteSummaryRequest;
import br.senai.lab365.LABMedical.entities.Paciente;
import org.springframework.stereotype.Component;

@Component
public class ProntuarioMapper {

    public PacienteSummaryRequest getRequestToResponse(Paciente paciente) {
        PacienteSummaryRequest response = new PacienteSummaryRequest();
        response.setIdPaciente(paciente.getId());
        response.setNome(paciente.getNome());
        response.setConvenio(paciente.getConvenio());
        return response;
    }
}
