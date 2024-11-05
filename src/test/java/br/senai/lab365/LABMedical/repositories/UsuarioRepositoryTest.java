//package br.senai.lab365.LABMedical.repositories;
//
//import br.senai.lab365.LABMedical.entities.Usuario;
//import br.senai.lab365.LABMedical.repositories.UsuarioRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class UsuarioRepositoryTest {
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    @BeforeEach
//    void setUp() {
//        // Limpa o repositório antes de cada teste
//        usuarioRepository.deleteAll();
//    }
//
//    @Test
//    void existsByCpf_DeveRetornarTrueParaCpfExistente() {
//        // Arrange
//        Usuario usuario = new Usuario();
//        usuario.setCpf("123.456.789-00");
//        usuario.setEmail("teste@example.com");
//        usuarioRepository.save(usuario);
//
//        // Act
//        boolean exists = usuarioRepository.existsByCpf("123.456.789-00");
//
//        // Assert
//        assertThat(exists).isTrue();
//    }
//
//    @Test
//    void existsByCpf_DeveRetornarFalseParaCpfInexistente() {
//        // Act
//        boolean exists = usuarioRepository.existsByCpf("999.999.999-99");
//
//        // Assert
//        assertThat(exists).isFalse();
//    }
//
//    @Test
//    void existsByEmail_DeveRetornarTrueParaEmailExistente() {
//        // Arrange
//        Usuario usuario = new Usuario();
//        usuario.setCpf("123.456.789-00");
//        usuario.setEmail("teste@example.com");
//        usuarioRepository.save(usuario);
//
//        // Act
//        boolean exists = usuarioRepository.existsByEmail("teste@example.com");
//
//        // Assert
//        assertThat(exists).isTrue();
//    }
//
//    @Test
//    void existsByEmail_DeveRetornarFalseParaEmailInexistente() {
//        // Act
//        boolean exists = usuarioRepository.existsByEmail("inexistente@example.com");
//
//        // Assert
//        assertThat(exists).isFalse();
//    }
//
//    @Test
//    void findByEmailIgnoreCaseContaining_DeveRetornarUsuarioParaEmailExistente() {
//        // Arrange
//        Usuario usuario = new Usuario();
//        usuario.setEmail("teste@example.com");
//        usuarioRepository.save(usuario);
//
//        // Act
//        Optional<Usuario> foundUsuario = usuarioRepository.findByEmailIgnoreCaseContaining("teste");
//
//        // Assert
//        assertThat(foundUsuario).isPresent();
//        assertThat(foundUsuario.get().getEmail()).isEqualTo("teste@example.com");
//    }
//
//    @Test
//    void findByEmailIgnoreCaseContaining_DeveRetornarVazioParaEmailInexistente() {
//        // Act
//        Optional<Usuario> foundUsuario = usuarioRepository.findByEmailIgnoreCaseContaining("inexistente");
//
//        // Assert
//        assertThat(foundUsuario).isEmpty();
//    }
//
//    @Test
//    void findAllByEmailIgnoreCaseContaining_DeveRetornarListaDeUsuariosParaEmailsExistentes() {
//        // Arrange
//        Usuario usuario1 = new Usuario();
//        usuario1.setEmail("usuario1@example.com");
//        usuarioRepository.save(usuario1);
//
//        Usuario usuario2 = new Usuario();
//        usuario2.setEmail("usuario2@example.com");
//        usuarioRepository.save(usuario2);
//
//        // Act
//        List<Usuario> usuarios = usuarioRepository.findAllByEmailIgnoreCaseContaining("usuario");
//
//        // Assert
//        assertThat(usuarios).hasSize(2);
//        assertThat(usuarios).extracting(Usuario::getEmail)
//                .containsExactlyInAnyOrder("usuario1@example.com", "usuario2@example.com");
//    }
//
//    @Test
//    void findAllByEmailIgnoreCaseContaining_DeveRetornarListaVaziaParaEmailInexistente() {
//        // Act
//        List<Usuario> usuarios = usuarioRepository.findAllByEmailIgnoreCaseContaining("inexistente");
//
//        // Assert
//        assertThat(usuarios).isEmpty();
//    }
//
//    @Test
//    void findByPerfilListNomePerfil_DeveRetornarUsuariosComPerfilEspecifico() {
//        // Arrange
//        // Presumindo que o método está implementado corretamente e que há perfis associados.
//        // Aqui você precisaria adicionar os perfis e usuários conforme a lógica de seu sistema.
//
//        // Act
//        // List<Usuario> usuarios = usuarioRepository.findByPerfilListNomePerfil("PERFIL_EXISTENTE");
//
//        // Assert
//        // assertThat(usuarios).isNotEmpty();
//    }
//}
