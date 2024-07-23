package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.EnderecoRequest;
import br.senai.lab365.LABMedical.dtos.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.PacienteResponse;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.mappers.PacienteMapper;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper mapper;

    public PacienteService(PacienteRepository pacienteRepository, PacienteMapper mapper) {
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    public PacienteResponse cadastra(PacienteRequest pacienteRequest) {
        // Converter o DTO PacienteRequest para a entidade Paciente
        Paciente paciente = mapper.toEntity(pacienteRequest);
        // Salvar a entidade Paciente
        Paciente pacienteSalvo = pacienteRepository.save(paciente);
        // Converter a entidade Paciente salva para o DTO PacienteResponse
        return mapper.toResponse(pacienteSalvo);
    }
}
