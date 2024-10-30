package br.senai.lab365.LABMedical.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long id;
    private String nome;
    private String genero;
    private LocalDate dataNascimento;
    private String cpf;
    private String rg;
    private String orgaoExpedidor;
    private String estadoCivil;
    private String telefone;
    private String email;
    private String naturalidade;
    private String contatoEmergencia;
    private String listaAlergias;
    private String listaCuidados;
    private String convenio;
    private String numeroConvenio;
    private LocalDate validadeConvenio;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}