package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.usuario.UsuarioRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioResponse;
import br.senai.lab365.LABMedical.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBusca() {
        Long usuarioId = 1L;
        UsuarioResponse mockResponse = new UsuarioResponse(); // Crie uma instância adequada
        when(usuarioService.busca(usuarioId)).thenReturn(mockResponse);

        UsuarioResponse response = usuarioController.busca(usuarioId);

        verify(usuarioService, times(1)).busca(usuarioId);
        assert response.equals(mockResponse); // Verifique se a resposta é a esperada
    }

    @Test
    void testBuscaPorIdOuEmailComId() {
        Long usuarioId = 1L;
        List<UsuarioResponse> mockResponse = Collections.singletonList(new UsuarioResponse()); // Crie uma lista adequada
        when(usuarioService.lista(usuarioId, null)).thenReturn(mockResponse);

        List<UsuarioResponse> response = usuarioController.buscaPorIdOuEmail(usuarioId, null);

        verify(usuarioService, times(1)).lista(usuarioId, null);
        assert response.equals(mockResponse); // Verifique se a resposta é a esperada
    }

    @Test
    void testBuscaPorPerfil() {
        String perfil = "ADMIN";
        List<UsuarioResponse> mockResponse = Collections.singletonList(new UsuarioResponse()); // Crie uma lista adequada
        when(usuarioService.buscaPorPerfil(perfil)).thenReturn(mockResponse);

        List<UsuarioResponse> response = usuarioController.buscaPorPerfil(perfil);

        verify(usuarioService, times(1)).buscaPorPerfil(perfil);
        assert response.equals(mockResponse); // Verifique se a resposta é a esperada
    }

    @Test
    void testAtualiza() {
        Long usuarioId = 1L;
        UsuarioRequest request = new UsuarioRequest(); // Crie uma instância adequada
        UsuarioResponse mockResponse = new UsuarioResponse(); // Crie uma instância adequada
        when(usuarioService.atualiza(usuarioId, request)).thenReturn(mockResponse);

        UsuarioResponse response = usuarioController.atualiza(usuarioId, request);

        verify(usuarioService, times(1)).atualiza(usuarioId, request);
        assert response.equals(mockResponse); // Verifique se a resposta é a esperada
    }

    @Test
    void testRemove() {
        Long usuarioId = 1L;

        usuarioController.remove(usuarioId);

        verify(usuarioService, times(1)).remove(usuarioId);
    }
}
