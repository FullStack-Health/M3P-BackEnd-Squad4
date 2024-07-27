package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.DashboardResponse;
import br.senai.lab365.LABMedical.entities.Dashboard;
import br.senai.lab365.LABMedical.repositories.ConsultaRepository;
import br.senai.lab365.LABMedical.repositories.ExameRepository;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;
    private final ExameRepository exameRepository;

    public DashboardService(PacienteRepository pacienteRepository, ConsultaRepository consultaRepository, ExameRepository exameRepository) {
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
        this.exameRepository = exameRepository;
    }

    public DashboardResponse getDashboard() {
        long numeroPacientes = pacienteRepository.count();
        long numeroConsultas = consultaRepository.count();
        long numeroExames = exameRepository.count();

        return new DashboardResponse(numeroPacientes, numeroConsultas, numeroExames);
    }
}
