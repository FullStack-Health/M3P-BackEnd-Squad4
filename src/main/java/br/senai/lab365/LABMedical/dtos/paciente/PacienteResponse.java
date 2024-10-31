package br.senai.lab365.LABMedical.dtos.paciente;

import br.senai.lab365.LABMedical.dtos.usuario.UsuarioResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PacienteResponse {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("genero")
    private String genero;
    @JsonProperty("dataNascimento")
    private LocalDate dataNascimento;
    @JsonProperty("cpf")
    private String cpf;
    @JsonProperty("rg")
    private String rg;
    @JsonProperty("orgaoExpedidor")
    private String orgaoExpedidor;
    @JsonProperty("estadoCivil")
    private String estadoCivil;
    @JsonProperty("telefone")
    private String telefone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("naturalidade")
    private String naturalidade;
    @JsonProperty("contatoEmergencia")
    private String contatoEmergencia;
    @JsonProperty("listaAlergias")
    private String listaAlergias;
    @JsonProperty("listaCuidados")
    private String listaCuidados;
    @JsonProperty("convenio")
    private String convenio;
    @JsonProperty("numeroConvenio")
    private String numeroConvenio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("validadeConvenio")
    private LocalDate validadeConvenio;
    @JsonProperty("endereco")
    private EnderecoResponse endereco;
    @JsonProperty("usuario")
    private UsuarioResponse usuario;
}