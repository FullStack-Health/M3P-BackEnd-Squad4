package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.login.LoginRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroResponse;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioResponse;
import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.mappers.UsuarioMapper;
import br.senai.lab365.LABMedical.mappers.UsuarioPreRegistroMapper;
import br.senai.lab365.LABMedical.repositories.PerfilRepository;
import br.senai.lab365.LABMedical.repositories.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioPreRegistroMapper preRegistroMapper;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PerfilRepository perfilRepository;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @PersistenceContext
    private EntityManager entityManager;

    public UsuarioPreRegistroResponse cadastra(UsuarioPreRegistroRequest usuarioRequest) {
        logger.info("Iniciando cadastro de usuário para o email: {}", usuarioRequest.getEmail());

        if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new DuplicateKeyException("O email " + usuarioRequest.getEmail() + " já foi cadastrado");
        }

        Perfil perfil = perfilRepository.findByNomePerfil(usuarioRequest.getNomePerfil());
        if (perfil == null || (!"ADMIN".equals(perfil.getNomePerfil())) && (!"MÉDICO".equals(perfil.getNomePerfil()))) {
            logger.error("Perfil inválido: {}", usuarioRequest.getNomePerfil());
            throw new IllegalArgumentException("Perfil inválido. Deve ser \"ADMIN\" ou \"MÉDICO\"");
        }

        String senhaOriginal = usuarioRequest.getPassword();
        String senhaCriptografada = passwordEncoder.encode(senhaOriginal);
        logger.info("Senha criptografada com sucesso");

        Usuario usuario = preRegistroMapper.toEntity(usuarioRequest);
        usuario.setPassword(senhaCriptografada);
        usuario.setSenhaComMascara(senhaOriginal);
        usuario.setPerfilList(Set.of(perfil));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        logger.info("Usuário salvo com sucesso: {}", usuarioSalvo.getEmail());

        UsuarioPreRegistroResponse response = preRegistroMapper.toResponse(usuarioSalvo);
        response.setSenhaComMascara(usuario.getSenhaComMascara());
        logger.info("Senha com máscara: {}", usuario.getSenhaComMascara());

        return response;
    }

    @Transactional
    public void criaUsuarioAdminSeNaoExiste() {
        if (!usuarioRepository.existsByEmail("admin@example.com")) {
            Usuario usuario = new Usuario();
            usuario.setNome("Admin");
            usuario.setEmail("admin@example.com");
            usuario.setDataNascimento(LocalDate.parse("2000-01-01"));
            usuario.setCpf("987.654.321-00");
            String senhaOriginal = "admin123";
            usuario.setPassword(passwordEncoder.encode(senhaOriginal));
            usuario.setSenhaComMascara(senhaOriginal);

            Perfil perfil = perfilRepository.findByNomePerfil("ADMIN");
            Perfil manegedPerfil = entityManager.merge(perfil);

            usuario.setPerfilList(Collections.singleton(perfilRepository.findByNomePerfil("ADMIN")));
            usuarioRepository.save(usuario);

            String senhaComMascara = usuarioMapper.mascaraSenha(senhaOriginal);
            logger.info("Senha com máscara: {}", usuario.getSenhaComMascara());
        }
    }

    public Usuario validaUsuario(LoginRequest loginRequest) throws RuntimeException {
        Usuario usuario = usuarioRepository
                .findByEmailIgnoreCaseContaining(loginRequest.email())
                .orElseThrow(
                        () -> new EntityNotFoundException("Nenhum usuário com este email foi cadastrado: "
                                + loginRequest.email())
                );

        if (!passwordEncoder.matches(loginRequest.password(), usuario.getPassword())) {
            throw new EntityNotFoundException("Senha errada para este usuário");
        }
        return usuario;
    }

    public List<UsuarioResponse> lista() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> {
                    UsuarioResponse response = usuarioMapper.toResponse(usuario);
                    return response;
                })
                .collect(Collectors.toList());
    }
}