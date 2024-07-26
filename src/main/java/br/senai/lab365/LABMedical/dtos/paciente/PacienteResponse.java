package br.senai.lab365.LABMedical.dtos.paciente;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PacienteResponse {

    private Long id;
    private String nome;
    private String genero;
    private LocalDate dataNascimento;
    private String cpf;
    private String rg;
    private String orgaoExpedidor;
    private String estadoCivil;
    private String telefone;
    private String email;
    private String naturalidade;
    private String contatoEmergencia;
    private String listaAlergias;
    private String listaCuidados;
    private String convenio;
    private String numeroConvenio;
    private LocalDate validadeConvenio;
    private EnderecoResponse endereco;
    private String id_usuario;


}
