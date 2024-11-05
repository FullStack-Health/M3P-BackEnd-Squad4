package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.consulta.ConsultaResponse;
import br.senai.lab365.LABMedical.dtos.exame.ExameResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.PacienteSummaryRequest;
import br.senai.lab365.LABMedical.dtos.prontuario.ProntuarioResponse;
import br.senai.lab365.LABMedical.dtos.prontuario.SummaryResponsePagination;
import br.senai.lab365.LABMedical.entities.Consulta;
import br.senai.lab365.LABMedical.entities.Exame;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.mappers.ProntuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ProntuarioMapperTest {

    private ProntuarioMapper prontuarioMapper;

    @BeforeEach
    void setUp() {
        prontuarioMapper = new ProntuarioMapper();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRequestToResponse_DeveConverterPacienteParaPacienteSummaryRequest() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("John Doe");
        paciente.setConvenio("Unimed");

        // Act
        PacienteSummaryRequest response = prontuarioMapper.getRequestToResponse(paciente);

        // Assert
        assertNotNull(response);
        assertEquals(paciente.getId(), response.getIdPaciente());
        assertEquals(paciente.getNome(), response.getNome());
        assertEquals(paciente.getConvenio(), response.getConvenio());
    }



//    @Test
//    void mapToSummaryResponsePagination_DeveConverterPaginaPacientesParaSummaryResponsePagination() {
//        // Arrange
//        List<Paciente> pacientes = new ArrayList<>();
//        pacientes.add(new Paciente());
//        Page<Paciente> paginaPacientes = new PageImpl<>(pacientes, PageRequest.of(0, 10), 1);
//
//        List<PacienteSummaryRequest> conteudo = new ArrayList<>();
//        conteudo.add(new PacienteSummaryRequest());
//
//        // Act
//        SummaryResponsePagination response = prontuarioMapper.mapToSummaryResponsePagination(paginaPacientes, conteudo, 0, 10);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(conteudo, response.getConteudo());
//        assertEquals(paginaPacientes.getTotalPages(), response.getTotalPaginas());
//        assertEquals(10, response.getTamanhoPagina());
//        assertEquals(0, response.getPaginaAtual());
//        assertEquals(paginaPacientes.getTotalElements(), response.getTotalElementos());
//        assertFalse(response.isUltima());
//    }
//
//    @Test
//    void examesToResponse_DeveConverterListaDeExamesParaExameResponse() {
//        // Arrange
//        List<Exame> exames = new ArrayList<>();
//        Paciente paciente = new Paciente();
//        paciente.setId(1L);
//        exames.add(new Exame());
//
//        // Act
//        List<ExameResponse> responses = prontuarioMapper.examesToResponse(exames);
//
//        // Assert
//        assertNotNull(responses);
//        assertEquals(1, responses.size());
//        assertEquals(exames.get(0).getNomeExame(), responses.get(0).getNomeExame());
//        assertEquals(exames.get(0).getId(), responses.get(0).getId());
//    }
//
//    @Test
//    void consultasToResponse_DeveConverterListaDeConsultasParaConsultaResponse() {
//        // Arrange
//        List<Consulta> consultas = new ArrayList<>();
//        Paciente paciente = new Paciente();
//        paciente.setId(1L);
//        consultas.add(new Consulta());
//
//        // Act
//        List<ConsultaResponse> responses = prontuarioMapper.consultasToResponse(consultas);
//
//        // Assert
//        assertNotNull(responses);
//        assertEquals(1, responses.size());
//        assertEquals(consultas.get(0).getMotivo(), responses.get(0).getMotivo());
//        assertEquals(consultas.get(0).getId(), responses.get(0).getId());
//    }
}
