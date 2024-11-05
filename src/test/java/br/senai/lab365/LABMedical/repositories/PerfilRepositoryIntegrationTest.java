package br.senai.lab365.LABMedical.repositories;

import br.senai.lab365.LABMedical.entities.Perfil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test") // Carrega o perfil de teste
public class PerfilRepositoryIntegrationTest {

    @Autowired
    private PerfilRepository perfilRepository;

    @BeforeEach
    public void setUp() {
        perfilRepository.deleteAll();

        Perfil perfilAdmin = new Perfil();
        perfilAdmin.setNomePerfil("ADMIN");
        perfilRepository.save(perfilAdmin);

        Perfil perfilUser = new Perfil();
        perfilUser.setNomePerfil("USER");
        perfilRepository.save(perfilUser);
    }

    @Test
    public void testFindByNomePerfil() {
        Perfil perfilAdmin = perfilRepository.findByNomePerfil("ADMIN");
        assertEquals("ADMIN", perfilAdmin.getNomePerfil());

        Perfil perfilUser = perfilRepository.findByNomePerfil("USER");
        assertEquals("USER", perfilUser.getNomePerfil());
    }

    @Test
    public void testFindByNomePerfilNotFound() {
        Perfil perfilNaoExistente = perfilRepository.findByNomePerfil("MANAGER");
        assertTrue(perfilNaoExistente == null);
    }
}
