package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.paciente.EnderecoRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteGetRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponse;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioRequest;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioResponse;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Component
public class PacienteMapper {

    private final EnderecoMapper enderecoMapper;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public PacienteMapper(EnderecoMapper enderecoMapper, UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.enderecoMapper = enderecoMapper;
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public Paciente toEntity(PacienteRequest pacienteRequest) {
        if (pacienteRequest == null) {
            return null;
        }

        UsuarioRequest usuarioRequest = Optional.ofNullable(pacienteRequest.getUsuario())
                .orElseThrow(() -> new IllegalArgumentException("UsuarioRequest não pode ser null"));

        Long usuarioId = usuarioRequest.getId();

        return new Paciente(
                null,
                pacienteRequest.getNome(),
                pacienteRequest.getGenero(),
                pacienteRequest.getDataNascimento(),
                pacienteRequest.getCpf(),
                pacienteRequest.getRg(),
                pacienteRequest.getOrgaoExpedidor(),
                pacienteRequest.getEstadoCivil(),
                pacienteRequest.getTelefone(),
                pacienteRequest.getEmail(),
                pacienteRequest.getNaturalidade(),
                pacienteRequest.getContatoEmergencia(),
                pacienteRequest.getListaAlergias(),
                pacienteRequest.getListaCuidados(),
                pacienteRequest.getConvenio(),
                pacienteRequest.getNumeroConvenio(),
                pacienteRequest.getValidadeConvenio(),
                enderecoMapper.toEntity(pacienteRequest.getEndereco()),
                usuarioRepository.getReferenceById(usuarioId)
        );
    }

    public PacienteResponse toResponse(Paciente paciente) {
        if (paciente == null) {
            return null;
        }

        Long usuarioId = paciente.getUsuario() != null ? paciente.getUsuario().getId(): null;

        return new PacienteResponse(
                paciente.getId(),
                paciente.getNome(),
                paciente.getGenero(),
                paciente.getDataNascimento(),
                paciente.getCpf(),
                paciente.getRg(),
                paciente.getOrgaoExpedidor(),
                paciente.getEstadoCivil(),
                paciente.getTelefone(),
                paciente.getEmail(),
                paciente.getNaturalidade(),
                paciente.getContatoEmergencia(),
                paciente.getListaAlergias(),
                paciente.getListaCuidados(),
                paciente.getConvenio(),
                paciente.getNumeroConvenio(),
                paciente.getValidadeConvenio(),
                enderecoMapper.toResponse(paciente.getEndereco()),
                usuarioMapper.toResponse(usuarioRepository.getReferenceById(usuarioId))
                );
    }

    public void atualizaPacienteDesdeRequest(Paciente paciente, PacienteRequest request) {
        if (request.getNome() != null) paciente.setNome(request.getNome());
        if (request.getGenero() != null) paciente.setGenero(request.getGenero());
        if (request.getDataNascimento() != null) paciente.setDataNascimento(request.getDataNascimento());
        if (request.getCpf() != null) paciente.setCpf(request.getCpf());
        if (request.getRg() != null) paciente.setRg(request.getRg());
        if (request.getOrgaoExpedidor() != null) paciente.setOrgaoExpedidor(request.getOrgaoExpedidor());
        if (request.getEstadoCivil() != null) paciente.setEstadoCivil(request.getEstadoCivil());
        if (request.getTelefone() != null) paciente.setTelefone(request.getTelefone());
        if (request.getEmail() != null) paciente.setEmail(request.getEmail());
        if (request.getNaturalidade() != null) paciente.setNaturalidade(request.getNaturalidade());
        if (request.getContatoEmergencia() != null) paciente.setContatoEmergencia(request.getContatoEmergencia());
        if (request.getListaAlergias() != null) paciente.setListaAlergias(request.getListaAlergias());
        if (request.getListaCuidados() != null) paciente.setListaCuidados(request.getListaCuidados());
        if (request.getConvenio() != null) paciente.setConvenio(request.getConvenio());
        if (request.getNumeroConvenio() != null) paciente.setNumeroConvenio(request.getNumeroConvenio());
        if (request.getValidadeConvenio() != null) paciente.setValidadeConvenio(request.getValidadeConvenio());
        if (request.getEndereco() != null) {
            @Valid EnderecoRequest enderecoRequest = request.getEndereco();
            if (enderecoRequest.getCep() != null) paciente.getEndereco().setCep(enderecoRequest.getCep());
            if (enderecoRequest.getRua() != null) paciente.getEndereco().setRua(enderecoRequest.getRua());
            if (enderecoRequest.getNumero() != null) paciente.getEndereco().setNumero(enderecoRequest.getNumero());
            if (enderecoRequest.getComplemento() != null) paciente.getEndereco().setComplemento(enderecoRequest.getComplemento());
            if (enderecoRequest.getBairro() != null) paciente.getEndereco().setBairro(enderecoRequest.getBairro());
            if (enderecoRequest.getCidade() != null) paciente.getEndereco().setCidade(enderecoRequest.getCidade());
            if (enderecoRequest.getEstado() != null) paciente.getEndereco().setEstado(enderecoRequest.getEstado());
            if (enderecoRequest.getPtoReferencia() != null) paciente.getEndereco().setPtoReferencia(enderecoRequest.getPtoReferencia());
        }
    }

    public PacienteGetRequest getRequestToResponse(Paciente paciente) {
        PacienteGetRequest response = new PacienteGetRequest();
        response.setNome(paciente.getNome());
        response.setTelefone(paciente.getTelefone());
        response.setEmail(paciente.getEmail());
        response.setIdade(calcularIdade(paciente.getDataNascimento()));
        response.setConvenio(paciente.getConvenio());
        return response;
    }

    private int calcularIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return 0;
        }
        LocalDate hoje = LocalDate.now();
        return Period.between(dataNascimento, hoje).getYears();
    }
}
