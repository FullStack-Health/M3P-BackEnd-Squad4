package br.senai.lab365.LABMedical.dtos.prontuario;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProntuarioResponse {

    private Long id;
    private String nome;
    private String convenio;
    private String contatoEmergencia;
    private String listaAlergias;
    private String listaCuidados;
    private List<ExameResponse> exames;
    private List<ConsultaResponse> consultas;

//    private PacienteResponse paciente;
}
