package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.login.LoginRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioResponse;
import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.mappers.UsuarioMapper;
import br.senai.lab365.LABMedical.repositories.PerfilRepository;
import br.senai.lab365.LABMedical.repositories.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PerfilRepository perfilRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public UsuarioResponse cadastra(UsuarioRequest usuarioRequest) {
        if (usuarioRepository.existsByCpf(usuarioRequest.getCpf())) {
            throw new DuplicateKeyException("CPF já cadastrado com este número: " + usuarioRequest.getCpf());
        }

        if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new DuplicateKeyException("O email " + usuarioRequest.getEmail() + " já foi cadastrado");
        }

        Perfil perfil = perfilRepository.findByNomePerfil(usuarioRequest.getNomePerfil());

        String senhaCriptografada = passwordEncoder.encode(usuarioRequest.getPassword());

        Usuario usuario = mapper.toEntity(usuarioRequest);
        usuario.setPassword(senhaCriptografada);
//        usuario.setPassword(usuarioRequest.getPassword());
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        usuario.setPerfilList(Set.of(perfil));

        return mapper.toResponse(usuarioSalvo);
    }

    @Transactional
    public void criaUsuarioAdminSeNaoExiste() {
        if (!usuarioRepository.existsByEmail("admin@example.com")) {
            Usuario usuario = new Usuario();
            usuario.setNome("Admin");
            usuario.setEmail("admin@example.com");
            usuario.setDataNascimento(LocalDate.parse("2000-01-01"));
            usuario.setCpf("987.654.321-00");
//            usuario.setPassword("admin");
//
            usuario.setPassword(passwordEncoder.encode("admin123"));

            Perfil perfil = perfilRepository.findByNomePerfil("ADMIN");
            Perfil manegedPerfil = entityManager.merge(perfil);

            usuario.setPerfilList(Collections.singleton(perfilRepository.findByNomePerfil("ADMIN")));
            usuarioRepository.save(usuario);
        }
    }

    public Usuario validaUsuario(LoginRequest loginRequest) throws RuntimeException {
        Usuario usuario = usuarioRepository
                .findByEmail(loginRequest.email())
                .orElseThrow(
                        ()->new EntityNotFoundException("Nenhum usuário com este email foi cadastrado: "
                                + loginRequest.email())
                );

        if(!passwordEncoder.matches(loginRequest.password(),usuario.getPassword())){
            throw new EntityNotFoundException("Senha errada para este usuário");
        }
        return usuario;
    }



}
