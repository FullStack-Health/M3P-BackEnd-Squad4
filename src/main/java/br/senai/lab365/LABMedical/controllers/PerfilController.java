package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.services.PerfilService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService service;

    public PerfilController(PerfilService service) {
        this.service = service;
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public String cadastra(@RequestBody PerfilRequest request) {
//        service.cadastra(request);
//        return "Perfil '" + request.nomePerfil() + "' criado com sucesso!";
//    }


}
