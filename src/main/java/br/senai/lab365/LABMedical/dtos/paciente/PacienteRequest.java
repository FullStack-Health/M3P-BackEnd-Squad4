package br.senai.lab365.LABMedical.dtos.paciente;


import br.senai.lab365.LABMedical.dtos.usuario.UsuarioRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PacienteRequest {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 8, max = 64, message = "Nome deve ter no mínimo 8 e no máximo 64 caracteres")
    private String nome;

    @NotNull(message = "O gênero é obrigatório")
    private String genero;

    @NotNull(message = "Data de nascimento é obrigatória. Enviar no formato yyyy-MM-dd")
    @Past(message = "Data de nascimento deve estar no passado")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @NotBlank(message = "CPF é obrigatório")
    @CPF(message = "CPF inválido. Enviar no formato 000.000.000-00")
    @Column(nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "RG é obrigatório")
    @Size(max = 20, message = "RG deve ter no máximo 20 dígitos")
    private String rg;

    @NotBlank(message = "Orgão expedidor é obrigatório")
    @Column(name = "orgao_expedidor")
    private String orgaoExpedidor;

    @NotNull
    @Column(name = "estado_civil")
    private String estadoCivil;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\(\\d{2}\\)\\d{4,5}-\\d{4}", message = "Telefone inválido. Enviar no formato (00)00000-0000 ou (00)0000-0000")
    private String telefone;

    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Naturalidade é obrigatória")
    @Size(min = 3, max = 64, message = "Naturalidade deve ter no mínimo 3 e no máximo 64 dígitos")
    private String naturalidade;

    @NotBlank(message = "Contato de emergência é obrigatório")
    @Pattern(regexp = "\\(\\d{2}\\)\\d{4,5}-\\d{4}", message = "Telefone inválido. Enviar no formato (00) 00000-0000 ou (00) 0000-0000")
    @Column(name = "contato_emergencia")
    private String contatoEmergencia;

    @Column(name = "lista_alergias")
    private String listaAlergias;
    @Column(name = "lista_cuidados")
    private String listaCuidados;
    private String convenio;
    @Column(name = "numero_convenio")
    private String numeroConvenio;
    @Column(name = "validade_convenio")
    private LocalDate validadeConvenio;
    @Valid
    private EnderecoRequest endereco;


    @JsonProperty("id_usuario")
    private UsuarioRequest usuario;

}
