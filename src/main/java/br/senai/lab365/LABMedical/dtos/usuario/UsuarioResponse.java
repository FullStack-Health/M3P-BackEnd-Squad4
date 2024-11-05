package br.senai.lab365.LABMedical.dtos.usuario;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;
    private String password;
    private List<String> listaNomesPerfis;
    private String senhaComMascara;

    public UsuarioResponse(Long id, String nome, String email, LocalDate dataNascimento, String cpf, String telefone, String password, List<String> listaNomesPerfis, String senhaComMascara) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.telefone = telefone;
        this.password = password;
        this.listaNomesPerfis = listaNomesPerfis;
        this.senhaComMascara = senhaComMascara;
    }
}
