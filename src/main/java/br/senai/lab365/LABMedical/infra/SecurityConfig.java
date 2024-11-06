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
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/usuarios/perfil/{nomePerfil}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.PATCH, "/usuarios/email/{email}/redefinir-senha")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET, "/usuarios")
                        .permitAll()

                        .requestMatchers(HttpMethod.GET,  "/usuarios/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.PUT, "/usuarios/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.DELETE, "/usuarios/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.POST, "/pacientes")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.GET, "/pacientes/{id}")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO", "SCOPE_PACIENTE")

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
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO", "SCOPE_PACIENTE")

                        .requestMatchers(HttpMethod.GET, "/pacientes/{id}/exames")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.GET, "/pacientes/{id}/consultas")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .requestMatchers(HttpMethod.GET, "/dashboard")
                        .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_MÉDICO")

                        .anyRequest()
                        .authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
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
