package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.prontuario.PacienteSummaryRequest;
import br.senai.lab365.LABMedical.dtos.prontuario.ProntuarioResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.SummaryResponsePagination;
import br.senai.lab365.LABMedical.entities.Consulta;
import br.senai.lab365.LABMedical.entities.Exame;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.mappers.ProntuarioMapper;
import br.senai.lab365.LABMedical.repositories.ConsultaRepository;
import br.senai.lab365.LABMedical.repositories.ExameRepository;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProntuarioService {

    private final PacienteRepository pacienteRepository;
    private final ProntuarioMapper mapper;
    private final ExameRepository exameRepository;
    private final ConsultaRepository consultaRepository;

    public ProntuarioService(PacienteRepository pacienteRepository, ExameRepository exameRepository, ConsultaRepository consultaRepository, ProntuarioMapper mapper) {
        this.pacienteRepository = pacienteRepository;
        this.exameRepository = exameRepository;
        this.consultaRepository = consultaRepository;
        this.mapper = mapper;
    }


    public SummaryResponsePagination lista(Long idPaciente, String nome, int numeroPagina, int tamanhoPagina) {
        Pageable paginacao = PageRequest.of(numeroPagina, tamanhoPagina);
        Page<Paciente> paginaPacientes;

        if (nome != null && idPaciente != null) {
            paginaPacientes = pacienteRepository.findByIdAndNomeIgnoreCaseContaining(idPaciente, nome, paginacao);
        } else if (nome != null) {
            paginaPacientes = pacienteRepository.findByNomeIgnoreCaseContaining(nome, paginacao);
        } else {
            paginaPacientes = pacienteRepository.findAll(paginacao);
        }

        if (idPaciente != null) {
            Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow(
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

    public ProntuarioResponse busca(Long idPaciente) {
        Paciente paciente = pacienteRepository.findById(idPaciente).orElseThrow(
                () -> new EntityNotFoundException("Paciente não encontrado com o id: " + idPaciente)
        );

        List<Exame> exames = exameRepository.findByPacienteId(idPaciente);
        List<Consulta> consultas = consultaRepository.findByPacienteId(idPaciente);

           return mapper.getPacienteToProntuario(paciente, exames, consultas);
    }


}
