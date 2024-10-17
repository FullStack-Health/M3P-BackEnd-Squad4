package br.senai.lab365.LABMedical.exceptions;

import br.senai.lab365.LABMedical.exceptions.dtos.ErroResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErroResponse> trataChaveDuplicada(DuplicateKeyException exception) {
        ErroResponse response = new ErroResponse();

        if (exception.getMostSpecificCause().getMessage().contains("CPF")) {
            response.setCampo("CPF");
        } else if (exception.getMostSpecificCause().getMessage().contains("email")) {
            response.setCampo("email");
        }

        response.setMensagem(exception.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> trataParametroInvalido(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> trataEntidadeNaoEncontrada(EntityNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(DataFormatException.class)
//    public ResponseEntity<ErroResponse> trataErroDeFormatoDeData(DataFormatException exception) {
//        ErroResponse response = new ErroResponse();
//        response.setCampo("dataConsulta");
//        response.setMensagem(exception.getMessage());
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
}
