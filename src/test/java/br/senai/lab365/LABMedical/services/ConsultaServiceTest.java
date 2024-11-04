package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaRequest;
import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.entities.Consulta;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.mappers.ConsultaMapper;
import br.senai.lab365.LABMedical.repositories.ConsultaRepository;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ConsultaServiceTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ConsultaMapper mapper;

    @InjectMocks
    private ConsultaService consultaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastra() {
        ConsultaRequest request = new ConsultaRequest();
        request.setIdPaciente(1L);

        Paciente mockPaciente = new Paciente();
        Consulta mockConsulta = new Consulta();
        ConsultaResponse mockResponse = new ConsultaResponse();

        when(pacienteRepository.findById(request.getIdPaciente())).thenReturn(Optional.of(mockPaciente));
        when(mapper.toEntity(request, mockPaciente)).thenReturn(mockConsulta);
        when(consultaRepository.save(mockConsulta)).thenReturn(mockConsulta);
        when(mapper.toResponse(mockConsulta)).thenReturn(mockResponse);

        ConsultaResponse response = consultaService.cadastra(request);

        verify(pacienteRepository, times(1)).findById(request.getIdPaciente());
        verify(consultaRepository, times(1)).save(mockConsulta);
        assertEquals(mockResponse, response);
    }

    @Test
    void testCadastraPacienteNotFound() {
        ConsultaRequest request = new ConsultaRequest();
        request.setIdPaciente(1L);

        when(pacienteRepository.findById(request.getIdPaciente())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> consultaService.cadastra(request));

        assertEquals("Paciente não encontrado como o id: " + request.getIdPaciente(), exception.getMessage());
    }

    @Test
    void testBusca() {
        Consulta mockConsulta = new Consulta();
        ConsultaResponse mockResponse = new ConsultaResponse();

        when(consultaRepository.findById(anyLong())).thenReturn(Optional.of(mockConsulta));
        when(mapper.toResponse(mockConsulta)).thenReturn(mockResponse);

        ConsultaResponse response = consultaService.busca(1L);

        verify(consultaRepository, times(1)).findById(1L);
        assertEquals(mockResponse, response);
    }

    @Test
    void testBuscaConsultaNotFound() {
        when(consultaRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> consultaService.busca(1L));

        assertEquals("Consulta não encontrado com o id: 1", exception.getMessage());
    }

    @Test
    void testAtualiza() {
        ConsultaRequest request = new ConsultaRequest();
        request.setIdPaciente(1L);

        Paciente mockPaciente = new Paciente();
        Consulta mockConsulta = new Consulta();
        ConsultaResponse mockResponse = new ConsultaResponse();

        when(pacienteRepository.findById(request.getIdPaciente())).thenReturn(Optional.of(mockPaciente));
        when(consultaRepository.findById(anyLong())).thenReturn(Optional.of(mockConsulta));
        doNothing().when(mapper).atualizaConsultaDesdeRequest(mockConsulta, request, mockPaciente);
        when(consultaRepository.save(mockConsulta)).thenReturn(mockConsulta);
        when(mapper.toResponse(mockConsulta)).thenReturn(mockResponse);

        ConsultaResponse response = consultaService.atualiza(1L, request);

        verify(pacienteRepository, times(1)).findById(request.getIdPaciente());
        verify(consultaRepository, times(1)).findById(1L);
        verify(consultaRepository, times(1)).save(mockConsulta);
        assertEquals(mockResponse, response);
    }

    @Test
    void testAtualizaConsultaNotFound() {
        ConsultaRequest request = new ConsultaRequest();
        request.setIdPaciente(1L);

        when(pacienteRepository.findById(request.getIdPaciente())).thenReturn(Optional.of(new Paciente()));
        when(consultaRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> consultaService.atualiza(1L, request));

        assertEquals("Consulta não encontrada com o id: 1", exception.getMessage());
    }

    @Test
    void testRemove() {
        when(consultaRepository.existsById(anyLong())).thenReturn(true);

        consultaService.remove(1L);

        verify(consultaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRemoveConsultaNotFound() {
        when(consultaRepository.existsById(anyLong())).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> consultaService.remove(1L));

        assertEquals("Consulta não encontrada com o id: 1", exception.getMessage());
    }
}
