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

import java.util.Optional;

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

    public ConsultaResponse busca(Long id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        return mapper.toResponse(
                consulta.orElseThrow(
                        ()-> new EntityNotFoundException("Consulta não encontrado com o id: " + id)));
    }

    public ConsultaResponse atualiza(Long id, ConsultaRequest request) {
        if (request.getIdPaciente() == null) {
            throw new IllegalArgumentException("O id do paciente não pode ser nulo.");
        }

        Paciente paciente = pacienteRepository.findById(request.getIdPaciente())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado como o id: " + request.getIdPaciente()));

        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Consulta não encontrada com o id: " + id)
                );

        mapper.atualizaConsultaDesdeRequest(consulta, request, paciente);

        consulta = consultaRepository.save(consulta);
        return mapper.toResponse(consulta);
    }

    public void remove(Long id) {
        if(!consultaRepository.existsById(id)) {
            throw new EntityNotFoundException("Consulta não encontrada com o id: " + id);
        }
        consultaRepository.deleteById(id);
    }
}
