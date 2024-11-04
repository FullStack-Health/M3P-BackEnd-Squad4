package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.DashboardResponse;
import br.senai.lab365.LABMedical.repositories.ConsultaRepository;
import br.senai.lab365.LABMedical.repositories.ExameRepository;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import br.senai.lab365.LABMedical.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DashboardServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private ExameRepository exameRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDashboardAsAdmin() {
        // Dados simulados
        long pacientesCount = 100L;
        long consultasCount = 200L;
        long examesCount = 300L;
        long usuariosCount = 50L;

        // Configuração dos mocks
        when(pacienteRepository.count()).thenReturn(pacientesCount);
        when(consultaRepository.count()).thenReturn(consultasCount);
        when(exameRepository.count()).thenReturn(examesCount);
        when(usuarioRepository.count()).thenReturn(usuariosCount);

        // Chama o método com o perfil de administrador
        DashboardResponse response = dashboardService.getDashboard("SCOPE_ADMIN");

        // Verifica os resultados
        assertEquals(pacientesCount, response.getNumeroPacientes());
        assertEquals(consultasCount, response.getNumeroConsultas());
        assertEquals(examesCount, response.getNumeroExames());
        assertEquals(usuariosCount, response.getNumeroUsuarios());

        // Verifica se os métodos de contagem foram chamados
        verify(pacienteRepository, times(1)).count();
        verify(consultaRepository, times(1)).count();
        verify(exameRepository, times(1)).count();
        verify(usuarioRepository, times(1)).count();
    }

    @Test
    void testGetDashboardAsNonAdmin() {
        // Dados simulados
        long pacientesCount = 100L;
        long consultasCount = 200L;
        long examesCount = 300L;

        // Configuração dos mocks
        when(pacienteRepository.count()).thenReturn(pacientesCount);
        when(consultaRepository.count()).thenReturn(consultasCount);
        when(exameRepository.count()).thenReturn(examesCount);

        // Chama o método com um perfil que não é administrador
        DashboardResponse response = dashboardService.getDashboard("SCOPE_USER");

        // Verifica os resultados
        assertEquals(pacientesCount, response.getNumeroPacientes());
        assertEquals(consultasCount, response.getNumeroConsultas());
        assertEquals(examesCount, response.getNumeroExames());
        assertEquals(null, response.getNumeroUsuarios());

        // Verifica se os métodos de contagem foram chamados
        verify(pacienteRepository, times(1)).count();
        verify(consultaRepository, times(1)).count();
        verify(exameRepository, times(1)).count();
        verify(usuarioRepository, never()).count();  // Não deve chamar count de usuarios
    }
}
