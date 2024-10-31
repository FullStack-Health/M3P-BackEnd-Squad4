package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.ProntuarioResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.SummaryResponsePagination;
import br.senai.lab365.LABMedical.services.ProntuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class ProntuarioController {

    private static final Logger logger = LoggerFactory.getLogger(ProntuarioController.class);
    private final ProntuarioService service;

    public ProntuarioController(ProntuarioService service) {
        this.service = service;
    }

    @GetMapping("/prontuarios")
    @ResponseStatus(HttpStatus.OK)
    public SummaryResponsePagination lista(
            @RequestParam(required = false) Long idPaciente,
            @RequestParam(required = false) String nome,
            @RequestParam(value = "numeroPagina", defaultValue = "0", required = false) int numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "10", required = false) int tamanhoPagina
    ) {
        logger.info("GET /pacientes/prontuarios - Iniciando a listagem de prontuarios");
        SummaryResponsePagination response = service.lista(idPaciente, nome, numeroPagina, tamanhoPagina);
        logger.info("GET /pacientes/prontuarios - Listagem de prontuarios concluída com sucesso");
        return response;
    }

    @GetMapping("/{idPaciente}/prontuarios")
    @ResponseStatus(HttpStatus.OK)
    public ProntuarioResponse busca(@PathVariable Long idPaciente) {
        logger.info("GET /pacientes/{}/prontuarios - Iniciando a busca do prontuario do paciente com id: {}", idPaciente, idPaciente);
        ProntuarioResponse response = service.busca(idPaciente);
        logger.info("GET /pacientes/{}/prontuarios - Busca do prontuario concluída com sucesso", idPaciente);
        return response;
    }

    @GetMapping("/{idPaciente}/exames")
    @ResponseStatus(HttpStatus.OK)
    public List<ExameResponse> listaTodosExamesPaciente(@PathVariable Long idPaciente) {
        logger.info("GET /pacientes/{}/exames - Iniciando a listagem de exames do paciente com id: {}", idPaciente, idPaciente);
        List<ExameResponse> examesPaciente = service.listaTodosExamesPaciente(idPaciente);
        logger.info("GET /pacientes/{}/exames - Listagem de exames concluída com sucesso", idPaciente);
        return examesPaciente;
    }

    @GetMapping("/{idPaciente}/consultas")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsultaResponse> listaTodasConsultasPaciente(@PathVariable Long idPaciente) {
        logger.info("GET /pacientes/{}/consultas - Iniciando a listagem de consultas do paciente com id: {}", idPaciente, idPaciente);
        List<ConsultaResponse> consultasPaciente = service.listaTodasConsultasPaciente(idPaciente);
        logger.info("GET /pacientes/{}/consultas - Listagem de consultas concluída com sucesso", idPaciente);
        return consultasPaciente;
    }
}