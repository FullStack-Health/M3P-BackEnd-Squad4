package br.senai.lab365.LABMedical.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
public class CpfDuplicadoException extends RuntimeException {
    public CpfDuplicadoException(String message) {
        super(message);
    }
}
