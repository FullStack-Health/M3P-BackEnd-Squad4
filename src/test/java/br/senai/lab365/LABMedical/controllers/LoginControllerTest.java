package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.login.LoginRequest;
import br.senai.lab365.LABMedical.dtos.login.LoginResponse;
import br.senai.lab365.LABMedical.dtos.usuario.RedefinicaoSenhaRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroResponse;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import br.senai.lab365.LABMedical.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testCadastra_Success() {
        // Configurações
        UsuarioPreRegistroRequest request = new UsuarioPreRegistroRequest();
        UsuarioPreRegistroResponse expectedResponse = new UsuarioPreRegistroResponse(1L, "usuario@teste.com", "senha123", Collections.singletonList("PACIENTE"), "senha123****");

        when(usuarioService.cadastra(any(UsuarioPreRegistroRequest.class))).thenReturn(expectedResponse);

        // Executa o método
        UsuarioPreRegistroResponse response = loginController.cadastra(request);

        // Verifica o resultado
        assertNotNull(response);
        assertEquals("usuario@teste.com", response.getEmail());
        assertEquals(1L, response.getId());
    }

    @Test
    void testRedefineSenha_Success() {
        // Configurações
        String email = "usuario@teste.com";
        RedefinicaoSenhaRequest request = new RedefinicaoSenhaRequest("novaSenha123");
        UsuarioPreRegistroResponse expectedResponse = new UsuarioPreRegistroResponse(1L, email, "novaSenha123", Collections.singletonList("PACIENTE"), "nova****");

        when(usuarioService.redefine(anyString(), any(RedefinicaoSenhaRequest.class))).thenReturn(expectedResponse);

        // Executa o método
        UsuarioPreRegistroResponse response = loginController.redefineSenha(email, request);

        // Verifica o resultado
        assertNotNull(response);
        assertEquals("usuario@teste.com", response.getEmail());
        assertEquals("nova****", response.getSenhaComMascara());
    }
}
