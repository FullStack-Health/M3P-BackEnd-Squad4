package br.senai.lab365.LABMedical.dtos.paciente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnderecoResponse {

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
