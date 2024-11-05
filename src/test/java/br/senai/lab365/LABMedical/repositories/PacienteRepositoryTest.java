package br.senai.lab365.LABMedical.repositories;

import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PacienteRepositoryTest {

    @Autowired
    private PacienteRepository pacienteRepository;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        pacienteRepository.deleteAll();
        paciente = new Paciente();
        paciente.setNome("John Doe");
        paciente.setCpf("12345678900");
        paciente.setTelefone("123456789");
        paciente.setEmail("john.doe@example.com");
        pacienteRepository.save(paciente);
    }

    @Test
    void existsByCpf_DeveRetornarTrueParaCpfExistente() {
        boolean exists = pacienteRepository.existsByCpf("12345678900");
        assertThat(exists).isTrue();
    }

    @Test
    void existsByCpf_DeveRetornarFalseParaCpfInexistente() {
        boolean exists = pacienteRepository.existsByCpf("99999999999");
        assertThat(exists).isFalse();
    }

    @Test
    void existsByCpfAndIdNot_DeveRetornarTrueParaCpfExistenteExcluindoId() {
        Paciente novoPaciente = new Paciente();
        novoPaciente.setNome("Jane Doe");
        novoPaciente.setCpf("12345678900");
        pacienteRepository.save(novoPaciente);

        boolean exists = pacienteRepository.existsByCpfAndIdNot("12345678900", paciente.getId());
        assertThat(exists).isTrue();
    }

    @Test
    void findByNomeIgnoreCaseContaining_DeveRetornarPacientesQueContenhamONome() {
        Page<Paciente> pacientes = pacienteRepository.findByNomeIgnoreCaseContaining("john", PageRequest.of(0, 10));
        assertThat(pacientes.getContent()).hasSize(1);
        assertThat(pacientes.getContent().get(0).getNome()).isEqualTo("John Doe");
    }

    @Test
    void findByTelefoneContaining_DeveRetornarPacientesQueContenhamOTelefone() {
        Page<Paciente> pacientes = pacienteRepository.findByTelefoneContaining("123", PageRequest.of(0, 10));
        assertThat(pacientes.getContent()).hasSize(1);
        assertThat(pacientes.getContent().get(0).getTelefone()).isEqualTo("123456789");
    }

    @Test
    void findByEmailContaining_DeveRetornarPacientesQueContenhamOEmail() {
        Page<Paciente> pacientes = pacienteRepository.findByEmailContaining("john", PageRequest.of(0, 10));
        assertThat(pacientes.getContent()).hasSize(1);
        assertThat(pacientes.getContent().get(0).getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void findById_DeveRetornarPacienteExistente() {
        Optional<Paciente> encontrado = pacienteRepository.findById(paciente.getId());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("John Doe");
    }

    @Test
    void findById_DeveRetornarNuloParaIdInexistente() {
        Optional<Paciente> encontrado = pacienteRepository.findById(999L);
        assertThat(encontrado).isNotPresent();
    }
}
