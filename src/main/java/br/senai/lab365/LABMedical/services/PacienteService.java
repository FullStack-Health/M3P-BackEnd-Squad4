package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.EnderecoRequest;
import br.senai.lab365.LABMedical.dtos.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.PacienteResponse;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.mappers.PacienteMapper;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
