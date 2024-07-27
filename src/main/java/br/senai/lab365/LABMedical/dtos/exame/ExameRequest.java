package br.senai.lab365.LABMedical.dtos.exame;

import jakarta.persistence.Column;
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
public class ExameRequest {

    @NotNull(message = "O nome do exame é obrigatório")
    @Size(min = 8, max = 64, message = "O nome do exame deve ter no mínimo 8 e no máximo 64 caracteres")
    private String nomeExame;

    @NotNull(message = "A data do exame é obrigatória")
    private LocalDate dataExame;

    @NotNull(message = "O horário do exame é obrigatório")
    private LocalTime horarioExame;

    @NotNull(message = "O tipo do exame é obrigatório")
    @Size(min = 4, max = 32, message = "O tipo do exame deve ter no mínimo 4 e no máximo 32 caracteres")
    private String tipoExame;

    @NotNull(message = "O nome do laboratório é obrigatório")
    @Size(min = 4, max = 32, message = "O nome do laboratório deve ter no mínimo 4 e no máximo 32 caracteres")
    private String laboratorio;

    private String urlDocumento;

    @Size(min = 16, max = 1024, message = "Os resultados devem ter no mínimo 16 e no máximo 1024 caracteres")
    private String resultados;

    @NotNull(message = "O id do paciente é obrigatório")
    @Column(name = "id_paciente")
    private Long idPaciente;
}
