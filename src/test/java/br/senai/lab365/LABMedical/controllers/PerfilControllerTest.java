package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.services.PerfilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class PerfilControllerTest {

    @Mock
    private PerfilService perfilService;

    @InjectMocks
    private PerfilController perfilController;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPerfilControllerInitialization() {
        // Testa se o controlador é inicializado corretamente
        assert perfilController != null;
    }


}
