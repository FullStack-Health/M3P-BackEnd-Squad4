package br.senai.lab365.LABMedical.dtos.prontuario;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaRequest;
import br.senai.lab365.LABMedical.dtos.exame.ExameRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProntuarioRequest {

    private String nome;
    private String convenio;
    private String contatoEmergencia;
    private String listaAlergias;
    private String listaCuidados;
    private List<ExameRequest> exames;
    private List<ConsultaRequest> consultas;

//    private PacienteRequest paciente;
}
