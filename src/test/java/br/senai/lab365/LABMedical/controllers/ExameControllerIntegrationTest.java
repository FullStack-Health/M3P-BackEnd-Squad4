//package br.senai.lab365.LABMedical.controllers;
//
//import br.senai.lab365.LABMedical.dtos.exame.ExameRequest;
//import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
//import br.senai.lab365.LABMedical.services.ExameService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class ExameControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private ExameService exameService;
//
//    private ExameRequest createExameRequest(String nome, String tipoExame) {
//        ExameRequest request = new ExameRequest();
//        request.setNomeExame(nome); // Altere para os nomes corretos conforme a sua classe
//        request.setTipoExame(tipoExame);
//        request.setDataExame(LocalDate.now());
//        request.setHorarioExame(LocalTime.now());
//        request.setLaboratorio("Laboratório XYZ");
//        request.setIdPaciente(123L); // ou um ID válido de paciente
//        return request;
//    }
//
//    @BeforeEach
//    public void setUp() {
//        // Limpeza ou preparação necessária antes de cada teste
//    }
//
//    @Test
//    public void testCadastraExame() throws Exception {
//        ExameRequest request = createExameRequest("Exame de Sangue", "Sangue");
//
//        ResultActions response = mockMvc.perform(post("/exames")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)));
//
//        response.andExpect(status().isCreated())
//                .andExpect(jsonPath("$.nomeExame", is("Exame de Sangue")))
//                .andExpect(jsonPath("$.tipoExame", is("Sangue")));
//    }
//
//    @Test
//    public void testBuscaExame() throws Exception {
//        ExameRequest request = createExameRequest("Exame de Urina", "Urina");
//        ExameResponse savedExame = exameService.cadastra(request);
//
//        ResultActions response = mockMvc.perform(get("/exames/{id}", savedExame.getId())
//                .contentType(MediaType.APPLICATION_JSON));
//
//        response.andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(savedExame.getId().intValue())))
//                .andExpect(jsonPath("$.nomeExame", is("Exame de Urina")))
//                .andExpect(jsonPath("$.tipoExame", is("Urina")));
//    }
//
//    @Test
//    public void testAtualizaExame() throws Exception {
//        ExameRequest initialRequest = createExameRequest("Exame de Colesterol", "Colesterol");
//        ExameResponse savedExame = exameService.cadastra(initialRequest);
//
//        ExameRequest updateRequest = createExameRequest("Exame de Colesterol Atualizado", "Colesterol Atualizado");
//
//        ResultActions response = mockMvc.perform(put("/exames/{id}", savedExame.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(updateRequest)));
//
//        response.andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(savedExame.getId().intValue())))
//                .andExpect(jsonPath("$.nomeExame", is("Exame de Colesterol Atualizado")))
//                .andExpect(jsonPath("$.tipoExame", is("Colesterol Atualizado")));
//    }
//
//    @Test
//    public void testRemoveExame() throws Exception {
//        ExameRequest request = createExameRequest("Exame de Glicemia", "Glicemia");
//        ExameResponse savedExame = exameService.cadastra(request);
//
//        ResultActions response = mockMvc.perform(delete("/exames/{id}", savedExame.getId())
//                .contentType(MediaType.APPLICATION_JSON));
//
//        response.andExpect(status().isNoContent());
//    }
//}
