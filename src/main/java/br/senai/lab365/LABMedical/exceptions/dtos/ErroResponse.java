package br.senai.lab365.LABMedical.exceptions.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Getter
@Setter
@NoArgsConstructor
public class ErroResponse {
    private String campo;
    private String mensagem;

    public ErroResponse(FieldError error) {
        this.campo = error.getField();
        this.mensagem = error.getDefaultMessage();
    }
}
