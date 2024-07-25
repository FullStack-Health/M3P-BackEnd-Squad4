package br.senai.lab365.LABMedical.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "exames")
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exame")
    private Long id;

    @Column(name = "nome_exame", nullable = false)
    private String nomeExame;

    @Column(name = "data_exame", nullable = false)
    private LocalDate dataExame;

    @Column(name = "horario_exame", nullable = false)
    private LocalTime horarioExame;

    @Column(name = "tipo_exame", nullable = false)
    private String tipoExame;

    @Column(name = "laboratorio", nullable = false)
    private String laboratorio;

    private String urlDocumento;

    @Column(name = "resultados")
    private String resultados;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;
}
