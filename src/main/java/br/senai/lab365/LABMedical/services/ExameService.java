package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.exame.ExameRequest;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.entities.Exame;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.mappers.ExameMapper;
import br.senai.lab365.LABMedical.repositories.ExameRepository;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExameService {

    private final ExameRepository exameRepository;
    private final PacienteRepository pacienteRepository;
    private final ExameMapper mapper;


    public ExameService(ExameRepository exameRepository, PacienteRepository pacienteRepository, ExameMapper mapper) {
        this.exameRepository = exameRepository;
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    public ExameResponse cadastra(ExameRequest request) {
        if (request.getIdPaciente() == null) {
            throw new IllegalArgumentException("O id do paciente não pode ser nulo.");
        }

        Paciente paciente = pacienteRepository.findById(request.getIdPaciente())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado como o id: " + request.getIdPaciente()));

        Exame exame = mapper.toEntity(request, paciente);
        Exame exameSalvo = exameRepository.save(exame);
        return mapper.toResponse(exameSalvo);
    }

    public ExameResponse busca(Long id) {
        Optional<Exame> exame = exameRepository.findById(id);
        return mapper.toResponse(
                exame.orElseThrow(
                        ()-> new EntityNotFoundException("Exame não encontrado com o id: " + id)));
    }


    public ExameResponse atualiza(Long id, ExameRequest request) {
        if (request.getIdPaciente() == null) {
            throw new IllegalArgumentException("O id do paciente não pode ser nulo.");
        }

        Paciente paciente = pacienteRepository.findById(request.getIdPaciente())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado como o id: " + request.getIdPaciente()));

        Exame exame = exameRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Exame não encontrada com o id: " + id)
                );

        mapper.atualizaExameDesdeRequest(exame, request, paciente);

        exame = exameRepository.save(exame);
        return mapper.toResponse(exame);
    }

    public void remove(Long id) {
        if (!exameRepository.existsById(id)) {
            throw new EntityNotFoundException("Exame não encontrado com o id: " + id);
        }

        exameRepository.deleteById(id);
    }
}
