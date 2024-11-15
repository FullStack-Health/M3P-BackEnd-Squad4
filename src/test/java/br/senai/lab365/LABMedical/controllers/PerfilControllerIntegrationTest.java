//package br.senai.lab365.LABMedical.controllers;
//
//import br.senai.lab365.LABMedical.entities.Perfil;
//import br.senai.lab365.LABMedical.services.PerfilService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc // Para permitir o uso do MockMvc
//public class PerfilControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private PerfilService perfilService;
//
//    @BeforeEach
//    public void setup() {
//        // Limpar a base de dados ou configurar os dados necessários antes de cada teste, se necessário.
//    }
//
//    @Test
//    public void testCadastraPerfil() throws Exception {
//        // Prepare a requisição JSON para criar um novo perfil
//        String perfilJson = "{\"nomePerfil\": \"Usuario\"}"; // Supondo que PerfilRequest tenha apenas o campo nomePerfil
//
//        mockMvc.perform(post("/perfis")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(perfilJson))
//                .andExpect(status().isCreated()); // Verifica se a resposta é 201 CREATED
//    }
//}
