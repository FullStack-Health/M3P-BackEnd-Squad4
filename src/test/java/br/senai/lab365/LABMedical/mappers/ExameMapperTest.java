package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.exame.ExameRequest;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.entities.Exame;
import br.senai.lab365.LABMedical.entities.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExameMapperTest {

    private ExameMapper exameMapper;

    @BeforeEach
    void setUp() {
        exameMapper = new ExameMapper();
    }

    @Test
    void toEntity_DeveRetornarExameQuandoRequestValidoEPacienteValido() {
        // Arrange
        Paciente paciente = new Paciente(); // Crie um objeto Paciente válido
        paciente.setId(1L); // Defina um ID para o paciente

        ExameRequest request = new ExameRequest();

        // Act
        Exame exame = exameMapper.toEntity(request, paciente);

        // Assert
        assertNotNull(exame);
        assertEquals(request.getNomeExame(), exame.getNomeExame());
        assertEquals(request.getDataExame(), exame.getDataExame());
        assertEquals(request.getHorarioExame(), exame.getHorarioExame());
        assertEquals(request.getTipoExame(), exame.getTipoExame());
        assertEquals(request.getLaboratorio(), exame.getLaboratorio());
        assertEquals(request.getUrlDocumento(), exame.getUrlDocumento());
        assertEquals(request.getResultados(), exame.getResultados());
        assertEquals(paciente, exame.getPaciente());
    }

    @Test
    void toEntity_DeveRetornarNuloQuandoRequestOuPacienteNulos() {
        // Act & Assert
        assertNull(exameMapper.toEntity(null, null));
        assertNull(exameMapper.toEntity(new ExameRequest(), null));
        assertNull(exameMapper.toEntity(null, new Paciente()));
    }

//    @Test
//    void toResponse_DeveRetornarExameResponseQuandoExameValido() {
//        // Arrange
//        Paciente paciente = new Paciente(); // Crie um objeto Paciente válido
//        paciente.setId(1L); // Defina um ID para o paciente
//
//        Exame exame = new Exame();
//
//        // Act
//        ExameResponse response = exameMapper.toResponse(exame);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(exame.getId(), response.getId());
//        assertEquals(exame.getNomeExame(), response.getNomeExame());
//        assertEquals(exame.getDataExame(), response.getDataExame());
//        assertEquals(exame.getHorarioExame(), response.getHorarioExame());
//        assertEquals(exame.getTipoExame(), response.getTipoExame());
//        assertEquals(exame.getLaboratorio(), response.getLaboratorio());
//        assertEquals(exame.getUrlDocumento(), response.getUrlDocumento());
//        assertEquals(exame.getResultados(), response.getResultados());
//        assertEquals(exame.getPaciente().getId(), response.getIdPaciente());
//    }

    @Test
    void toResponse_DeveRetornarNuloQuandoExameNulo() {
        // Act & Assert
        assertNull(exameMapper.toResponse(null));
    }



}



