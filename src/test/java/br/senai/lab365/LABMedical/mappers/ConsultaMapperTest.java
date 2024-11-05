package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaRequest;
import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.entities.Consulta;
import br.senai.lab365.LABMedical.entities.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaMapperTest {

    private ConsultaMapper consultaMapper;

    @BeforeEach
    void setUp() {
        consultaMapper = new ConsultaMapper();
    }

    @Test
    void toEntity_DeveRetornarConsultaQuandoDadosValidos() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        ConsultaRequest request = new ConsultaRequest(
                "Dor de cabeça",
                LocalDate.of(2023, 11, 1),
                LocalTime.of(10, 30),
                "Descrição detalhada do problema",
                "Medicamento XYZ",
                "1 comprimido a cada 8 horas",
                1L
        );

        // Act
        Consulta consulta = consultaMapper.toEntity(request, paciente);

        // Assert
        assertNotNull(consulta);
        assertEquals(request.getMotivo(), consulta.getMotivo());
        assertEquals(request.getDataConsulta(), consulta.getDataConsulta());
        assertEquals(request.getHorarioConsulta(), consulta.getHorarioConsulta());
        assertEquals(request.getDescricaoProblema(), consulta.getDescricaoProblema());
        assertEquals(request.getMedicacaoReceitada(), consulta.getMedicacaoReceitada());
        assertEquals(request.getDosagemPrecaucoes(), consulta.getDosagemPrecaucoes());
        assertEquals(paciente, consulta.getPaciente());
    }

    @Test
    void toEntity_DeveRetornarNuloQuandoRequestOuPacienteNulo() {
        // Act & Assert
        assertNull(consultaMapper.toEntity(null, new Paciente()));
        assertNull(consultaMapper.toEntity(new ConsultaRequest(), null));
    }

    @Test
    void toResponse_DeveRetornarConsultaResponseQuandoConsultaValida() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        Consulta consulta = new Consulta(
                1L,
                "Dor de cabeça",
                LocalDate.of(2023, 11, 1),
                LocalTime.of(10, 30),
                "Descrição detalhada do problema",
                "Medicamento XYZ",
                "1 comprimido a cada 8 horas",
                paciente
        );

        // Act
        ConsultaResponse response = consultaMapper.toResponse(consulta);

        // Assert
        assertNotNull(response);
        assertEquals(consulta.getId(), response.getId());
        assertEquals(consulta.getMotivo(), response.getMotivo());
        assertEquals(consulta.getDataConsulta(), response.getDataConsulta());
        assertEquals(consulta.getHorarioConsulta(), response.getHorarioConsulta());
        assertEquals(consulta.getDescricaoProblema(), response.getDescricaoProblema());
        assertEquals(consulta.getMedicacaoReceitada(), response.getMedicacaoReceitada());
        assertEquals(consulta.getDosagemPrecaucoes(), response.getDosagemPrecaucoes());
        assertEquals(consulta.getPaciente().getId(), response.getIdPaciente());
    }

    @Test
    void toResponse_DeveRetornarNuloQuandoConsultaNula() {
        // Act & Assert
        assertNull(consultaMapper.toResponse(null));
    }

    @Test
    void atualizaConsultaDesdeRequest_DeveAtualizarConsultaComNovosDados() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        Consulta consulta = new Consulta(
                1L,
                "Dor nas costas",
                LocalDate.of(2023, 10, 25),
                LocalTime.of(9, 0),
                "Descrição antiga do problema",
                "Medicamento antigo",
                "2 comprimidos ao dia",
                paciente
        );

        ConsultaRequest request = new ConsultaRequest(
                "Dor de cabeça",
                LocalDate.of(2023, 11, 1),
                LocalTime.of(10, 30),
                "Nova descrição do problema",
                "Medicamento XYZ",
                "1 comprimido a cada 8 horas",
                1L
        );

        // Act
        consultaMapper.atualizaConsultaDesdeRequest(consulta, request, paciente);

        // Assert
        assertEquals(request.getMotivo(), consulta.getMotivo());
        assertEquals(request.getDataConsulta(), consulta.getDataConsulta());
        assertEquals(request.getHorarioConsulta(), consulta.getHorarioConsulta());
        assertEquals(request.getDescricaoProblema(), consulta.getDescricaoProblema());
        assertEquals(request.getMedicacaoReceitada(), consulta.getMedicacaoReceitada());
        assertEquals(request.getDosagemPrecaucoes(), consulta.getDosagemPrecaucoes());
    }

    @Test
    void atualizaConsultaDesdeRequest_DeveIgnorarCamposNulos() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1L);

        Consulta consulta = new Consulta(
                1L,
                "Dor nas costas",
                LocalDate.of(2023, 10, 25),
                LocalTime.of(9, 0),
                "Descrição antiga do problema",
                "Medicamento antigo",
                "2 comprimidos ao dia",
                paciente
        );

        ConsultaRequest request = new ConsultaRequest(
                null,
                null,
                null,
                "Nova descrição do problema",
                null,
                "1 comprimido a cada 8 horas",
                null
        );

        // Act
        consultaMapper.atualizaConsultaDesdeRequest(consulta, request, paciente);

        // Assert
        assertEquals("Dor nas costas", consulta.getMotivo());
        assertEquals(LocalDate.of(2023, 10, 25), consulta.getDataConsulta());
        assertEquals(LocalTime.of(9, 0), consulta.getHorarioConsulta());
        assertEquals("Nova descrição do problema", consulta.getDescricaoProblema());
        assertEquals("Medicamento antigo", consulta.getMedicacaoReceitada());
        assertEquals("1 comprimido a cada 8 horas", consulta.getDosagemPrecaucoes());
    }


}



