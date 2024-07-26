package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.paciente.PacienteGetRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponse;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponsePagination;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.mappers.PacienteMapper;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper mapper;

    public PacienteService(PacienteRepository pacienteRepository, PacienteMapper mapper) {
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    public PacienteResponse cadastra(@Valid PacienteRequest pacienteRequest) {
        // Verificar se o CPF já existe
        if (pacienteRepository.existsByCpf(pacienteRequest.getCpf())) {
            throw new DuplicateKeyException("CPF já cadastrado com este número: " + pacienteRequest.getCpf());
        }
        // Converter o DTO PacienteRequest para a entidade Paciente
        Paciente paciente = mapper.toEntity(pacienteRequest);
        // Salvar a entidade Paciente
        Paciente pacienteSalvo = pacienteRepository.save(paciente);
        // Converter a entidade Paciente salva para o DTO PacienteResponse
        return mapper.toResponse(pacienteSalvo);
    }

    public PacienteResponse busca(Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return mapper.toResponse(
                paciente.orElseThrow(
                        ()-> new EntityNotFoundException("Paciente não encontrado com o id: " + id)));

    }

    public PacienteResponse atualiza(Long id, PacienteRequest request) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Paciente não encontrado com o id: " + id)
                );

        mapper.atualizaPacienteDesdeRequest(paciente, request);

        paciente = pacienteRepository.save(paciente);
        return mapper.toResponse(paciente);
    }

    public void remove(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Paciente não encontrado com o id: " + id);
        }

        pacienteRepository.deleteById(id);
    }


    public PacienteResponsePagination lista(String nome, String telefone, String email, int numeroPagina, int tamanhoPagina) {
        Pageable paginacao = PageRequest.of(numeroPagina, tamanhoPagina);
        Page<Paciente> paginaPacientes;

        if (nome != null && telefone != null && email != null) {
            paginaPacientes = pacienteRepository.findByNomeIgnoreCaseContainingAndTelefoneContainingAndEmailContaining(nome, telefone, email, paginacao);
        } else if (nome != null) {
            paginaPacientes = pacienteRepository.findByNomeIgnoreCaseContaining(nome, paginacao);
        } else if (telefone != null) {
            paginaPacientes = pacienteRepository.findByTelefoneContaining(telefone, paginacao);
        } else if (email != null) {
            paginaPacientes = pacienteRepository.findByEmailContaining(email, paginacao);
        } else {
            paginaPacientes = pacienteRepository.findAll(paginacao);
        }

        List<PacienteGetRequest> conteudo =  paginaPacientes.getContent().stream()
                .map(mapper::getRequestToResponse)
                .collect(Collectors.toList());

        if(conteudo.isEmpty()){
            throw new EntityNotFoundException("Nenhum paciente encontrado");
        }

        PacienteResponsePagination response = new PacienteResponsePagination();
        response.setConteudo(conteudo);
        response.setTotalPaginas(paginaPacientes.getTotalPages());
        response.setTamanhoPagina(tamanhoPagina);
        response.setPaginaAtual(numeroPagina);
        response.setTotalElementos((int) paginaPacientes.getTotalElements());
        response.setUltima(paginaPacientes.isLast());

        return response;
    }


}
