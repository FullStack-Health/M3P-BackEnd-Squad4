package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.login.LoginRequest;
import br.senai.lab365.LABMedical.dtos.usuario.*;
import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.mappers.UsuarioMapper;
import br.senai.lab365.LABMedical.mappers.UsuarioPreRegistroMapper;
import br.senai.lab365.LABMedical.repositories.PerfilRepository;
import br.senai.lab365.LABMedical.repositories.UsuarioRepository;
import br.senai.lab365.LABMedical.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private UsuarioPreRegistroMapper preRegistroMapper;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastra_RetornaUsuarioPreRegistroResponse() {
        // Arrange
        UsuarioPreRegistroRequest request = new UsuarioPreRegistroRequest();
        Usuario usuario = new Usuario();
        Perfil perfil = new Perfil();
        perfil.setNomePerfil("ADMIN");

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(perfilRepository.findByNomePerfil(request.getNomePerfil())).thenReturn(perfil);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("senhaCodificada");
        when(preRegistroMapper.toEntity(request)).thenReturn(usuario);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(preRegistroMapper.toResponse(usuario)).thenReturn(new UsuarioPreRegistroResponse());

        // Act
        UsuarioPreRegistroResponse response = usuarioService.cadastra(request);

        // Assert
        assertNotNull(response);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void cadastra_LancaExceptionParaEmailDuplicado() {
        // Arrange
        UsuarioPreRegistroRequest request = new UsuarioPreRegistroRequest();
        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateKeyException.class, () -> usuarioService.cadastra(request));
    }

    @Test
    void cadastra_LancaExceptionParaPerfilInvalido() {
        // Arrange
        UsuarioPreRegistroRequest request = new UsuarioPreRegistroRequest();
        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(perfilRepository.findByNomePerfil(request.getNomePerfil())).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> usuarioService.cadastra(request));
    }

    @Test
    void validaUsuario_RetornaUsuarioQuandoCredenciaisValidas() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("user@example.com", "senha123");
        Usuario usuario = new Usuario();
        usuario.setPassword("senhaCodificada");

        when(usuarioRepository.findByEmailIgnoreCaseContaining(loginRequest.email())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(loginRequest.password(), usuario.getPassword())).thenReturn(true);

        // Act
        Usuario result = usuarioService.validaUsuario(loginRequest);

        // Assert
        assertNotNull(result);
        verify(usuarioRepository, times(1)).findByEmailIgnoreCaseContaining(loginRequest.email());
    }

    @Test
    void validaUsuario_LancaExceptionParaSenhaInvalida() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("user@example.com", "senhaInvalida");
        Usuario usuario = new Usuario();
        usuario.setPassword("senhaCodificada");

        when(usuarioRepository.findByEmailIgnoreCaseContaining(loginRequest.email())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(loginRequest.password(), usuario.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> usuarioService.validaUsuario(loginRequest));
    }

    @Test
    void validaUsuario_LancaExceptionParaEmailNaoEncontrado() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("inexistente@example.com", "senha123");
        when(usuarioRepository.findByEmailIgnoreCaseContaining(loginRequest.email())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> usuarioService.validaUsuario(loginRequest));
    }

    @Test
    void redefine_RetornaUsuarioPreRegistroResponseParaSenhaAtualizada() {
        // Arrange
        String email = "user@example.com";
        RedefinicaoSenhaRequest request = new RedefinicaoSenhaRequest("novaSenha123");
        Usuario usuario = new Usuario();

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("senhaNovaCodificada");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(preRegistroMapper.toResponse(usuario)).thenReturn(new UsuarioPreRegistroResponse());

        // Act
        UsuarioPreRegistroResponse response = usuarioService.redefine(email, request);

        // Assert
        assertNotNull(response);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void redefine_LancaExceptionParaUsuarioNaoEncontrado() {
        // Arrange
        String email = "inexistente@example.com";
        RedefinicaoSenhaRequest request = new RedefinicaoSenhaRequest("novaSenha123");

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> usuarioService.redefine(email, request));
    }

    @Test
    void remove_DeletaUsuarioQuandoExistente() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario();

        when(usuarioRepository.existsById(id)).thenReturn(true);
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Act
        usuarioService.remove(id);

        // Assert
        verify(usuarioRepository, times(1)).deleteById(id);
    }

    @Test
    void remove_LancaExceptionParaUsuarioNaoEncontrado() {
        // Arrange
        Long id = 1L;
        when(usuarioRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> usuarioService.remove(id));
    }
}




