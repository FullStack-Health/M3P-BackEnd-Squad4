package br.senai.lab365.LABMedical.repositories;

import br.senai.lab365.LABMedical.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
//    Optional<Usuario> findByCpf(Long id);
}