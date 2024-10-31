package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.exame.ExameRequest;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.ProntuarioResponse;
import br.senai.lab365.LABMedical.services.ExameService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exames")
public class ExameController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    private final ExameService service;

    private ExameController(ExameService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExameResponse cadastra(@Valid @RequestBody ExameRequest request) {
        logger.info("Iniciando cadastro de exame para paciente {}", request.getIdPaciente());
        ExameResponse exame = service.cadastra(request);
        logger.info("Cadastro de exame concluído para paciente {}", request.getIdPaciente());
        return exame;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ExameResponse> lista(Pageable pageable) {
        logger.info("GET /exames/ - Iniciando a busca de todos os exames com pageable: {}", pageable);
        List<ExameResponse> exames = service.lista(pageable);
        logger.info("GET /exames/ - Busca de todos exames concluída!");
        return exames;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExameResponse busca(@PathVariable Long id) {
        logger.info("GET /exames/{} - Iniciando a busca de exame", id);
        ExameResponse exame = service.busca(id);
        logger.info("GET /exames/{} - Busca de exame concluída", id);
        return exame;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExameResponse atualiza(@PathVariable Long id,
                                  @Valid @RequestBody ExameRequest request) {
        logger.info("PUT /exames/{} - Iniciando a atualização de exame", id);
        ExameResponse exame = service.atualiza(id, request);
        logger.info("PUT /exames/{} - Atualização de exame concluída", id);
        return exame;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        logger.info("DELETE /exames/{} - Iniciando a remoção de exame", id);
        service.remove(id);
        logger.info("DELETE /exames/{} - Remoção de exame concluída", id);
    }
}
