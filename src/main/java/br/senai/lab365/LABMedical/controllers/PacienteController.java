package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponse;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponsePagination;
import br.senai.lab365.LABMedical.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService service;

    @Autowired
    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteResponse cadastra(@Valid @RequestBody PacienteRequest request) {
        return service.cadastra(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PacienteResponse busca(
            @PathVariable Long id,
            @RequestBody PacienteRequest request) {
        return service.busca(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PacienteResponse atualiza(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequest request) {
        return service.atualiza(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        service.remove(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PacienteResponsePagination lista(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) String email,
            @RequestParam(value = "numeroPagina", defaultValue = "0", required = false) int numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "10", required = false) int tamanhoPagina
    ) {
        return service.lista(nome, telefone, email, numeroPagina, tamanhoPagina);
    }
}
