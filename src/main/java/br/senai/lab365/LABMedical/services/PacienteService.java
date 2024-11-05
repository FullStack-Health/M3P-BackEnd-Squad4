package br.senai.lab365.LABMedical.services;

import br.senai.lab365.LABMedical.dtos.paciente.PacienteGetRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponse;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponsePagination;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.exceptions.CpfDuplicadoException;
import br.senai.lab365.LABMedical.exceptions.PacienteNaoEncontradoException;
import br.senai.lab365.LABMedical.mappers.PacienteMapper;
import br.senai.lab365.LABMedical.repositories.EnderecoRepository;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import br.senai.lab365.LABMedical.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PacienteMapper pacienteMapper;

    public PacienteResponse cadastra(@Valid PacienteRequest pacienteRequest) {
        // Verificar se o CPF já existe
        if (pacienteRepository.existsByCpf(pacienteRequest.getCpf())) {
            throw new CpfDuplicadoException("CPF já cadastrado com este número: " + pacienteRequest.getCpf());
        }
        // Converter o DTO PacienteRequest para a entidade Paciente
        Paciente paciente = pacienteMapper.toEntity(pacienteRequest);
        // Salvar a entidade Usuario antes de salvar o Paciente
        usuarioRepository.save(paciente.getUsuario());
        //endereco
        enderecoRepository.save(paciente.getEndereco());
        // Salvar a entidade Paciente
        Paciente pacienteSalvo = pacienteRepository.save(paciente);


        // Converter a entidade Paciente salva para o DTO PacienteResponse
        return pacienteMapper.toResponse(pacienteSalvo);
    }

    public PacienteResponse busca(Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);

        return pacienteMapper.toResponse(
                paciente.orElseThrow(
                        ()-> new EntityNotFoundException("Paciente não encontrado com o id: " + id)));

    }

    public PacienteResponse atualiza(Long id, PacienteRequest request) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Paciente não encontrado com o id: " + id)
                );

        pacienteMapper.atualizaPacienteDesdeRequest(paciente, request);

        paciente = pacienteRepository.save(paciente);
        return pacienteMapper.toResponse(paciente);
    }

    @Transactional
    public void remove(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        // 1. Remover associações de perfil antes da exclusão
        Usuario usuario = paciente.getUsuario();
        if (usuario != null) {
            usuario.getPerfilList().clear(); // Remove perfis associados ao usuário
            usuarioRepository.save(usuario); // Atualiza o usuário sem perfis
        }

        // 2. Excluir o paciente
        pacienteRepository.delete(paciente);
    }


   public PacienteResponsePagination lista(Long id, String nome, String telefone, String email, int numeroPagina, int tamanhoPagina) {
    Pageable paginacao = PageRequest.of(numeroPagina, tamanhoPagina);
    Page<Paciente> paginaPacientes;

    if (id != null) {
        paginaPacientes = pacienteRepository.findById(id, paginacao);
    } else if (nome != null && telefone != null && email != null) {
        paginaPacientes = pacienteRepository.findByNomeIgnoreCaseContainingAndTelefoneContainingAndEmailContaining(nome, telefone, email, paginacao);
    } else if (nome != null) {
        paginaPacientes = pacienteRepository.findByNomeIgnoreCaseContaining(nome, paginacao);
    } else if (telefone != null) {
        paginaPacientes = pacienteRepository.findByTelefoneContaining(telefone, paginacao);
    } else if (email != null) {
        paginaPacientes = pacienteRepository.findByEmailContaining(email, paginacao);
    } else {
        paginaPacientes = pacienteRepository.findAll(paginacao);
    }

    List<PacienteGetRequest> pacientes = paginaPacientes.getContent().stream()
            .map(pacienteMapper::getRequestToResponse)
            .collect(Collectors.toList());

   if (pacientes.isEmpty()) {
       String queryParameter = "id:" + id + ", nome:" + nome + ", telefone:" + telefone + ", email:" + email;
       throw new PacienteNaoEncontradoException("Nenhum paciente encontrado", queryParameter);
   }

    PacienteResponsePagination response = new PacienteResponsePagination();
    response.setPacientes(pacientes);
    response.setTotalPaginas(paginaPacientes.getTotalPages());
    response.setTamanhoPagina(tamanhoPagina);
    response.setPaginaAtual(numeroPagina);
    response.setTotalElementos((int) paginaPacientes.getTotalElements());
    response.setUltima(paginaPacientes.isLast());

    return response;
}

    public Paciente createPaciente(PacienteRequest request) {
        Paciente paciente = pacienteMapper.toEntity(request);
        usuarioRepository.save(paciente.getUsuario()); // Salva o usuário primeiro
        return pacienteRepository.save(paciente); // Salva o paciente
    }

    public boolean existePaciente(Long id) {
        return pacienteRepository.existsById(id);
    }
}