package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.PerfilRequest;
import br.senai.lab365.LABMedical.services.PerfilService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService service;

    public PerfilController(PerfilService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String cadastra(@RequestBody PerfilRequest request) {
        service.cadastra(request);
        return "Perfil '" + request.nomePerfil() + "' criado com sucesso!";
    }


}
