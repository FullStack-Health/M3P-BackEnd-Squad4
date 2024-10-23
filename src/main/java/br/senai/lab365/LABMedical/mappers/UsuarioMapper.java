package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.usuario.UsuarioRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioResponse;
import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.repositories.PerfilRepository;
import br.senai.lab365.LABMedical.services.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private PerfilRepository perfilRepository;

    public Usuario toEntity(UsuarioRequest request) {
        if (request == null) {
            return null;
        }

        Perfil perfil = perfilRepository.findByNomePerfil(request.getNomePerfil());
        Set<Perfil> perfis = new HashSet<>();
        if (perfil != null) {
            perfis.add(perfil);
        }
        return new Usuario(
                null,
                request.getNome(),
                request.getEmail(),
                request.getDataNascimento(),
                request.getCpf(),
                request.getPassword(),
                perfis
        );
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        List<String> listaNomesPerfis = usuario.getPerfilList().stream()
                .map(Perfil::getNomePerfil)
                .collect(Collectors.toList());

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataNascimento(),
                usuario.getCpf(),
                usuario.getPassword(),
                listaNomesPerfis
        );
    }

    public List<UsuarioResponse> toResponse(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}