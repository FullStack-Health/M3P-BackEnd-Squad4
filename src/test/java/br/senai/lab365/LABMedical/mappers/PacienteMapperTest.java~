package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.paciente.EnderecoRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteGetRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponse;
import br.senai.lab365.LABMedical.entities.Endereco;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.repositories.PerfilRepository;
import br.senai.lab365.LABMedical.repositories.UsuarioRepository;
import br.senai.lab365.LABMedical.mappers.EnderecoMapper;
import br.senai.lab365.LABMedical.mappers.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacienteMapperTest {

    @InjectMocks
    private PacienteMapper pacienteMapper;

    @Mock
    private EnderecoMapper enderecoMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toEntity_DeveConverterPacienteRequestParaPaciente() {
        // Arrange
        PacienteRequest pacienteRequest = new PacienteRequest();
        pacienteRequest.setNome("John Doe");
        pacienteRequest.setGenero("Masculino");
        pacienteRequest.setDataNascimento(LocalDate.of(1990, 1, 1));
        pacienteRequest.setCpf("123.456.789-09");
        pacienteRequest.setRg("12.345.678-9");
        pacienteRequest.setOrgaoExpedidor("SSP");
        pacienteRequest.setEstadoCivil("Solteiro");
        pacienteRequest.setTelefone("1234-5678");
        pacienteRequest.setEmail("john@example.com");
        pacienteRequest.setNaturalidade("Brasil");
        pacienteRequest.setContatoEmergencia("9876-5432");
        pacienteRequest.setListaAlergias(Set.of("Alergia a pólen").toString());
        pacienteRequest.setListaCuidados(Set.of("Cuidado com sol").toString());
        pacienteRequest.setConvenio("Unimed");
        pacienteRequest.setNumeroConvenio("123456");
        pacienteRequest.setValidadeConvenio(LocalDate.now().plusYears(1));

        EnderecoRequest enderecoRequest = new EnderecoRequest();
        enderecoRequest.setRua("Rua A");
        enderecoRequest.setNumero("123");
        pacienteRequest.setEndereco(enderecoRequest);

        Perfil perfil = new Perfil();
        perfil.setNomePerfil("PACIENTE");
        when(perfilRepository.findByNomePerfil("PACIENTE")).thenReturn(perfil);
        when(enderecoMapper.toEntity(enderecoRequest)).thenReturn(new Endereco());

        // Act
        Paciente paciente = pacienteMapper.toEntity(pacienteRequest);

        // Assert
        assertNotNull(paciente);
        assertEquals(pacienteRequest.getNome(), paciente.getNome());
        assertEquals(pacienteRequest.getGenero(), paciente.getGenero());
        assertEquals(pacienteRequest.getDataNascimento(), paciente.getDataNascimento());
        assertEquals(pacienteRequest.getCpf(), paciente.getCpf());
        assertEquals(pacienteRequest.getRg(), paciente.getRg());
        assertEquals(pacienteRequest.getOrgaoExpedidor(), paciente.getOrgaoExpedidor());
        assertEquals(pacienteRequest.getEstadoCivil(), paciente.getEstadoCivil());
        assertEquals(pacienteRequest.getTelefone(), paciente.getTelefone());
        assertEquals(pacienteRequest.getEmail(), paciente.getEmail());
        assertEquals(pacienteRequest.getNaturalidade(), paciente.getNaturalidade());
        assertEquals(pacienteRequest.getContatoEmergencia(), paciente.getContatoEmergencia());
        assertEquals(pacienteRequest.getListaAlergias(), paciente.getListaAlergias());
        assertEquals(pacienteRequest.getListaCuidados(), paciente.getListaCuidados());
        assertEquals(pacienteRequest.getConvenio(), paciente.getConvenio());
        assertEquals(pacienteRequest.getNumeroConvenio(), paciente.getNumeroConvenio());
        assertEquals(pacienteRequest.getValidadeConvenio(), paciente.getValidadeConvenio());
    }

    @Test
    void toResponse_DeveConverterPacienteParaPacienteResponse() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("John Doe");
        paciente.setGenero("Masculino");
        paciente.setDataNascimento(LocalDate.of(1990, 1, 1));
        paciente.setCpf("123.456.789-09");
        paciente.setRg("12.345.678-9");
        paciente.setOrgaoExpedidor("SSP");
        paciente.setEstadoCivil("Solteiro");
        paciente.setTelefone("1234-5678");
        paciente.setEmail("john@example.com");
        paciente.setNaturalidade("Brasil");
        paciente.setContatoEmergencia("9876-5432");

        paciente.setConvenio("Unimed");
        paciente.setNumeroConvenio("123456");
        paciente.setValidadeConvenio(LocalDate.now().plusYears(1));
        paciente.setEndereco(new Endereco());

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(usuarioRepository.getReferenceById(1L)).thenReturn(usuario);
        when(enderecoMapper.toResponse(paciente.getEndereco())).thenReturn(null); // Mock de retorno para endereço
        when(usuarioMapper.toResponse(usuario)).thenReturn(null); // Mock de retorno para usuário

        // Act
        PacienteResponse response = pacienteMapper.toResponse(paciente);

        // Assert
        assertNotNull(response);
        assertEquals(paciente.getId(), response.getId());
        assertEquals(paciente.getNome(), response.getNome());
        assertEquals(paciente.getGenero(), response.getGenero());
        assertEquals(paciente.getDataNascimento(), response.getDataNascimento());
        assertEquals(paciente.getCpf(), response.getCpf());
        assertEquals(paciente.getRg(), response.getRg());
        assertEquals(paciente.getOrgaoExpedidor(), response.getOrgaoExpedidor());
        assertEquals(paciente.getEstadoCivil(), response.getEstadoCivil());
        assertEquals(paciente.getTelefone(), response.getTelefone());
        assertEquals(paciente.getEmail(), response.getEmail());
        assertEquals(paciente.getNaturalidade(), response.getNaturalidade());
        assertEquals(paciente.getContatoEmergencia(), response.getContatoEmergencia());
        assertEquals(paciente.getListaAlergias(), response.getListaAlergias());
        assertEquals(paciente.getListaCuidados(), response.getListaCuidados());
        assertEquals(paciente.getConvenio(), response.getConvenio());
        assertEquals(paciente.getNumeroConvenio(), response.getNumeroConvenio());
        assertEquals(paciente.getValidadeConvenio(), response.getValidadeConvenio());
    }

    @Test
    void atualizaPacienteDesdeRequest_DeveAtualizarPacienteCorretamente() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setNome("Old Name");
        paciente.setGenero("Masculino");

        PacienteRequest request = new PacienteRequest();
        request.setNome("New Name");
        request.setGenero("Feminino");

        // Act
        pacienteMapper.atualizaPacienteDesdeRequest(paciente, request);

        // Assert
        assertEquals("New Name", paciente.getNome());
        assertEquals("Feminino", paciente.getGenero());
    }

    @Test
    void getRequestToResponse_DeveConverterPacienteParaPacienteGetRequest() {
        // Arrange
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("John Doe");
        paciente.setTelefone("1234-5678");
        paciente.setEmail("john@example.com");
        paciente.setDataNascimento(LocalDate.of(1990, 1, 1));

        // Act
        PacienteGetRequest response = pacienteMapper.getRequestToResponse(paciente);

        // Assert
        assertNotNull(response);
        assertEquals(paciente.getId(), response.getId());
        assertEquals(paciente.getNome(), response.getNome());
        assertEquals(paciente.getTelefone(), response.getTelefone());
        assertEquals(paciente.getEmail(), response.getEmail());
        assertEquals(30, response.getIdade()); // Ajuste para refletir o ano atual
    }

}

