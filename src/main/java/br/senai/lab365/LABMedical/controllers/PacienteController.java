package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponse;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponsePagination;
import br.senai.lab365.LABMedical.exceptions.PacienteNaoEncontradoException;
import br.senai.lab365.LABMedical.services.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "Cadastrar um novo paciente", description = "Endpoint para cadastrar um novo paciente", responses = {
            @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, por exemplo, dados ausentes ou incorretos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "409", description = "Falha ao cadastrar, pois há algum dado duplicado")
    })
    public ResponseEntity<?> cadastra(@Valid @RequestBody PacienteRequest request) {
        try {
            PacienteResponse pacienteResponse = service.cadastra(request);
            logger.info("Paciente cadastrado com sucesso: {}", pacienteResponse.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteResponse); // Código 201
        } catch (DataIntegrityViolationException e) {
            logger.warn("Erro de integridade: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado!"); // Código 409
        } catch (ConstraintViolationException e) {
            logger.warn("Requisição inválida: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Requisição inválida: " + e.getMessage()); // Código 400
        } catch (AccessDeniedException | AuthenticationException e) {
            logger.warn("Falha de autenticação ou acesso negado.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Falha de autenticação."); // Código 401
        } catch (Exception e) {
            logger.error("Erro interno ao cadastrar paciente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao cadastrar paciente."); // Código 500
        }
    }

    // 2. Obter Paciente por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar paciente por ID", description = "Endpoint para buscar um paciente pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    public ResponseEntity<?> busca(@PathVariable Long id) {
        logger.info("GET /pacientes/{} - Iniciando busca do paciente com ID {}", id, id);
        try {
            if (!service.existePaciente(id)) {
                logger.info("Paciente com ID {} não encontrado.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Paciente não encontrado."); // Código 404
            }

            PacienteResponse paciente = service.busca(id);
            logger.info("Paciente com ID {} encontrado.", id);
            return ResponseEntity.ok(paciente); // Código 200
        } catch (AccessDeniedException | AuthenticationException e) {
            logger.warn("Falha de autenticação ou acesso negado para o paciente com ID {}.", id);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Falha de autenticação."); // Código 401
        } catch (Exception e) {
            logger.error("Erro interno ao buscar paciente com ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao buscar o paciente."); // Código 500
        }
    }

    // 3. Atualizar Paciente
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações do paciente", description = "Endpoint para atualizar as informações de um paciente", responses = {
            @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, por exemplo, dados ausentes ou incorretos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    public ResponseEntity<?> atualiza(@PathVariable Long id, @Valid @RequestBody PacienteRequest request) {
        try {
            if (!service.existePaciente(id)) {
                logger.info("Paciente com ID {} não encontrado.", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Paciente não encontrado.");  // Código 404
            }

            logger.info("Paciente com ID {} encontrado e atualizado.", id);
            PacienteResponse pacienteAtualizado = service.atualiza(id, request);
            return ResponseEntity.ok(pacienteAtualizado);  // Código 200
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Requisição inválida: " + e.getMessage());  // Código 400
        } catch (AuthenticationException | AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Falha de autenticação.");  // Código 401
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao tentar atualizar o paciente.");
        }
    }

    // 4. Excluir Paciente
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover paciente por ID", description = "Endpoint para excluir um paciente pelo ID", responses = {
            @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    public ResponseEntity<String> remove(@PathVariable Long id) {
        if (!service.existePaciente(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Paciente não encontrado."); // Código 404
        }

        try {
            service.remove(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Código 204
        } catch (AccessDeniedException | AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Falha de autenticação."); // Código 401
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro ao remover: o paciente tem dependências ativas."); // Código 409
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao tentar remover o paciente."); // Código 500
        }
    }

    // 5. Listar Pacientes com Filtros e Paginação
    @GetMapping
    @Operation(summary = "Listar pacientes com filtros e paginação", description = "Endpoint para listar pacientes com filtros e paginação", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<?> lista(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) String email,
            @RequestParam(value = "numeroPagina", defaultValue = "0") int numeroPagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "10") int tamanhoPagina) {
        try {
            PacienteResponsePagination pacientes = service.lista(id, nome, telefone, email, numeroPagina, tamanhoPagina);
            logger.info("Lista de pacientes recuperada com sucesso.");
            return ResponseEntity.ok(pacientes); // Código 200
        } catch (AccessDeniedException | AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Falha de autenticação."); // Código 401
        } catch (PacienteNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Paciente não encontrado: " + e.getQueryParameter()); // Código 404
        } catch (Exception e) {
            logger.error("Erro interno ao listar pacientes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao listar pacientes."); // Código 500
        }
    }
}
