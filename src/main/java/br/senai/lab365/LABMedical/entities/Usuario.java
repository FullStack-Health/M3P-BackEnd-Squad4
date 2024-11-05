package br.senai.lab365.LABMedical.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "usuarios")
public class Usuario implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

//    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

//    @Column(unique = true, nullable = false)
    private String cpf;

    private String telefone;

    @Column(nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_perfis",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_perfil")
    )
    private Set<Perfil> perfilList;

    @Getter
    @Column(name = "senha_mascara")
    private String senhaComMascara;

    public Usuario(String email, String password, Set<Perfil> perfis) {
        this.email = email;
        this.password = password;
        this.perfilList = perfis;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfilList;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setSenhaComMascara(String password) {
        if (password == null || password.length() < 4) {
            this.senhaComMascara = password;
        } else {
            StringBuilder senhaComMascara = new StringBuilder(password.substring(0, 4));
            for (int i = 4; i < password.length(); i++) {
                senhaComMascara.append("*");
            }
            this.senhaComMascara = senhaComMascara.toString();
        }
    }
}
