package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponse;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponsePagination;
import br.senai.lab365.LABMedical.services.PacienteService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PacienteControllerTest {

    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private PacienteController pacienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastra() {
        PacienteRequest request = new PacienteRequest(); // Crie uma instância adequada de PacienteRequest
        PacienteResponse mockResponse = new PacienteResponse(); // Crie uma instância adequada de PacienteResponse
        when(pacienteService.cadastra(any())).thenReturn(mockResponse);

        ResponseEntity<?> response = pacienteController.cadastra(request);

        verify(pacienteService, times(1)).cadastra(request);
        assert response.getStatusCode() == HttpStatus.CREATED; // Verifica o status 201
        assert response.getBody().equals(mockResponse); // Verifica se a resposta é a esperada
    }

    @Test
    void testCadastra_DataIntegrityViolation() {
        PacienteRequest request = new PacienteRequest(); // Crie uma instância adequada de PacienteRequest
        when(pacienteService.cadastra(any())).thenThrow(new DataIntegrityViolationException("CPF já cadastrado"));

        ResponseEntity<?> response = pacienteController.cadastra(request);

        verify(pacienteService, times(1)).cadastra(request);
        assert response.getStatusCode() == HttpStatus.CONFLICT; // Verifica o status 409
        assert response.getBody().equals("CPF já cadastrado!"); // Verifica a mensagem de erro
    }

    @Test
    void testBusca() {
        Long pacienteId = 1L;
        PacienteResponse mockResponse = new PacienteResponse(); // Crie uma instância adequada de PacienteResponse
        when(pacienteService.existePaciente(anyLong())).thenReturn(true);
        when(pacienteService.busca(pacienteId)).thenReturn(mockResponse);

        ResponseEntity<?> response = pacienteController.busca(pacienteId);

        verify(pacienteService, times(1)).busca(pacienteId);
        assert response.getStatusCode() == HttpStatus.OK; // Verifica o status 200
        assert response.getBody().equals(mockResponse); // Verifica se a resposta é a esperada
    }

    @Test
    void testBusca_PacienteNaoEncontrado() {
        Long pacienteId = 1L;
        when(pacienteService.existePaciente(anyLong())).thenReturn(false);

        ResponseEntity<?> response = pacienteController.busca(pacienteId);

        assert response.getStatusCode() == HttpStatus.NOT_FOUND; // Verifica o status 404
        assert response.getBody().equals("Paciente não encontrado."); // Verifica a mensagem de erro
    }

    @Test
    void testAtualiza() {
        Long pacienteId = 1L;
        PacienteRequest request = new PacienteRequest(); // Crie uma instância adequada de PacienteRequest
        PacienteResponse mockResponse = new PacienteResponse(); // Crie uma instância adequada de PacienteResponse
        when(pacienteService.existePaciente(anyLong())).thenReturn(true);
        when(pacienteService.atualiza(anyLong(), any())).thenReturn(mockResponse);

        ResponseEntity<?> response = pacienteController.atualiza(pacienteId, request);

        verify(pacienteService, times(1)).atualiza(pacienteId, request);
        assert response.getStatusCode() == HttpStatus.OK; // Verifica o status 200
        assert response.getBody().equals(mockResponse); // Verifica se a resposta é a esperada
    }

    @Test
    void testAtualiza_PacienteNaoEncontrado() {
        Long pacienteId = 1L;
        PacienteRequest request = new PacienteRequest(); // Crie uma instância adequada de PacienteRequest
        when(pacienteService.existePaciente(anyLong())).thenReturn(false);

        ResponseEntity<?> response = pacienteController.atualiza(pacienteId, request);

        assert response.getStatusCode() == HttpStatus.NOT_FOUND; // Verifica o status 404
        assert response.getBody().equals("Paciente não encontrado."); // Verifica a mensagem de erro
    }

    @Test
    void testRemove() {
        Long pacienteId = 1L;
        when(pacienteService.existePaciente(anyLong())).thenReturn(true);

        ResponseEntity<String> response = pacienteController.remove(pacienteId);

        verify(pacienteService, times(1)).remove(pacienteId);
        assert response.getStatusCode() == HttpStatus.OK; // Verifica o status 200
        assert response.getBody().equals("Paciente excluído com sucesso."); // Verifica a mensagem de sucesso
    }

    @Test
    void testRemove_PacienteNaoEncontrado() {
        Long pacienteId = 1L;
        when(pacienteService.existePaciente(anyLong())).thenReturn(false);

        ResponseEntity<String> response = pacienteController.remove(pacienteId);

        assert response.getStatusCode() == HttpStatus.NOT_FOUND; // Verifica o status 404
        assert response.getBody().equals("Paciente não encontrado."); // Verifica a mensagem de erro
    }

    @Test
    void testLista() {
        PacienteResponsePagination mockResponse = new PacienteResponsePagination(); // Crie uma instância adequada de PacienteResponsePagination
        when(pacienteService.lista(anyLong(), any(), any(), any(), anyInt(), anyInt())).thenReturn(mockResponse);

        ResponseEntity<?> response = pacienteController.lista(null, null, null, null, 0, 10);

        verify(pacienteService, times(1)).lista(null, null, null, null, 0, 10);
        assert response.getStatusCode() == HttpStatus.OK; // Verifica o status 200
        assert response.getBody().equals(mockResponse); // Verifica se a resposta é a esperada
    }
}
