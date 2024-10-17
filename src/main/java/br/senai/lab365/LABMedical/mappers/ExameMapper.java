package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.exame.ExameRequest;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.entities.Exame;
import br.senai.lab365.LABMedical.entities.Paciente;
import org.springframework.stereotype.Component;

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

    public void atualizaExameDesdeRequest(Exame exame, ExameRequest request, Paciente paciente) {
        if (request.getNomeExame() != null) exame.setNomeExame(request.getNomeExame());
        if (request.getDataExame() != null) exame.setDataExame(request.getDataExame());
        if (request.getHorarioExame() != null) exame.setHorarioExame(request.getHorarioExame());
        if (request.getTipoExame() != null) exame.setTipoExame(request.getTipoExame());
        if (request.getLaboratorio() != null) exame.setLaboratorio(request.getLaboratorio());
        if (request.getUrlDocumento() != null) exame.setUrlDocumento(request.getUrlDocumento());
        if (request.getResultados() != null) exame.setResultados(request.getResultados());
        if (request.getIdPaciente() != null) paciente.getId();
    }

}
