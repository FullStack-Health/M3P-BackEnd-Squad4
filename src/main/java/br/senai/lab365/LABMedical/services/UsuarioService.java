package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.PerfilRequest;
import br.senai.lab365.LABMedical.dtos.login.LoginRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioResponse;
import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.mappers.UsuarioMapper;
import br.senai.lab365.LABMedical.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PerfilService perfilService;


    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper mapper, BCryptPasswordEncoder passwordEncoder, PerfilService perfilService) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.perfilService = perfilService;
    }


    public UsuarioResponse cadastra(UsuarioRequest usuarioRequest) {
        if (usuarioRepository.existsByCpf(usuarioRequest.getCpf())) {
            throw new DuplicateKeyException("CPF já cadastrado com este número: " + usuarioRequest.getCpf());
        }

        if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new DuplicateKeyException("O email " + usuarioRequest.getEmail() + " já foi cadastrado");
        }

        Perfil perfil = perfilService.validaPerfil(usuarioRequest.getNomePerfil());

        String senhaCriptografada = passwordEncoder.encode(usuarioRequest.getPassword());

        Usuario usuario = mapper.toEntity(usuarioRequest);
        usuario.setPassword(senhaCriptografada);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        usuario.setPerfilList(Set.of(perfil));

        return mapper.toResponse(usuarioSalvo);
    }



    public Usuario validaUsuario(LoginRequest loginRequest) throws RuntimeException {
        Usuario usuario = usuarioRepository
                .findByEmail(loginRequest.email())
                .orElseThrow(
                        ()->new RuntimeException("Usuário não existe com o email "
                                +loginRequest.email())
                );

        if(!passwordEncoder.matches(loginRequest.password(),usuario.getPassword())){
            throw new RuntimeException("Senha errada para usuario com email "
                    + loginRequest.email());
        }
        return usuario;
    }


}
