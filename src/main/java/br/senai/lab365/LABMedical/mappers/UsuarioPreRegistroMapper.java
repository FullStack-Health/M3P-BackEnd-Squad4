package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroResponse;
import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.repositories.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioPreRegistroMapper {

    @Autowired
    private PerfilRepository perfilRepository;

    public Usuario toEntity(UsuarioPreRegistroRequest request) {
        if (request == null) {
            return null;
        }

        Perfil perfil = perfilRepository.findByNomePerfil(request.getNomePerfil());
        Set<Perfil> perfis = new HashSet<>();
        if (perfil != null) {
            perfis.add(perfil);
        }

        String senhaComMascara = mascaraSenha(request.getSenhaComMascara());

        return new Usuario(
                null,
                null,
                request.getEmail(),
                null,
                null,
                null,
                request.getPassword(),
                perfis,
                senhaComMascara
        );
    }

    public UsuarioPreRegistroResponse toResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        List<String> listaNomesPerfis = usuario.getPerfilList().stream()
                .map(Perfil::getNomePerfil)
                .collect(Collectors.toList());

        return new UsuarioPreRegistroResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getPassword(),
                listaNomesPerfis,
                usuario.getSenhaComMascara()
        );
    }

    public String mascaraSenha(String senhaOriginal) {
        if (senhaOriginal == null) {
            return null;
        }
        int length = senhaOriginal.length();
        if (length <= 4) {
            return senhaOriginal;
        }
        StringBuilder comMascara = new StringBuilder(senhaOriginal.substring(0, 4));
        for (int i = 4; i < length; i++) {
            comMascara.append('*');
        }
        return comMascara.toString();
    }
}