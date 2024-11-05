package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroResponse;
import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.repositories.PerfilRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UsuarioPreRegistroMapperTest {

    @InjectMocks
    private UsuarioPreRegistroMapper usuarioPreRegistroMapper;

    @Mock
    private PerfilRepository perfilRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void toEntity_DeveConverterUsuarioPreRegistroRequestParaUsuario() {
//        // Arrange
//        UsuarioPreRegistroRequest request = new UsuarioPreRegistroRequest();
//        request.setEmail("john@example.com");
//        request.setPassword("senhaForte");
//        request.setNomePerfil("PACIENTE");
//        String senhaComMascara = "sens****"; // Mascara esperada
//
//        Perfil perfil = new Perfil();
//        perfil.setNomePerfil("PACIENTE");
//        when(perfilRepository.findByNomePerfil(request.getNomePerfil())).thenReturn(perfil);
//
//        // Act
//        Usuario usuario = usuarioPreRegistroMapper.toEntity(request);
//
//        // Assert
//        assertNotNull(usuario);
//        assertEquals(request.getEmail(), usuario.getEmail());
//        assertEquals(request.getPassword(), usuario.getPassword());
//        assertEquals(senhaComMascara, usuario.getSenhaComMascara());
//        assertEquals(1, usuario.getPerfilList().size());
//        assertTrue(usuario.getPerfilList().stream().anyMatch(p -> p.getNomePerfil().equals("PACIENTE")));
//    }

    @Test
    void toResponse_DeveConverterUsuarioParaUsuarioPreRegistroResponse() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("john@example.com");
        usuario.setPassword("senhaForte");
        usuario.setSenhaComMascara("sens****");
        Set<Perfil> perfis = new HashSet<>();
        Perfil perfil = new Perfil();
        perfil.setNomePerfil("PACIENTE");
        perfis.add(perfil);
        usuario.setPerfilList(perfis);

        // Act
        UsuarioPreRegistroResponse response = usuarioPreRegistroMapper.toResponse(usuario);

        // Assert
        assertNotNull(response);
        assertEquals(usuario.getId(), response.getId());
        assertEquals(usuario.getEmail(), response.getEmail());
        assertEquals(usuario.getPassword(), response.getPassword());
        assertEquals(1, response.getListaNomesPerfis().size());
        assertTrue(response.getListaNomesPerfis().contains("PACIENTE"));
        assertEquals(usuario.getSenhaComMascara(), response.getSenhaComMascara());
    }

//    @Test
//    void mascaraSenha_DeveRetornarSenhaComMascara() {
//        // Arrange
//        String senhaOriginal = "senhaForte";
//
//        // Act
//        String senhaMascara = usuarioPreRegistroMapper.mascaraSenha(senhaOriginal);
//
//        // Assert
//        assertEquals("sens****", senhaMascara);
//    }

    @Test
    void mascaraSenha_DeveRetornarSenhaOriginalQuandoMenorOuIgual4Caracteres() {
        // Arrange
        String senhaOriginal = "abc";

        // Act
        String senhaMascara = usuarioPreRegistroMapper.mascaraSenha(senhaOriginal);

        // Assert
        assertEquals("abc", senhaMascara);
    }

    @Test
    void mascaraSenha_DeveRetornarNullQuandoSenhaOriginalForNull() {
        // Act
        String senhaMascara = usuarioPreRegistroMapper.mascaraSenha(null);

        // Assert
        assertNull(senhaMascara);
    }
}
