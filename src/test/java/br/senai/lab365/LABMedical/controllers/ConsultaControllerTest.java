package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaRequest;
import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.services.ConsultaService;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ConsultaControllerTest {

    @InjectMocks
    private ConsultaController consultaController;

    @Mock
    private ConsultaService consultaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastra() {
        // Arrange
        ConsultaRequest request = new ConsultaRequest();
        ConsultaResponse response = new ConsultaResponse();
        when(consultaService.cadastra(any(ConsultaRequest.class))).thenReturn(response);

        // Act
        ConsultaResponse result = consultaController.cadastra(request);

        // Assert
        assertEquals(response, result);
        verify(consultaService, times(1)).cadastra(request);
    }

    @Test
    public void testBusca() {
        // Arrange
        Long id = 1L;
        ConsultaResponse response = new ConsultaResponse();
        when(consultaService.busca(id)).thenReturn(response);

        // Act
        ConsultaResponse result = consultaController.busca(id);

        // Assert
        assertEquals(response, result);
        verify(consultaService, times(1)).busca(id);
    }

    @Test
    public void testAtualiza() {
        // Arrange
        Long id = 1L;
        ConsultaRequest request = new ConsultaRequest();
        ConsultaResponse response = new ConsultaResponse();
        when(consultaService.atualiza(eq(id), any(ConsultaRequest.class))).thenReturn(response);

        // Act
        ConsultaResponse result = consultaController.atualiza(id, request);

        // Assert
        assertEquals(response, result);
        verify(consultaService, times(1)).atualiza(id, request);
    }

    @Test
    public void testRemove() {
        // Arrange
        Long id = 1L;

        // Act
        consultaController.remove(id);

        // Assert
        verify(consultaService, times(1)).remove(id);
    }
}
