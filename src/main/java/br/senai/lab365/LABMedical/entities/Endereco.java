package br.senai.lab365.LABMedical.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter@Table(name = "enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String cidade;
    private String estado;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String ptoReferencia;

}
