package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.PacienteResponse;
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
}
