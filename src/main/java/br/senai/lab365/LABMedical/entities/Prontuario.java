package br.senai.lab365.LABMedical.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "prontuarios")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prontuario")
    private Long id;
    private String nome;
    private String convenio;
    private String contatoEmergencia;
    private String listaAlergias;
    private String listaCuidados;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_prontuario")
    private List<Exame> exames;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_prontuario")
    private List<Consulta> consultas;

}
