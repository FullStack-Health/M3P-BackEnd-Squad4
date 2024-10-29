package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroResponse;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioResponse;
import br.senai.lab365.LABMedical.entities.Exame;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse busca(@PathVariable Long id) {
        logger.info("GET /usuarios/{id} - Iniciando a busca usuário com id: {}", id);
        UsuarioResponse usuario = service.busca(id);
        logger.info("GET /usuarios/{id} - Busca concluída com sucesso: {}", id);
        return usuario;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponse> buscaPorIdOuEmail(
            @RequestParam(required = false)  Long id,
            @RequestParam(required = false)  String email) {
        if (id != null || email != null) {
            logger.info("GET /usuarios - Iniciando a busca de usuários por ID: {} ou email: {}", id, email);
        } else {
            logger.info("GET /usuarios - Iniciando a listagem de todos os usuários");
        }
        List<UsuarioResponse> usuarios = service.lista(id, email);
        if (id != null || email != null) {
            logger.info("GET /usuarios - Busca concluída com sucesso!");
        } else {
            logger.info("GET /usuarios - Listagem concluída com sucesso");
        }
        return usuarios;
    }

    @GetMapping("/perfil/{nomePerfil}")
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponse> buscaPorPerfil(@PathVariable String nomePerfil) {
        logger.info("GET /usuarios/perfil/{} - Iniciando busca de usuários com o perfil: {}", nomePerfil, nomePerfil);
        List<UsuarioResponse> usuarios = service.buscaPorPerfil(nomePerfil);
        logger.info("GET /usuarios/perfil/{} - Busca concluída com sucesso! Perfil: {}", nomePerfil, nomePerfil);
        return usuarios;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponse atualiza(@PathVariable Long id, @RequestBody @Valid UsuarioRequest request) {
        logger.info("PUT /usuarios/{} - Iniciando a atualização do usuário com id: {}", id, id);
        UsuarioResponse usuario = service.atualiza(id, request);
        logger.info("PUT /usuarios/{} - Atualização concluída com sucesso: {}", id, id);
        return usuario;
    }

}
