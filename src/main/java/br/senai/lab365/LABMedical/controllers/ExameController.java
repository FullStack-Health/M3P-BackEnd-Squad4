package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.exame.ExameRequest;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.services.ExameService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exames")
public class ExameController {

    private final ExameService service;

    public ExameController(ExameService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um novo exame")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exame cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ExameResponse cadastra(@Valid @RequestBody ExameRequest request) {
        return service.cadastra(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca um exame pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame encontrado"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ExameResponse busca(@PathVariable Long id) {
        return service.busca(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza um exame existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    })
    public ExameResponse atualiza(@PathVariable Long id,
                                  @Valid @RequestBody ExameRequest request) {
        return service.atualiza(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove um exame pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exame removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public void remove(@PathVariable Long id) {
        service.remove(id);
    }

}
