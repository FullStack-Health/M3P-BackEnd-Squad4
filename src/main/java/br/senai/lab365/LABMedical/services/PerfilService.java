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


//    public void cadastra(PerfilRequest request) {
//        repository.findByNomePerfil(request.nomePerfil())
//                ifPresent(perfil -> {
//                    throw new IllegalArgumentException("Perfil já cadastrado com o nome: " + request.nomePerfil());
//                });
//
//        Perfil perfil = new Perfil();
//        perfil.setNomePerfil(request.nomePerfil());
//        repository.save(perfil);
//    }

//    public Perfil validaPerfil(String nomePerfil) {
//        return repository.findByNomePerfil(nomePerfil)
//                .orElseThrow(() -> new IllegalArgumentException("Perfil não existe com o nome: " + nomePerfil));
//    }
}



