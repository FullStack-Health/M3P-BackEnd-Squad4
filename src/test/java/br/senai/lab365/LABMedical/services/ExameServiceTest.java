package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.exame.ExameRequest;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.entities.Exame;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.mappers.ExameMapper;
import br.senai.lab365.LABMedical.repositories.ExameRepository;
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

class ExameServiceTest {

    @Mock
    private ExameRepository exameRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ExameMapper mapper;

    @InjectMocks
    private ExameService exameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastra_Success() {
        ExameRequest request = new ExameRequest();
        request.setIdPaciente(1L);

        Paciente paciente = new Paciente();
        Exame exame = new Exame();
        Exame exameSalvo = new Exame();
        ExameResponse response = new ExameResponse();

        when(pacienteRepository.findById(request.getIdPaciente())).thenReturn(Optional.of(paciente));
        when(mapper.toEntity(request, paciente)).thenReturn(exame);
        when(exameRepository.save(exame)).thenReturn(exameSalvo);
        when(mapper.toResponse(exameSalvo)).thenReturn(response);

        ExameResponse result = exameService.cadastra(request);

        verify(pacienteRepository, times(1)).findById(request.getIdPaciente());
        verify(exameRepository, times(1)).save(exame);
        assertEquals(response, result);
    }

    @Test
    void testCadastra_PacienteNaoEncontrado() {
        ExameRequest request = new ExameRequest();
        request.setIdPaciente(1L);

        when(pacienteRepository.findById(request.getIdPaciente())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> exameService.cadastra(request)
        );

        assertEquals("Paciente não encontrado como o id: 1", exception.getMessage());
    }

    @Test
    void testBusca_Success() {
        Long idExame = 1L;
        Exame exame = new Exame();
        ExameResponse response = new ExameResponse();

        when(exameRepository.findById(idExame)).thenReturn(Optional.of(exame));
        when(mapper.toResponse(exame)).thenReturn(response);

        ExameResponse result = exameService.busca(idExame);

        verify(exameRepository, times(1)).findById(idExame);
        assertEquals(response, result);
    }

    @Test
    void testBusca_ExameNaoEncontrado() {
        Long idExame = 1L;
        when(exameRepository.findById(idExame)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> exameService.busca(idExame)
        );

        assertEquals("Exame não encontrado com o id: 1", exception.getMessage());
    }

    @Test
    void testAtualiza_Success() {
        Long idExame = 1L;
        ExameRequest request = new ExameRequest();
        request.setIdPaciente(1L);

        Paciente paciente = new Paciente();
        Exame exame = new Exame();
        ExameResponse response = new ExameResponse();

        when(pacienteRepository.findById(request.getIdPaciente())).thenReturn(Optional.of(paciente));
        when(exameRepository.findById(idExame)).thenReturn(Optional.of(exame));
        when(exameRepository.save(any(Exame.class))).thenReturn(exame);
        when(mapper.toResponse(exame)).thenReturn(response);

        ExameResponse result = exameService.atualiza(idExame, request);

        verify(pacienteRepository, times(1)).findById(request.getIdPaciente());
        verify(exameRepository, times(1)).findById(idExame);
        verify(exameRepository, times(1)).save(exame);
        assertEquals(response, result);
    }

    @Test
    void testAtualiza_ExameNaoEncontrado() {
        Long idExame = 1L;
        ExameRequest request = new ExameRequest();
        request.setIdPaciente(1L);

        when(pacienteRepository.findById(request.getIdPaciente())).thenReturn(Optional.of(new Paciente()));
        when(exameRepository.findById(idExame)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> exameService.atualiza(idExame, request)
        );

        assertEquals("Exame não encontrada com o id: 1", exception.getMessage());
    }

    @Test
    void testRemove_Success() {
        Long idExame = 1L;

        when(exameRepository.existsById(idExame)).thenReturn(true);

        exameService.remove(idExame);

        verify(exameRepository, times(1)).deleteById(idExame);
    }

    @Test
    void testRemove_ExameNaoEncontrado() {
        Long idExame = 1L;
        when(exameRepository.existsById(idExame)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> exameService.remove(idExame)
        );

        assertEquals("Exame não encontrado com o id: 1", exception.getMessage());
    }
}
