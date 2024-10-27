package br.senai.lab365.LABMedical.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class DashboardResponse {

    private Long numeroPacientes;
    private Long numeroConsultas;
    private Long numeroExames;
    private Long numeroUsuarios;
}
