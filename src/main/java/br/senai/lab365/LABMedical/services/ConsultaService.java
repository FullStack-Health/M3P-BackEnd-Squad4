package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaRequest;
import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.entities.Consulta;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.mappers.ConsultaMapper;
import br.senai.lab365.LABMedical.repositories.ConsultaRepository;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaMapper mapper;

    public ConsultaService(ConsultaRepository consultaRepository, PacienteRepository pacienteRepository, ConsultaMapper mapper) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    public ConsultaResponse cadastra(ConsultaRequest request) {
        Paciente paciente = pacienteRepository.findById(request.getIdPaciente())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado como o id: " + request.getIdPaciente()));
        Consulta consulta = mapper.toEntity(request, paciente);
        Consulta consultaSalva = consultaRepository.save(consulta);
        return mapper.toResponse(consultaSalva);

    }
}
