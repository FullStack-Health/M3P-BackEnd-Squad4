package br.senai.lab365.LABMedical.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PacienteResponsePagination {

    private List<PacienteGetRequest> conteudo;
    private int totalPaginas;
    private int tamanhoPagina;
    private int paginaAtual;
    private int totalElementos;
    private boolean ultima;

}
