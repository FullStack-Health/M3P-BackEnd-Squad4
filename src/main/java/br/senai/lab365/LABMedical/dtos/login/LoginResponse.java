package br.senai.lab365.LABMedical.dtos.login;

public record LoginResponse(String token, Long tempoExpiracao) {
}
