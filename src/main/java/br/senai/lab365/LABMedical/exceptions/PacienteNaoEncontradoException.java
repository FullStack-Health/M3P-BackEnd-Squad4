package br.senai.lab365.LABMedical.exceptions;

public class PacienteNaoEncontradoException extends RuntimeException {
    private final String queryParameter;

    public PacienteNaoEncontradoException(String message, String queryParameter) {
        super(message);
        this.queryParameter = queryParameter;
    }

    public String getQueryParameter() {
        return queryParameter;
    }
}