package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.PerfilRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroResponse;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioResponse;
import br.senai.lab365.LABMedical.services.UsuarioService;
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

    @PostMapping("/pre-registro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioPreRegistroResponse cadastra(@Valid @RequestBody UsuarioPreRegistroRequest usuarioRequest) {
        logger.info("POST/usuarios/pre-registro para o email: {}", usuarioRequest.getEmail());
        UsuarioPreRegistroResponse response = service.cadastra(usuarioRequest);
        logger.info("POST/usuarios/pre-registro usuário cadastrado com sucesso: {}", response.getEmail());
        return response;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponse> lista() {
        logger.info("GET /usuarios - Iniciando a busca de todos os usuários");
        List<UsuarioResponse> usuarios = service.lista();
        logger.info("GET /usuarios - Listagem concluída com sucesso");
        return usuarios;
    }
}
