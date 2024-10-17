package br.senai.lab365.LABMedical.dtos.consulta;

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
public class ConsultaResponse {

    private Long id;
    private String motivo;
    private LocalDate dataConsulta;
    private LocalTime horarioConsulta;
    private String descricaoProblema;
    private String medicacaoReceitada;
    private String dosagemPrecaucoes;
    private Long idPaciente;
}
