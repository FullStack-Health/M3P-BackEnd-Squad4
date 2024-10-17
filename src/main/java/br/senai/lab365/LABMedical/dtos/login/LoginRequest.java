package br.senai.lab365.LABMedical.dtos.login;

public record LoginRequest(
        String email,
        String password
//        String nomePerfil
) {
}
