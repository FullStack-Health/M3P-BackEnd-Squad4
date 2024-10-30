package br.senai.lab365.LABMedical.dtos.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
public class UsuarioRequest {

    private Long id;

    @NotBlank(message = "Nome não pode ser em branco")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @NotBlank(message = "Email não pode ser em branco")
    @Size(max = 255)
    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "Data de nascimento é obrigatória. Enviar no formato yyyy-MM-dd")
    @Past(message = "Data de nascimento deve estar no passado")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @NotBlank(message = "CPF não pode ser em branco")
    @CPF(message = "CPF inválido. Escreva no formato 000.000.000-00")
    private String cpf;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\(\\d{2}\\)\\d{4,5}-\\d{4}", message = "Telefone inválido. Enviar no formato (00)00000-0000 ou (00)0000-0000")
    private String telefone;

//    @NotBlank(message = "Senha não pode ser em branco")
    @Size(max = 255, message = "Senha deve ter no máximo 255 caracteres")
    private String password;

//    @NotBlank(message = "Nome do perfil não pode ser em branco")
    private String nomePerfil;    // Nome do perfil que será associado ao usuário no momento do cadastro

    @Getter
    private String senhaComMascara;

    public void setSenhaComMascara(String password) {
        if (password == null || password.length() < 4) {
            this.senhaComMascara = password;
        } else {
            StringBuilder senhaComMascara = new StringBuilder(password.substring(0, 4));
            for (int i = 4; i < password.length(); i++) {
                senhaComMascara.append("*");
            }
            this.senhaComMascara = senhaComMascara.toString();
        }
    }
}