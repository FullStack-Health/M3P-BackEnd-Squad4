package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.PerfilRequest;
import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.repositories.PerfilRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilService {

    private final PerfilRepository repository;

    public PerfilService(PerfilRepository repository) {
        this.repository = repository;
    }

    public void cadastra(PerfilRequest request) {
        repository.findByNomePerfil(request.nomePerfil())
                .ifPresent(perfil -> {
                    throw new IllegalArgumentException("Perfil já cadastrado com o nome: " + request.nomePerfil());
                });

        Perfil perfil = new Perfil();
        perfil.setNomePerfil(request.nomePerfil());
        repository.save(perfil);
    }

    public Perfil validaPerfil(String nomePerfil) {
        return repository.findByNomePerfil(nomePerfil)
                .orElseThrow(() -> new IllegalArgumentException("Perfil não existe com o nome: " + nomePerfil));
    }
}
