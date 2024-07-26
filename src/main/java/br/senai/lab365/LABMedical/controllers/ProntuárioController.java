package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponsePagination;
import br.senai.lab365.LABMedical.dtos.paciente.SummaryResponsePagination;
import br.senai.lab365.LABMedical.services.ProntuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ProntuárioController {

    private final ProntuarioService service;

    public ProntuárioController(ProntuarioService service) {
        this.service = service;
    }

    @GetMapping("/pacientes/prontuarios")
    @ResponseStatus(HttpStatus.OK)
    public SummaryResponsePagination lista(
            @RequestParam(required = false) Long idPaciente,
            @RequestParam(required = false) String nome,
            @RequestParam(value = "numeroPagina", defaultValue = "0", required = false) int numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "10", required = false) int tamanhoPagina
    ) {
        return service.lista(idPaciente, nome, numeroPagina, tamanhoPagina);
    }
}
