package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.ProntuarioResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.SummaryResponsePagination;
import br.senai.lab365.LABMedical.services.ProntuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProntuarioControllerTest {

    @Mock
    private ProntuarioService prontuarioService;

    @InjectMocks
    private ProntuarioController prontuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLista() {
        Long idPaciente = 1L;
        SummaryResponsePagination mockResponse = new SummaryResponsePagination(); // Crie uma instância adequada de SummaryResponsePagination
        when(prontuarioService.lista(any(), any(), anyInt(), anyInt())).thenReturn(mockResponse);

        SummaryResponsePagination response = prontuarioController.lista(idPaciente, null, 0, 10);

        verify(prontuarioService, times(1)).lista(idPaciente, null, 0, 10);
        assert response.equals(mockResponse); // Verifica se a resposta é a esperada
    }

    @Test
    void testBusca() {
        Long idPaciente = 1L;
        ProntuarioResponse mockResponse = new ProntuarioResponse(); // Crie uma instância adequada de ProntuarioResponse
        when(prontuarioService.busca(anyLong())).thenReturn(mockResponse);

        ProntuarioResponse response = prontuarioController.busca(idPaciente);

        verify(prontuarioService, times(1)).busca(idPaciente);
        assert response.equals(mockResponse); // Verifica se a resposta é a esperada
    }

    @Test
    void testListaTodosExamesPaciente() {
        Long idPaciente = 1L;
        List<ExameResponse> mockResponse = Collections.emptyList(); // Ou crie uma lista com dados de exemplo
        when(prontuarioService.listaTodosExamesPaciente(anyLong())).thenReturn(mockResponse);

        List<ExameResponse> response = prontuarioController.listaTodosExamesPaciente(idPaciente);

        verify(prontuarioService, times(1)).listaTodosExamesPaciente(idPaciente);
        assert response.equals(mockResponse); // Verifica se a resposta é a esperada
    }

    @Test
    void testListaTodasConsultasPaciente() {
        Long idPaciente = 1L;
        List<ConsultaResponse> mockResponse = Collections.emptyList(); // Ou crie uma lista com dados de exemplo
        when(prontuarioService.listaTodasConsultasPaciente(anyLong())).thenReturn(mockResponse);

        List<ConsultaResponse> response = prontuarioController.listaTodasConsultasPaciente(idPaciente);

        verify(prontuarioService, times(1)).listaTodasConsultasPaciente(idPaciente);
        assert response.equals(mockResponse); // Verifica se a resposta é a esperada
    }
}
