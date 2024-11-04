package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.exame.ExameRequest;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.services.ExameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ExameControllerTest {

    @InjectMocks
    private ExameController exameController;

    @Mock
    private ExameService exameService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastra() {
        // Arrange
        ExameRequest exameRequest = new ExameRequest();
        ExameResponse exameResponse = new ExameResponse();
        when(exameService.cadastra(exameRequest)).thenReturn(exameResponse);

        // Act
        ExameResponse result = exameController.cadastra(exameRequest);

        // Assert
        assertEquals(exameResponse, result);
        verify(exameService, times(1)).cadastra(exameRequest);
    }

    @Test
    public void testBusca() {
        // Arrange
        Long exameId = 1L;
        ExameResponse exameResponse = new ExameResponse();
        when(exameService.busca(exameId)).thenReturn(exameResponse);

        // Act
        ExameResponse result = exameController.busca(exameId);

        // Assert
        assertEquals(exameResponse, result);
        verify(exameService, times(1)).busca(exameId);
    }

    @Test
    public void testAtualiza() {
        // Arrange
        Long exameId = 1L;
        ExameRequest exameRequest = new ExameRequest();
        ExameResponse exameResponse = new ExameResponse();
        when(exameService.atualiza(eq(exameId), any(ExameRequest.class))).thenReturn(exameResponse);

        // Act
        ExameResponse result = exameController.atualiza(exameId, exameRequest);

        // Assert
        assertEquals(exameResponse, result);
        verify(exameService, times(1)).atualiza(eq(exameId), any(ExameRequest.class));
    }

    @Test
    public void testRemove() {
        // Arrange
        Long exameId = 1L;

        // Act
        exameController.remove(exameId);

        // Assert
        verify(exameService, times(1)).remove(exameId);
    }
}
