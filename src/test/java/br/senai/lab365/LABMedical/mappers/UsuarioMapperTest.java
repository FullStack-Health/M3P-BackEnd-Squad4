package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.usuario.UsuarioRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioResponse;
import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.repositories.PerfilRepository;
import br.senai.lab365.LABMedical.services.PerfilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioMapperTest {

    @InjectMocks
    private UsuarioMapper usuarioMapper;

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private PerfilService perfilService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toEntity_DeveRetornarUsuarioQuandoRequestValido() {
        // Arrange
        UsuarioRequest request = new UsuarioRequest(
        );

        Perfil perfil = new Perfil(); // Cria um objeto Perfil
        perfil.setNomePerfil("Admin");

        when(perfilRepository.findByNomePerfil("Admin")).thenReturn(perfil);

        // Act
        Usuario usuario = usuarioMapper.toEntity(request);

        // Assert
        assertNotNull(usuario);
        assertEquals(request.getNome(), usuario.getNome());
        assertEquals(request.getEmail(), usuario.getEmail());
        assertEquals(request.getDataNascimento(), usuario.getDataNascimento());
        assertEquals(request.getCpf(), usuario.getCpf());
        assertEquals(request.getTelefone(), usuario.getTelefone());
        assertEquals(request.getPassword(), usuario.getPassword());
        assertEquals(1, usuario.getPerfilList().size());
        assertTrue(usuario.getPerfilList().contains(perfil));
        assertEquals("senha****", usuario.getSenhaComMascara());
    }

    @Test
    void toEntity_DeveRetornarNuloQuandoRequestNulo() {
        // Act & Assert
        assertNull(usuarioMapper.toEntity(null));
    }

    @Test
    void toResponse_DeveRetornarUsuarioResponseQuandoUsuarioValido() {
        // Arrange
        Perfil perfil = new Perfil();
        perfil.setNomePerfil("Admin");

        Usuario usuario = new Usuario(
                1L,
                "John Doe",
                "john.doe@example.com",
                LocalDate.of(1990, 1, 1),
                "12345678901",
                "123456789",
                "senha123",
                new HashSet<>(Collections.singletonList(perfil)),
                "senha****"
        );

        // Act
        UsuarioResponse response = usuarioMapper.toResponse(usuario);

        // Assert
        assertNotNull(response);
        assertEquals(usuario.getId(), response.getId());
        assertEquals(usuario.getNome(), response.getNome());
        assertEquals(usuario.getEmail(), response.getEmail());
        assertEquals(usuario.getDataNascimento(), response.getDataNascimento());
        assertEquals(usuario.getCpf(), response.getCpf());
        assertEquals(usuario.getTelefone(), response.getTelefone());
        assertEquals(usuario.getPassword(), response.getPassword());
        assertEquals(1, response.getListaNomesPerfis().size());
        assertTrue(response.getListaNomesPerfis().contains("Admin"));
        assertEquals(usuario.getSenhaComMascara(), response.getSenhaComMascara());
    }



    @Test
    void toRequest_DeveAtualizarUsuarioComDadosDoRequest() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setPerfilList(new HashSet<>()); // Inicializa o conjunto de perfis

        UsuarioRequest request = new UsuarioRequest(
        );

        Perfil perfil = new Perfil();
        perfil.setNomePerfil("Admin");

        when(perfilRepository.findByNomePerfil("Admin")).thenReturn(perfil);

        // Act
        usuarioMapper.toRequest(usuario, request);

        // Assert
        assertEquals(request.getNome(), usuario.getNome());
        assertEquals(request.getEmail(), usuario.getEmail());
        assertEquals(request.getDataNascimento(), usuario.getDataNascimento());
        assertEquals(request.getCpf(), usuario.getCpf());
        assertEquals(request.getTelefone(), usuario.getTelefone());
        assertEquals(1, usuario.getPerfilList().size());
        assertTrue(usuario.getPerfilList().contains(perfil));
    }

    @Test
    void toRequest_NaoDeveAtualizarQuandoUsuarioOuRequestNulos() {
        // Act
        usuarioMapper.toRequest(null, null); // Não deve lançar exceção
        usuarioMapper.toRequest(new Usuario(), null); // Não deve lançar exceção
        usuarioMapper.toRequest(null, new UsuarioRequest()); // Não deve lançar exceção
    }

    @Test
    void mascaraSenha_DeveMascaraSenhaCorretamente() {
        // Arrange
        String senhaOriginal = "senha123";

        // Act
        String senhaMascara = usuarioMapper.mascaraSenha(senhaOriginal);

        // Assert
        assertEquals("senh****", senhaMascara);
    }

    @Test
    void mascaraSenha_DeveRetornarSenhaOriginalQuandoMenorQue4Caracteres() {
        // Arrange
        String senhaOriginal = "abc";

        // Act
        String senhaMascara = usuarioMapper.mascaraSenha(senhaOriginal);

        // Assert
        assertEquals("abc", senhaMascara);
    }
}
