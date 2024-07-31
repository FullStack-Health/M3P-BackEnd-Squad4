package br.senai.lab365.LABMedical.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Long id;
    private String motivo;
    @Column(name = "data_consulta", nullable = false)
    private LocalDate dataConsulta;
    @Column(name = "horario_consulta", nullable = false)
    private LocalTime horarioConsulta;
    @Column(name = "descricao_problema")
    private String descricaoProblema;
    @Column(name = "medicacao_receitada")
    private String medicacaoReceitada;
    @Column(name = "dosagem_e_precaucoes")
    private String dosagemPrecaucoes;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id_paciente", nullable = false)
//    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_paciente", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Paciente paciente;

}