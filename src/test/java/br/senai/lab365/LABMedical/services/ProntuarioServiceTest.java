package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
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
import br.senai.lab365.LABMedical.services.ProntuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProntuarioServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ExameRepository exameRepository;

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private ProntuarioMapper prontuarioMapper;

    @InjectMocks
    private ProntuarioService prontuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void lista_RetornaPacientesComPaginacao() {
//        // Arrange
//        Long idPaciente = 1L;
//        String nome = "João";
//        Pageable pageable = PageRequest.of(0, 10);
//        Paciente paciente = new Paciente();
//        Page<Paciente> page = new PageImpl<>(Collections.singletonList(paciente));
//
//        when(pacienteRepository.findByIdAndNomeIgnoreCaseContaining(idPaciente, nome, pageable)).thenReturn(page);
//        when(prontuarioMapper.getRequestToResponse(paciente)).thenReturn(new PacienteSummaryRequest());
//
//        // Act
//        SummaryResponsePagination response = prontuarioService.lista(idPaciente, nome, 0, 10);
//
//        // Assert
//        assertNotNull(response);
//        assertFalse(response.getConteudo().isEmpty());
//        verify(pacienteRepository, times(1)).findByIdAndNomeIgnoreCaseContaining(idPaciente, nome, pageable);
//    }

    @Test
    void lista_LancaExceptionQuandoNenhumPacienteEncontrado() {
        // Arrange
        Long idPaciente = 1L;
        String nome = "João";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Paciente> emptyPage = new PageImpl<>(Collections.emptyList());

        when(pacienteRepository.findByIdAndNomeIgnoreCaseContaining(idPaciente, nome, pageable)).thenReturn(emptyPage);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> prontuarioService.lista(idPaciente, nome, 0, 10));
    }

    @Test
    void busca_RetornaProntuarioDoPaciente() {
        // Arrange
        Long idPaciente = 1L;
        Paciente paciente = new Paciente();
        List<Exame> exames = Collections.singletonList(new Exame());
        List<Consulta> consultas = Collections.singletonList(new Consulta());

        when(pacienteRepository.findById(idPaciente)).thenReturn(Optional.of(paciente));
        when(exameRepository.findByPacienteId(idPaciente)).thenReturn(exames);
        when(consultaRepository.findByPacienteId(idPaciente)).thenReturn(consultas);
        when(prontuarioMapper.getPacienteToProntuario(paciente, exames, consultas)).thenReturn(new ProntuarioResponse());

        // Act
        ProntuarioResponse response = prontuarioService.busca(idPaciente);

        // Assert
        assertNotNull(response);
        verify(pacienteRepository, times(1)).findById(idPaciente);
        verify(exameRepository, times(1)).findByPacienteId(idPaciente);
        verify(consultaRepository, times(1)).findByPacienteId(idPaciente);
    }

    @Test
    void busca_LancaExceptionQuandoPacienteNaoEncontrado() {
        // Arrange
        Long idPaciente = 1L;
        when(pacienteRepository.findById(idPaciente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> prontuarioService.busca(idPaciente));
    }

    @Test
    void listaTodosExamesPaciente_RetornaListaDeExames() {
        // Arrange
        Long idPaciente = 1L;
        Paciente paciente = new Paciente();
        List<Exame> exames = Collections.singletonList(new Exame());

        when(pacienteRepository.findById(idPaciente)).thenReturn(Optional.of(paciente));
        when(exameRepository.findByPacienteId(idPaciente)).thenReturn(exames);
        when(prontuarioMapper.examesToResponse(exames)).thenReturn(Collections.singletonList(new ExameResponse()));

        // Act
        List<ExameResponse> response = prontuarioService.listaTodosExamesPaciente(idPaciente);

        // Assert
        assertNotNull(response);
        assertFalse(response.isEmpty());
        verify(exameRepository, times(1)).findByPacienteId(idPaciente);
    }

    @Test
    void listaTodasConsultasPaciente_RetornaListaDeConsultas() {
        // Arrange
        Long idPaciente = 1L;
        Paciente paciente = new Paciente();
        List<Consulta> consultas = Collections.singletonList(new Consulta());

        when(pacienteRepository.findById(idPaciente)).thenReturn(Optional.of(paciente));
        when(consultaRepository.findByPacienteId(idPaciente)).thenReturn(consultas);
        when(prontuarioMapper.consultasToResponse(consultas)).thenReturn(Collections.singletonList(new ConsultaResponse()));

        // Act
        List<ConsultaResponse> response = prontuarioService.listaTodasConsultasPaciente(idPaciente);

        // Assert
        assertNotNull(response);
        assertFalse(response.isEmpty());
        verify(consultaRepository, times(1)).findByPacienteId(idPaciente);
    }
}




