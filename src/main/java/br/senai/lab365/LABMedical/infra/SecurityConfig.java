package br.senai.lab365.LABMedical.infra;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.public.key}")
    private RSAPublicKey pub;
    @Value("${jwt.private.key}")
    private RSAPrivateKey priv;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/usuarios/pre-registro")
                        .permitAll()

                        .requestMatchers(HttpMethod.PATCH, "/usuarios/email/{email}/redefinir-senha")
                        .permitAll()

                        //Controle de acesso perfil
                        .requestMatchers(HttpMethod.GET, "/usuarios", "/usuarios/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        //Pacientes: Apenas admin e médico podem criar
                        .requestMatchers(HttpMethod.POST, "/pacientes")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        //Adicionado: Restringe acesso ao prontuário do próprio paciente
                        .requestMatchers(HttpMethod.GET, "/pacientes/{id}/prontuarios")
                        .access((authentication, context) -> {
                            // Obtém o ID do paciente da URL
                            String patientIdFromRequest = context.getVariables().get("id").toString();

                            // Extrai o ID do paciente do token JWT
                            String tokenPatientId = authentication.get().getAuthorities().stream()
                                    .filter(granted -> granted.getAuthority().startsWith("SCOPE_PACIENTE:"))
                                    .map(granted -> granted.getAuthority().split(":")[1])
                                    .findFirst()
                                    .orElse("");

                            // Retorna uma AuthorizationDecision baseada na comparação dos IDs
                            return new AuthorizationDecision(tokenPatientId.equals(patientIdFromRequest));
                        })

                        //Retirado
                        //.requestMatchers(HttpMethod.GET, "/pacientes/{id}")
                        //.hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO", "SCOPE_PACIENTE")

                        .requestMatchers(HttpMethod.PUT, "/pacientes/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.DELETE, "/pacientes/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.GET, "/pacientes")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.POST, "/consultas")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.GET, "/consultas/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO", "SCOPE_PACIENTE")

                        .requestMatchers(HttpMethod.PUT, "/consultas/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.DELETE, "/consultas/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.POST, "/exames")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.GET, "/exames/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO", "SCOPE_PACIENTE")

                        .requestMatchers(HttpMethod.PUT, "/exames/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.DELETE, "/exames/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.GET, "/pacientes/prontuarios")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.GET, "/pacientes/{id}/prontuarios")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.GET, "/dashboard")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .anyRequest()
                        .authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        ;

        return http.build();
    }


//    @Bean
//    public JwtDecoder jwtDecoder() {
//        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(this.pub).build();
//        jwtDecoder.setJwtValidator(JwtValidators.createDefaultWithIssuer("http://auth-server"));
//        jwtDecoder.setClaimSetConverter(new CustomClaimTypeConverter());
//        return jwtDecoder;
//    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.pub).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.pub).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
