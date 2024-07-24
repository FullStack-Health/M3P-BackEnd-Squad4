package br.senai.lab365.LABMedical.repositories;

import br.senai.lab365.LABMedical.entities.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsByCpf(String cpf);

    Page<Paciente> findByNomeIgnoreCaseContaining(String nome, Pageable paginacao);

    Page<Paciente> findByTelefoneContaining(String telefone, Pageable paginacao);

    Page<Paciente> findByEmailContaining(String email, Pageable paginacao);

    Page<Paciente> findByNomeIgnoreCaseContainingAndTelefoneContainingAndEmailContaining(
            String nome, String telefone, String email, Pageable paginacao);

}
