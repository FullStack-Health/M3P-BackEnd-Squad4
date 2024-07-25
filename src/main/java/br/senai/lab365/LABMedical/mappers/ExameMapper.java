package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.exame.ExameRequest;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.entities.Exame;
import br.senai.lab365.LABMedical.entities.Paciente;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ExameMapper {


    public Exame toEntity(ExameRequest request, Paciente paciente) {
        if (request == null || paciente == null) {
            return null;
        }
        return new Exame(
                null,
                request.getNomeExame(),
                request.getDataExame(),
                request.getHorarioExame(),
                request.getTipoExame(),
                request.getLaboratorio(),
                request.getUrlDocumento(),
                request.getResultados(),
                paciente
        );
    }

    public ExameResponse toResponse(Exame exame) {
        if (exame == null) {
            return null;
        }
        return new ExameResponse(
                exame.getId(),
                exame.getNomeExame(),
                exame.getDataExame(),
                exame.getHorarioExame(),
                exame.getTipoExame(),
                exame.getLaboratorio(),
                exame.getUrlDocumento(),
                exame.getResultados(),
                exame.getPaciente().getId()
        );
    }
}
