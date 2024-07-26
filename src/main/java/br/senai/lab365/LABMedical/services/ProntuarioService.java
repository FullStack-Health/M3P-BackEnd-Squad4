package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.paciente.PacienteGetRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponsePagination;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteSummaryRequest;
import br.senai.lab365.LABMedical.dtos.paciente.SummaryResponsePagination;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.mappers.ProntuarioMapper;
import br.senai.lab365.LABMedical.repositories.ProntuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProntuarioService {

    private final ProntuarioRepository repository;
    private final ProntuarioMapper mapper;

    public ProntuarioService(ProntuarioRepository repository, ProntuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public SummaryResponsePagination lista(Long idPaciente, String nome, int numeroPagina, int tamanhoPagina) {
        Pageable paginacao = PageRequest.of(numeroPagina, tamanhoPagina);
        Page<Paciente> paginaPacientes;

        if (nome != null && idPaciente != null) {
            paginaPacientes = repository.findByIdAndNomeIgnoreCaseContaining(idPaciente, nome, paginacao);
        } else if (nome != null) {
            paginaPacientes = repository.findByNomeIgnoreCaseContaining(nome, paginacao);
        } else {
            paginaPacientes = repository.findAll(paginacao);
        }

        if (idPaciente != null) {
            Paciente paciente = repository.findById(idPaciente).orElseThrow(
                    () -> new EntityNotFoundException("Paciente não encontrado com o id: " + idPaciente)
            );
        }

        List<PacienteSummaryRequest> conteudo =  paginaPacientes.getContent().stream()
                .map(mapper::getRequestToResponse)
                .collect(Collectors.toList());

        if(conteudo.isEmpty()){
            throw new EntityNotFoundException("Nenhum paciente encontrado");
        }

        SummaryResponsePagination response = new SummaryResponsePagination();
        response.setConteudo(conteudo);
        response.setTotalPaginas(paginaPacientes.getTotalPages());
        response.setTamanhoPagina(tamanhoPagina);
        response.setPaginaAtual(numeroPagina);
        response.setTotalElementos((int) paginaPacientes.getTotalElements());
        response.setUltima(paginaPacientes.isLast());

        return response;
    }
}
