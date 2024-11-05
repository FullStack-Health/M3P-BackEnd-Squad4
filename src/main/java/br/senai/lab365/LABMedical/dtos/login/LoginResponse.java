package br.senai.lab365.LABMedical.dtos.login;

import java.util.List;

public record LoginResponse(
        String token,
        Long tempoExpiracao,
        List<String> listaNomesPerfis,
        String pacienteId,
        String usuarioId,
        String email,
        String nome
) {}
