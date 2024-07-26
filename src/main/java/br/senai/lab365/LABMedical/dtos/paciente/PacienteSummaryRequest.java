package br.senai.lab365.LABMedical.dtos.paciente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteSummaryRequest {

    private Long idPaciente;
    private String nome;
    private String convenio;

}
