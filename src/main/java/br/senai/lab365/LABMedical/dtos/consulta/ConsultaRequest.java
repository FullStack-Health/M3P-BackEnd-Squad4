package br.senai.lab365.LABMedical.dtos.consulta;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ConsultaRequest {

    @NotBlank(message = "O motivo da consulta é obrigatório")
    @Size(min = 3, max = 64, message = "O motivo da consulta deve ter no mínimo 8 e no máximo 64 caracteres")
    private String motivo;

    @NotNull(message = "A data da consulta é obrigatória. Enviar a data no formato yyyy-MM-dd")
    private LocalDate dataConsulta;

    @NotNull(message = "O horário da consulta é obrigatório. Enviar o horário no formato HH:mm")
    private LocalTime horarioConsulta;

    @NotNull(message = "A descrição do problema é obrigatória")
    @Size(min = 16, max = 1024, message = "A descrição do problema deve ter no mínimo 16 e no máximo 1024 caracteres")
    private String descricaoProblema;

    private String medicacaoReceitada;

    @Size(min = 16, max = 256, message = "A dosagem e precauções devem ter no mínimo 16 e no máximo 256 caracteres")
    private String dosagemPrecaucoes;
//
    @NotNull(message = "O id do paciente é obrigatório")
    @Column(name = "paciente_id")
    private Long idPaciente;
}
