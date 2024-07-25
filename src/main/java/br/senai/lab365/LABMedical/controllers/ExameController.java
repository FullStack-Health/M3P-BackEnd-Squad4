package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.exame.ExameRequest;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.services.ExameService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exames")
public class ExameController {

    private final ExameService service;

    private ExameController(ExameService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExameResponse cadastra(@Valid @RequestBody ExameRequest request) {
        return service.cadastra(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExameResponse busca(@PathVariable Long id) {
        return service.busca(id);
    }


}
