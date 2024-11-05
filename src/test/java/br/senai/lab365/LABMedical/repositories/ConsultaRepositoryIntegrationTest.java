//package br.senai.lab365.LABMedical.repositories;
//
//import br.senai.lab365.LABMedical.entities.Consulta;
//import br.senai.lab365.LABMedical.entities.Paciente;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@DataJpaTest
//@ActiveProfiles("test")
//public class ConsultaRepositoryIntegrationTest {
//
//    @Autowired
//    private ConsultaRepository consultaRepository;
//
//    @Autowired
//    private PacienteRepository pacienteRepository;
//
//    private Paciente paciente;
//
//    @BeforeEach
//    public void setup() {
//        // Criar e salvar um paciente no banco H2
//        paciente = new Paciente();
//        paciente.setNome("João Silva");
//        paciente = pacienteRepository.save(paciente);
//
//        assertNotNull(paciente.getId(), "Paciente não foi salvo corretamente no H2.");
//
//        // Criar e salvar consultas no banco H2
//        Consulta consulta1 = new Consulta();
//        consulta1.setPaciente(paciente);
//        consulta1.setDataConsulta(LocalDate.now());
//        consultaRepository.save(consulta1);
//
//        Consulta consulta2 = new Consulta();
//        consulta2.setPaciente(paciente);
//        consulta2.setDataConsulta(LocalDate.now().plusDays(1));
//        consultaRepository.save(consulta2);
//    }
//
//    @Test
//    public void testFindByPacienteId() {
//        List<Consulta> consultas = consultaRepository.findByPacienteId(paciente.getId());
//
//        // Validações
//        assertEquals(2, consultas.size(), "O paciente deve ter duas consultas associadas.");
//        consultas.forEach(consulta ->
//                assertEquals(paciente.getId(), consulta.getPaciente().getId(), "O ID do paciente deve corresponder.")
//        );
//    }
//}
