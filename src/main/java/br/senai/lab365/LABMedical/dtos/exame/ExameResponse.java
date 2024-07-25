package br.senai.lab365.LABMedical.dtos.exame;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExameResponse {

    private Long id;
    private String nomeExame;
    private LocalDate dataExame;
    private LocalTime horarioExame;
    private String tipoExame;
    private String laboratorio;
    private String urlDocumento;
    private String resultados;
    private Long idPaciente;
}
