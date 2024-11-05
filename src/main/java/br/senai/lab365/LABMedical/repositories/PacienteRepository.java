package br.senai.lab365.LABMedical.repositories;

import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    /**
     * Verifica se existe um paciente com o CPF fornecido.
     * Utilizado durante o cadastro para garantir que o CPF é único.
     */
    boolean existsByCpf(String cpf);

    /**
     * Verifica se existe um paciente com o CPF fornecido, excluindo o paciente com o ID especificado.
     * Utilizado durante a atualização para evitar conflito de CPF ao atualizar os dados do paciente.
     */
    boolean existsByCpfAndIdNot(String cpf, Long id);

    /**
     * Busca pacientes pelo nome, ignorando maiúsculas e minúsculas, e que contenham o termo fornecido.
     */
    Page<Paciente> findByNomeIgnoreCaseContaining(String nome, Pageable paginacao);

    /**
     * Busca pacientes pelo telefone, contendo o termo fornecido.
     */
    Page<Paciente> findByTelefoneContaining(String telefone, Pageable paginacao);

    /**
     * Busca pacientes pelo email, contendo o termo fornecido.
     */
    Page<Paciente> findByEmailContaining(String email, Pageable paginacao);

    /**
     * Busca pacientes pelo nome, telefone e email, ignorando maiúsculas e minúsculas, e que contenham os termos fornecidos.
     */
    Page<Paciente> findByNomeIgnoreCaseContainingAndTelefoneContainingAndEmailContaining(
            String nome, String telefone, String email, Pageable paginacao);

    /**
     * Busca paciente pelo ID e nome, ignorando maiúsculas e minúsculas, e que contenham o termo fornecido.
     */
    Page<Paciente> findByIdAndNomeIgnoreCaseContaining(Long id, String nome, Pageable paginacao);

    /**
     * Encontra um paciente pelo ID.
     */
    Optional<Paciente> findById(Long id);

    /**
     * Encontra um paciente pelo ID com paginação.
     */
    Page<Paciente> findById(Long id, Pageable paginacao);

    Optional <Paciente> findByUsuario(Usuario usuario);
}
