package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.ProntuarioResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.SummaryResponsePagination;
import br.senai.lab365.LABMedical.services.ProntuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Lista todos os prontuários", description = "Retorna uma lista de prontuários com base nos parâmetros fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de prontuários concluída com sucesso."),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação."),
            @ApiResponse(responseCode = "404", description = "Não há prontuários cadastrados.")
    })
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

    @Operation(summary = "Busca um prontuário pelo ID do paciente", description = "Retorna o prontuário do paciente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca do prontuário concluída com sucesso."),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação."),
            @ApiResponse(responseCode = "404", description = "Não há pacientes cadastrados com o id informado.")
    })
    @GetMapping("/{idPaciente}/prontuarios")
    @ResponseStatus(HttpStatus.OK)
    public ProntuarioResponse busca(@PathVariable Long idPaciente) {
        logger.info("GET /pacientes/{}/prontuarios - Iniciando a busca do prontuario do paciente com id: {}", idPaciente, idPaciente);
        ProntuarioResponse response = service.busca(idPaciente);
        logger.info("GET /pacientes/{}/prontuarios - Busca do prontuario concluída com sucesso", idPaciente);
        return response;
    }

    @Operation(summary = "Lista todos os exames de um paciente", description = "Retorna uma lista de exames do paciente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de exames concluída com sucesso."),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação."),
            @ApiResponse(responseCode = "404", description = "Não há exames cadastrados para o paciente informado.")
    })
    @GetMapping("/{idPaciente}/exames")
    @ResponseStatus(HttpStatus.OK)
    public List<ExameResponse> listaTodosExamesPaciente(@PathVariable Long idPaciente) {
        logger.info("GET /pacientes/{}/exames - Iniciando a listagem de exames do paciente com id: {}", idPaciente, idPaciente);
        List<ExameResponse> response = service.listaTodosExamesPaciente(idPaciente);
        logger.info("GET /pacientes/{}/exames - Listagem de exames concluída com sucesso", idPaciente);
        return response;
    }

    @Operation(summary = "Lista todas as consultas de um paciente", description = "Retorna uma lista de consultas do paciente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem de consultas concluída com sucesso."),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação."),
            @ApiResponse(responseCode = "404", description = "Não há consultas cadastradas para o paciente informado.")
    })
    @GetMapping("/{idPaciente}/consultas")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsultaResponse> listaTodasConsultasPaciente(@PathVariable Long idPaciente) {
        logger.info("GET /pacientes/{}/consultas - Iniciando a listagem de consultas do paciente com id: {}", idPaciente, idPaciente);
        List<ConsultaResponse> response = service.listaTodasConsultasPaciente(idPaciente);
        logger.info("GET /pacientes/{}/consultas - Listagem de consultas concluída com sucesso", idPaciente);
        return response;
    }
}
