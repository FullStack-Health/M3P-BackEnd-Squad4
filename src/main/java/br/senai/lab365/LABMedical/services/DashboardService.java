package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.DashboardResponse;
import br.senai.lab365.LABMedical.repositories.ConsultaRepository;
import br.senai.lab365.LABMedical.repositories.ExameRepository;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import br.senai.lab365.LABMedical.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;
    private final ExameRepository exameRepository;
    private final UsuarioRepository usuarioRepository;

    public DashboardService(PacienteRepository pacienteRepository, ConsultaRepository consultaRepository, ExameRepository exameRepository, UsuarioRepository usuarioRepository) {
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
        this.exameRepository = exameRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public DashboardResponse getDashboard(String perfil) {
        long numeroPacientes = pacienteRepository.count();
        long numeroConsultas = consultaRepository.count();
        long numeroExames = exameRepository.count();
        Long numeroUsuarios = null;

        if ("SCOPE_ADMIN".equalsIgnoreCase(perfil)) {
            numeroUsuarios = usuarioRepository.count();
        }

        return new DashboardResponse(numeroPacientes, numeroConsultas, numeroExames, numeroUsuarios);
    }
}
