package br.senai.lab365.LABMedical.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteGetRequest {

    private String nome;
    private String telefone;
    private String email;
    private Integer idade;
    private String convenio;

}
