package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.login.LoginRequest;
import br.senai.lab365.LABMedical.dtos.login.LoginResponse;
import br.senai.lab365.LABMedical.dtos.usuario.RedefinicaoSenhaRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioPreRegistroResponse;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import br.senai.lab365.LABMedical.services.UsuarioService;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtEncoder jwtEncoder;
    private final UsuarioService usuarioService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final PacienteRepository pacienteRepository;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse geraToken(@RequestBody LoginRequest loginRequest) {
        logger.info("Recebendo requisição de login para o email: {}", loginRequest.email());

        //Validação do usuário e credenciais
        Usuario usuario = usuarioService.validaUsuario(loginRequest);
        if (usuario == null) {
            logger.error("Falha ao autenticar o usuário: {}", loginRequest.email());
            throw new RuntimeException("Usuário não encontrado ou credenciais inválidas.");
        }

        Instant agora = Instant.now();

        //Obtém os escopos associados ao usuário
        String scope = usuario.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Adicionado: Busca o ID do paciente associado ao usuário
        String pacienteId = "";
        if (scope.contains("PACIENTE")) {
            pacienteId = pacienteRepository.findByUsuario(usuario)
                    .map(paciente -> paciente.getId().toString())
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado para este usuário."));
        }

        // Criação do JWT com os claims personalizados
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(3600))
                .subject(usuario.getEmail())
                .claim("scope", scope)
                .claim("pacienteId", pacienteId) // Adicionado: Inclui ID do paciente no token
                .build();

        // Codifica e retorna o JWT
        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        logger.info("Usuário autenticado com sucesso: {}", loginRequest.email());


        List<String> listaNomesPerfis = usuario.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new LoginResponse(token, 3600L, listaNomesPerfis, pacienteId);
    }

    @PostMapping("/usuarios/pre-registro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioPreRegistroResponse cadastra(@Valid @RequestBody UsuarioPreRegistroRequest usuarioRequest) {
        logger.info("POST /usuarios/pre-registro - Iniciando o cadastro de: {}", usuarioRequest.getEmail());
        UsuarioPreRegistroResponse response = usuarioService.cadastra(usuarioRequest);
        logger.info("POST /usuarios/pre-registro - Usuário cadastrado com sucesso: {}", response.getEmail());
        return response;
    }

    @PatchMapping("/usuarios/email/{email}/redefinir-senha")
    public UsuarioPreRegistroResponse redefineSenha(@PathVariable String email,
                                                    @Valid @RequestBody RedefinicaoSenhaRequest request) {
        logger.info("PATCH /usuarios/email/{email}/redefinir-senha - Iniciando a redefinição de senha do usuário: {}, novaSenha: {}", email, request.getPassword());
        UsuarioPreRegistroResponse response = usuarioService.redefine(email, request);
        logger.info("PATCH /usuarios/email/{email}/redefinir-senha - Redefinição de senha concluída com sucesso para o usuário: {}", email);
        return response;
    }
}
