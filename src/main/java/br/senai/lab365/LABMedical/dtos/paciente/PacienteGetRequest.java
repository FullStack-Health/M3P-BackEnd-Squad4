package br.senai.lab365.LABMedical.dtos.paciente;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PacienteGetRequest {

    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private Integer idade;
    private String convenio;
    private LocalDate dataNascimento;
    private String cpf;
    private String listaAlergias;
    private String listaCuidados;
    private String contatoEmergencia;

}
