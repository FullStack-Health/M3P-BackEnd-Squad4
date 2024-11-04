package br.senai.lab365.LABMedical.repositories;

import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.repositories.PerfilRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PerfilRepositoryTest {

    @Autowired
    private PerfilRepository perfilRepository;

    @BeforeEach
    void setUp() {
        // Limpa o repositório antes de cada teste
        perfilRepository.deleteAll();
    }

    @Test
    void findByNomePerfil_DeveRetornarPerfilExistente() {
        // Arrange
        Perfil perfil = new Perfil();
        perfil.setNomePerfil("PACIENTE");
        perfilRepository.save(perfil);

        // Act
        Perfil encontrado = perfilRepository.findByNomePerfil("PACIENTE");

        // Assert
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getNomePerfil()).isEqualTo("PACIENTE");
    }

    @Test
    void findByNomePerfil_DeveRetornarNuloParaPerfilInexistente() {
        // Act
        Perfil encontrado = perfilRepository.findByNomePerfil("INEXISTENTE");

        // Assert
        assertThat(encontrado).isNull();
    }
}
