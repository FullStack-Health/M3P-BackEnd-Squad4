//package br.senai.lab365.LABMedical.repositories;
//
//import br.senai.lab365.LABMedical.entities.Paciente;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface ProntuarioRepository extends JpaRepository<Paciente, Long> {
//
//    Page<Paciente> findByNomeIgnoreCaseContaining(String nome, Pageable paginacao);
//    Page<Paciente> findByIdAndNomeIgnoreCaseContaining(Long id, String nome, Pageable paginacao);
//    Optional<Paciente> findById(Long id);
//}
