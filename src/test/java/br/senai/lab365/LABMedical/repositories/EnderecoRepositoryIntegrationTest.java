//package br.senai.lab365.LABMedical.repositories;
//
//import br.senai.lab365.LABMedical.entities.Endereco;
//import br.senai.lab365.LABMedical.LabMedicalApplication; // importe a classe principal
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@DataJpaTest
//@Import(LabMedicalApplication.class) // Importa as configurações da aplicação para o teste
//@ActiveProfiles("test")
//public class EnderecoRepositoryIntegrationTest {
//
//    @Autowired
//    private EnderecoRepository enderecoRepository;
//
//    private Endereco endereco;
//
//    @BeforeEach
//    public void setup() {
//        endereco = new Endereco();
//        endereco.setRua("Rua das Flores");
//        endereco.setCidade("Cidade Exemplo");
//        endereco.setEstado("Estado Exemplo");
//        endereco.setCep("12345-678");
//        endereco = enderecoRepository.save(endereco);
//        assertNotNull(endereco.getId(), "O ID do endereço não deve ser nulo após o salvamento.");
//    }
//
//    @Test
//    public void testSaveAndFindEndereco() {
//        Endereco enderecoEncontrado = enderecoRepository.findById(endereco.getId()).orElse(null);
//        assertNotNull(enderecoEncontrado, "O endereço não foi encontrado.");
//        assertEquals("Rua das Flores", enderecoEncontrado.getRua(), "A rua do endereço deve corresponder.");
//        assertEquals("Cidade Exemplo", enderecoEncontrado.getCidade(), "A cidade do endereço deve corresponder.");
//        assertEquals("Estado Exemplo", enderecoEncontrado.getEstado(), "O estado do endereço deve corresponder.");
//        assertEquals("12345-678", enderecoEncontrado.getCep(), "O CEP do endereço deve corresponder.");
//    }
//
//    @Test
//    public void testFindAllEnderecos() {
//        Endereco outroEndereco = new Endereco();
//        outroEndereco.setRua("Avenida Central");
//        outroEndereco.setCidade("Outra Cidade");
//        outroEndereco.setEstado("Outro Estado");
//        outroEndereco.setCep("87654-321");
//        enderecoRepository.save(outroEndereco);
//
//        List<Endereco> enderecos = enderecoRepository.findAll();
//        assertEquals(2, enderecos.size(), "Devem haver dois endereços no banco.");
//    }
//}
