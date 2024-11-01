package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponse;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponsePagination;
import br.senai.lab365.LABMedical.services.PacienteService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.security.core.AuthenticationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    // 1. Criar Paciente
    @PostMapping
    public ResponseEntity<?> cadastra(@Valid @RequestBody PacienteRequest request) {
        try {
            // Tenta cadastrar o paciente e retorna o JSON do paciente cadastrado com status 201.
            PacienteResponse pacienteResponse = service.cadastra(request);
            logger.info("Paciente cadastrado com sucesso: {}", pacienteResponse.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteResponse); // Código 201

        } catch (DataIntegrityViolationException e) {
            // Captura erros de integridade (dados duplicados), como CPF já cadastrado.
            logger.warn("Erro de integridade: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado!"); // Código 409

        } catch (ConstraintViolationException e) {
            // Captura erros de validação, como dados ausentes ou incorretos.
            logger.warn("Requisição inválida: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Requisição inválida: " + e.getMessage()); // Código 400

        } catch (AccessDeniedException | AuthenticationException e) {
            // Captura erros de autenticação ou autorização.
            logger.warn("Falha de autenticação ou acesso negado.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Falha de autenticação."); // Código 401

        } catch (Exception e) {
            // Captura qualquer outro erro inesperado.
            logger.error("Erro interno ao cadastrar paciente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao cadastrar paciente."); // Código 500
        }
    }


    // 2. Obter Paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> busca(@PathVariable Long id) {
        try {
            // Verificação se o paciente existe.
            if (!service.existePaciente(id)) {
                logger.info("Paciente com ID {} não encontrado.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Paciente não encontrado."); // Código 404: Paciente não encontrado.
            }

            // Retorno do paciente encontrado.
            PacienteResponse paciente = service.busca(id);
            logger.info("Paciente com ID {} encontrado.", id);
            return ResponseEntity.ok(paciente); // Código 200: OK

        } catch (AccessDeniedException | AuthenticationException e) {
            // Captura exceções de autorização/autenticação.
            logger.warn("Falha de autenticação ou acesso negado para o paciente com ID {}.", id);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Falha de autenticação."); // Código 401: Unauthorized

        } catch (Exception e) {
            // Captura qualquer outro erro inesperado.
            logger.error("Erro interno ao buscar paciente com ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao buscar o paciente."); // Código 500
        }
    }

    // 3. Atualizar Paciente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualiza(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequest request) {
        try {
            // Verificação se o paciente existe.
            if (!service.existePaciente(id)) {
                logger.info("Paciente com ID {} não encontrado.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Paciente não encontrado.");  // Código 404.
            }

            logger.info("Paciente com ID {} encontrado e atualizado.", id);
            PacienteResponse pacienteAtualizado = service.atualiza(id, request);
            return ResponseEntity.ok(pacienteAtualizado);  // Código 200.

        } catch (ConstraintViolationException e) {
            // Captura erros de validação de dados.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Requisição inválida: " + e.getMessage());  // Código 400.
        } catch (AuthenticationException | AccessDeniedException e) {
            // Captura falhas de autenticação/autorização.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Falha de autenticação.");  // Código 401.
        } catch (Exception e) {
            // Captura qualquer outro erro inesperado.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao tentar atualizar o paciente.");
        }
    }

    // 4. Excluir Paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        // Verificação se o paciente existe antes de tentar removê-lo.
        if (!service.existePaciente(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Paciente não encontrado."); // Código 404: Paciente não encontrado.
        }

        try {
            service.remove(id); // Remoção do paciente.
            // Retorna 200 OK com a mensagem no corpo da resposta.
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Paciente excluído com sucesso."); // Mensagem no corpo com sucesso.
        } catch (AccessDeniedException | AuthenticationException e) {
            // Captura falhas de autenticação.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Falha de autenticação."); // Código 401: Unauthorized.
        } catch (DataIntegrityViolationException e) {
            // Captura erros de integridade, como dependências ativas.
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro ao remover: o paciente tem dependências ativas."); // Código 409: Conflito.
        } catch (Exception e) {
            // Captura qualquer outro erro inesperado.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao tentar remover o paciente."); // Código 500: Erro interno.
        }
    }


    // 5. Listar Pacientes com Filtros e Paginação
    @GetMapping
    public ResponseEntity<?> lista(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) String email,
            @RequestParam(value = "numeroPagina", defaultValue = "0") int numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "10") int tamanhoPagina) {

        try {
            // Retorna a lista paginada de pacientes com filtros, se aplicáveis.
            PacienteResponsePagination pacientes = service.lista(id, nome, telefone, email, numeroPagina, tamanhoPagina);
            logger.info("Lista de pacientes recuperada com sucesso.");
            return ResponseEntity.ok(pacientes); // Código 200

        } catch (AccessDeniedException | AuthenticationException e) {
            // Captura falhas de autenticação ou autorização.
            logger.warn("Falha de autenticação ao listar pacientes.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha de autenticação."); // Código 401

        } catch (Exception e) {
            // Captura qualquer outro erro inesperado.
            logger.error("Erro ao listar pacientes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao listar pacientes."); // Código 500 (caso necessário)
        }
    }

}
