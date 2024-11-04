package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaRequest;
import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.services.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastra uma nova consulta",
            description = "Cria uma nova consulta com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consulta criada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse cadastra(@Valid @RequestBody ConsultaRequest consultaRequest) {
        return service.cadastra(consultaRequest);
    }

    @Operation(summary = "Busca uma consulta por ID",
            description = "Retorna os dados da consulta especificada pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta encontrada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaResponse busca(@PathVariable Long id) {
        return service.busca(id);
    }

    @Operation(summary = "Atualiza uma consulta existente",
            description = "Atualiza as informações de uma consulta com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaResponse atualiza(@PathVariable Long id,
                                     @Valid @RequestBody ConsultaRequest consultaRequest) {
        return service.atualiza(id, consultaRequest);
    }

    @Operation(summary = "Remove uma consulta",
            description = "Exclui a consulta especificada pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consulta removida com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        service.remove(id);
    }

}
