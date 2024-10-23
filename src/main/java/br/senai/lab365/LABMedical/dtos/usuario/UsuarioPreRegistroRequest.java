package br.senai.lab365.LABMedical.dtos.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
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
@Table(name = "usuarios_pre_registro")
public class UsuarioPreRegistroRequest {

    private Long id;

    @NotBlank(message = "Email não pode ser em branco")
    @Size(max = 255)
    @Email(message = "Email inválido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Senha não pode ser em branco")
    @Size(max = 255, message = "Senha deve ter no máximo 255 caracteres")
    private String password;

    @NotBlank(message = "Nome do perfil não pode ser em branco")
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