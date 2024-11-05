package br.senai.lab365.LABMedical.dtos.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RedefinicaoSenhaRequest {

    @NotBlank(message = "A nova senha não pode ser em branco")
    @Size(max = 255, message = "Senha deve ter no máximo 255 caracteres")
    private String password;

}