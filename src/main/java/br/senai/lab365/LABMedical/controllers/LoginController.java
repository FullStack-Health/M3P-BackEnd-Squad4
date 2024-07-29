package br.senai.lab365.LABMedical.controllers;

import br.senai.lab365.LABMedical.dtos.login.LoginRequest;
import br.senai.lab365.LABMedical.dtos.login.LoginResponse;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtEncoder jwtEncoder;
    private final UsuarioService usuarioService;
    private static long TEMPO_EXPIRACAO = 36000L;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse geraToken(@RequestBody LoginRequest loginRequest) {

        logger.info("Recebendo requisição de login para o email: {}", loginRequest.email());

        Usuario usuario = usuarioService.validaUsuario(loginRequest);
        if (usuario == null) {
            logger.error("Falha ao autenticar o usuário: {}", loginRequest.email());
        }
        Instant agora = Instant.now();
            String scope = usuario.getAuthorities()
                .stream()
                .map(autority -> autority.getAuthority())
                .collect(Collectors.joining(" "));
//        String scope = "ADMIN";
        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer("self")
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(TEMPO_EXPIRACAO))
                .subject(usuario.getEmail())
                .claim("perfil", scope)
                .build();

        var valorJwt = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        logger.info("Usuário autenticado com sucesso: {}", loginRequest.email());

        return new LoginResponse(valorJwt, TEMPO_EXPIRACAO);
    }

    @PostMapping("/login/admin")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse loginAdmin() {
        LoginRequest loginRequest = new LoginRequest("admin@example.com", "admin");

        return geraToken(loginRequest);
    }

}
