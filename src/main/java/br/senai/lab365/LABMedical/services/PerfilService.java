package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.PerfilRequest;
import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.repositories.PerfilRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilService {

    private final PerfilRepository repository;

    public PerfilService(PerfilRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void criaPerfil() {
        criaPerfilSeNãoExiste("ADMIN");
        criaPerfilSeNãoExiste("MÉDICO");
        criaPerfilSeNãoExiste("PACIENTE");
    }

    public void criaPerfilSeNãoExiste(String nomePerfil) {
        if (repository.findByNomePerfil(nomePerfil) == null) {
            Perfil perfil = new Perfil();
            perfil.setNomePerfil(nomePerfil);
            repository.save(perfil);
        }
    }
}



