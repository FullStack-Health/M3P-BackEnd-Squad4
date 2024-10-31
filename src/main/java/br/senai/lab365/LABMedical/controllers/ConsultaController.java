package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaRequest;
import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponse cadastra(@Valid @RequestBody ConsultaRequest consultaRequest) {
        return service.cadastra(consultaRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaResponse busca(@PathVariable Long id) {
        return service.busca(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaResponse atualiza(@PathVariable Long id,
                                     @Valid @RequestBody ConsultaRequest consultaRequest) {
        return service.atualiza(id, consultaRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        service.remove(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ConsultaResponse> mostraLista() {
        return service.mostraLista();

    }
}
