package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.usuario.UsuarioRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioResponse;
import br.senai.lab365.LABMedical.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
public class UsuarioController {

    private final UsuarioService service;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Busca Usuário", description = "Busca um usuário pelo ID informado.")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso.")
    @ApiResponse(responseCode = "401", description = "Código 401 (Unauthorized) - Falha de autenticação.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    public UsuarioResponse busca(@PathVariable Long id) {
        logger.info("GET /usuarios/{id} - Iniciando a busca usuário com id: {}", id);
        UsuarioResponse usuario = service.busca(id);
        logger.info("GET /usuarios/{id} - Busca concluída com sucesso: {}", id);
        return usuario;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista Usuários", description = "Lista todos os usuários ou busca por ID ou email.")
    @ApiResponse(responseCode = "200", description = "Listagem de usuários realizada com sucesso.")
    @ApiResponse(responseCode = "401", description = "Código 401 (Unauthorized) - Falha de autenticação.")
    @ApiResponse(responseCode = "404", description = "Usuário(s) não encontrado(s).")
    public List<UsuarioResponse> buscaPorIdOuEmail(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String email) {
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
    @Operation(summary = "Busca Usuários por Perfil", description = "Busca todos os usuários com o perfil informado.")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso.")
    @ApiResponse(responseCode = "401", description = "Código 401 (Unauthorized) - Falha de autenticação.")
    @ApiResponse(responseCode = "404", description = "Usuários com o perfil especificado não encontrados.")
    public List<UsuarioResponse> buscaPorPerfil(@PathVariable String nomePerfil) {
        logger.info("GET /usuarios/perfil/{} - Iniciando busca de usuários com o perfil: {}", nomePerfil, nomePerfil);
        List<UsuarioResponse> usuarios = service.buscaPorPerfil(nomePerfil);
        logger.info("GET /usuarios/perfil/{} - Busca concluída com sucesso! Perfil: {}", nomePerfil, nomePerfil);
        return usuarios;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualiza Usuário", description = "Atualiza os dados de um usuário com base no ID informado.")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso.")
    @ApiResponse(responseCode = "401", description = "Código 401 (Unauthorized) - Falha de autenticação.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados de entrada.")
    public UsuarioResponse atualiza(@PathVariable Long id, @RequestBody @Valid UsuarioRequest request) {
        logger.info("PUT /usuarios/{} - Iniciando a atualização do usuário com id: {}", id, id);
        UsuarioResponse usuario = service.atualiza(id, request);
        logger.info("PUT /usuarios/{} - Atualização concluída com sucesso: {}", id, id);
        return usuario;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove Usuário", description = "Remove um usuário com base no ID informado.")
    @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso.")
    @ApiResponse(responseCode = "401", description = "Código 401 (Unauthorized) - Falha de autenticação.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    public void remove(@PathVariable Long id) {
        logger.info("DELETE /usuarios/{} - Iniciando a remoção do usuário com id: {}", id, id);
        service.remove(id);
        logger.info("DELETE /usuarios/{} - Remoção concluída com sucesso: {}", id, id);
    }
}
