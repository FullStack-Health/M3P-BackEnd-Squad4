package br.senai.lab365.LABMedical.dtos.paciente;

import br.senai.lab365.LABMedical.dtos.usuario.UsuarioRequest;
import br.senai.lab365.LABMedical.validations.CpfUnico;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@CpfUnico // Aplica a validação personalizada de CPF único no objeto inteiro
public class PacienteRequest {

    @JsonProperty("id")
    private Long id; // Campo necessário para atualização no PUT

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 8, max = 64, message = "Nome deve ter no mínimo 8 e no máximo 64 caracteres")
    @JsonProperty("nome")
    private String nome;

    @NotNull(message = "O gênero é obrigatório")
    @JsonProperty("genero")
    private String genero;

    @NotNull(message = "Data de nascimento é obrigatória. Enviar no formato yyyy-MM-dd")
    @Past(message = "Data de nascimento deve estar no passado")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("dataNascimento")
    private LocalDate dataNascimento;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato 000.000.000-00")
    @CPF(message = "CPF inválido")
    @JsonProperty("cpf")
    private String cpf;

    @NotBlank(message = "RG é obrigatório")
    @Size(max = 20, message = "RG deve ter no máximo 20 dígitos")
    @JsonProperty("rg")
    private String rg;

    @NotBlank(message = "Órgão expedidor é obrigatório")
    @JsonProperty("orgaoExpedidor")
    private String orgaoExpedidor;

    @NotNull(message = "Estado civil é obrigatório")
    @JsonProperty("estadoCivil")
    private String estadoCivil;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\(\\d{2}\\)\\d{4,5}-\\d{4}", message = "Telefone inválido. Enviar no formato (00)00000-0000 ou (00)0000-0000")
    @JsonProperty("telefone")
    private String telefone;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Naturalidade é obrigatória")
    @Size(min = 3, max = 64, message = "Naturalidade deve ter no mínimo 3 e no máximo 64 caracteres")
    @JsonProperty("naturalidade")
    private String naturalidade;

    @NotBlank(message = "Contato de emergência é obrigatório")
    @Pattern(regexp = "\\(\\d{2}\\)\\d{4,5}-\\d{4}", message = "Telefone inválido. Enviar no formato (00)00000-0000 ou (00)0000-0000")
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

    @Valid
    @JsonProperty("endereco")
    private EnderecoRequest endereco;

    @Valid
    @JsonProperty("usuario")
    private UsuarioRequest usuario;
}
