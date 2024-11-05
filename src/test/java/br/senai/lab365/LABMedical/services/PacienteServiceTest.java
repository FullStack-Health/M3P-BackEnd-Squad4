package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.paciente.PacienteGetRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponse;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponsePagination;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.exceptions.CpfDuplicadoException;
import br.senai.lab365.LABMedical.mappers.PacienteMapper;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import br.senai.lab365.LABMedical.repositories.UsuarioRepository;
import br.senai.lab365.LABMedical.services.PacienteService;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PacienteMapper pacienteMapper;

    @InjectMocks
    private PacienteService pacienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void cadastraPacienteComSucesso() {
//        PacienteRequest pacienteRequest = new PacienteRequest();
//        pacienteRequest.setCpf("12345678900");
//        Paciente paciente = new Paciente();
//        PacienteResponse pacienteResponse = new PacienteResponse();
//
//        when(pacienteRepository.existsByCpf("12345678900")).thenReturn(false);
//        when(pacienteMapper.toEntity(pacienteRequest)).thenReturn(paciente);
//        when(usuarioRepository.save(paciente.getUsuario())).thenReturn(paciente.getUsuario());
//        when(pacienteRepository.save(paciente)).thenReturn(paciente);
//        when(pacienteMapper.toResponse(paciente)).thenReturn(pacienteResponse);
//
//        PacienteResponse result = pacienteService.cadastra(pacienteRequest);
//
//        assertNotNull(result);
//        verify(pacienteRepository).save(paciente);
//    }

    @Test
    void cadastraPacienteComCpfDuplicado() {
        PacienteRequest pacienteRequest = new PacienteRequest();
        pacienteRequest.setCpf("12345678900");

        when(pacienteRepository.existsByCpf("12345678900")).thenReturn(true);

        assertThrows(CpfDuplicadoException.class, () -> pacienteService.cadastra(pacienteRequest));
    }

    @Test
    void buscaPacientePorIdExistente() {
        Paciente paciente = new Paciente();
        PacienteResponse pacienteResponse = new PacienteResponse();

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(pacienteMapper.toResponse(paciente)).thenReturn(pacienteResponse);

        PacienteResponse result = pacienteService.busca(1L);

        assertNotNull(result);
    }

    @Test
    void buscaPacientePorIdInexistente() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pacienteService.busca(1L));
    }

    @Test
    void atualizaPacienteExistente() {
        Paciente paciente = new Paciente();
        PacienteRequest request = new PacienteRequest();
        PacienteResponse response = new PacienteResponse();

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        doNothing().when(pacienteMapper).atualizaPacienteDesdeRequest(paciente, request);
        when(pacienteRepository.save(paciente)).thenReturn(paciente);
        when(pacienteMapper.toResponse(paciente)).thenReturn(response);

        PacienteResponse result = pacienteService.atualiza(1L, request);

        assertNotNull(result);
        verify(pacienteRepository).save(paciente);
    }

    @Test
    void atualizaPacienteInexistente() {
        PacienteRequest request = new PacienteRequest();

        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pacienteService.atualiza(1L, request));
    }

//    @Test
//    void removePacienteExistente() {
//        Paciente paciente = new Paciente();
//        Usuario usuario = new Usuario();
//        paciente.setUsuario(usuario);
//
//        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
//
//        pacienteService.remove(1L);
//
//        verify(usuarioRepository).save(usuario);
//        verify(pacienteRepository).delete(paciente);
//    }

    @Test
    void removePacienteInexistente() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pacienteService.remove(1L));
    }

    @Test
    void listaPacientesComPaginacao() {
        PageRequest paginacao = PageRequest.of(0, 10);
        Paciente paciente = new Paciente();
        List<Paciente> pacientesList = List.of(paciente);
        Page<Paciente> paginaPacientes = new PageImpl<>(pacientesList, paginacao, pacientesList.size());

        when(pacienteRepository.findAll(paginacao)).thenReturn(paginaPacientes);
        when(pacienteMapper.getRequestToResponse(any())).thenReturn(new PacienteGetRequest());

        PacienteResponsePagination response = pacienteService.lista(null, null, null, null, 0, 10);

        assertNotNull(response);
        assertEquals(1, response.getTotalElementos());
    }
}





