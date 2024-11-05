package br.senai.lab365.LABMedical.dtos.paciente;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnderecoRequest {

//    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido. Enviar no formato 00000-000")
    private String cep;
    private String cidade;
    private String estado;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    @Column(name = "ponto_referencia")
    private String ptoReferencia;

}
