package br.senai.lab365.LABMedical.dtos.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioPreRegistroResponse {

    private Long id;
    private String email;
    private String password;
    private List<String> listaNomesPerfis;
    private String senhaComMascara;
}
