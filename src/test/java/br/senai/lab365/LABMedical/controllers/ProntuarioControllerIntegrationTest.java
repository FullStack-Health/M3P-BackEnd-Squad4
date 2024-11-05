//package br.senai.lab365.LABMedical.controllers;
//
//import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
//import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
//import br.senai.lab365.LABMedical.dtos.prontuario.ProntuarioResponse;
//import br.senai.lab365.LABMedical.dtos.prontuario.SummaryResponsePagination;
//import br.senai.lab365.LABMedical.entities.Paciente;
//import br.senai.lab365.LABMedical.services.ProntuarioService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
//public class ProntuarioControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ProntuarioService prontuarioService;
//
//    private Paciente paciente;
//
//    @BeforeEach
//    public void setup() {
//        paciente = new Paciente();
//        paciente.setId(1L);
//        paciente.setNome("João Silva");
//        // Salvar o paciente no banco H2 se necessário
//        // pacienteRepository.save(paciente); // Certifique-se de que a camada de repositório está configurada
//    }
//
//    @Test
//    public void testListaProntuarios() throws Exception {
//        SummaryResponsePagination response = new SummaryResponsePagination();
//        when(prontuarioService.lista(null, null, 0, 10)).thenReturn(response);
//
//        mockMvc.perform(get("/pacientes/prontuarios")
//                        .param("numeroPagina", "0")
//                        .param("tamanhoPagina", "10")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.totalElements").value(0)); // Ajuste conforme necessário
//    }
//
//    @Test
//    public void testBuscaProntuario() throws Exception {
//        ProntuarioResponse response = new ProntuarioResponse();
//        when(prontuarioService.busca(1L)).thenReturn(response);
//
//        mockMvc.perform(get("/pacientes/{idPaciente}/prontuarios", 1L)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testListaExames() throws Exception {
//        List<ExameResponse> response = List.of(new ExameResponse(/* preencha com dados de exemplo */));
//        when(prontuarioService.listaTodosExamesPaciente(1L)).thenReturn(response);
//
//        mockMvc.perform(get("/pacientes/{idPaciente}/exames", 1L)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testListaConsultas() throws Exception {
//        List<ConsultaResponse> response = List.of(new ConsultaResponse(/* preencha com dados de exemplo */));
//        when(prontuarioService.listaTodasConsultasPaciente(1L)).thenReturn(response);
//
//        mockMvc.perform(get("/pacientes/{idPaciente}/consultas", 1L)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//}
