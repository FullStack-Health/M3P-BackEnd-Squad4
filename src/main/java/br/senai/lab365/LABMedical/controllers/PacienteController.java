package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.PacienteResponse;
import br.senai.lab365.LABMedical.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PacienteResponse cadastra(@RequestBody PacienteRequest request) {
        return service.cadastra(request);
    }
}
