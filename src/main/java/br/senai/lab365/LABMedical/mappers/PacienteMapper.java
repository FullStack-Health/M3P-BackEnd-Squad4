package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.paciente.EnderecoRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteGetRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.paciente.PacienteResponse;
import br.senai.lab365.LABMedical.dtos.usuario.UsuarioRequest;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.entities.Perfil;
import br.senai.lab365.LABMedical.entities.Usuario;
import br.senai.lab365.LABMedical.repositories.PerfilRepository;
import br.senai.lab365.LABMedical.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Component
public class PacienteMapper {

    private final EnderecoMapper enderecoMapper;
    private final UsuarioMapper usuarioMapper;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public PacienteMapper(EnderecoMapper enderecoMapper,
//                          UsuarioRepository usuarioRepository,
                          UsuarioMapper usuarioMapper,
                          PerfilRepository perfilRepository,
                          PasswordEncoder passwordEncoder)
    {
        this.enderecoMapper = enderecoMapper;
        this.usuarioMapper = usuarioMapper;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Paciente toEntity(PacienteRequest pacienteRequest) {
        if (pacienteRequest == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(pacienteRequest.getEmail());

        // Remove caracteres não numéricos e criptografa como senha
        String cpfNumerico = pacienteRequest.getCpf().replaceAll("\\D", "");
        String senhaCriptografada = passwordEncoder.encode(cpfNumerico);
        usuario.setPassword(senhaCriptografada);

        // Cria a máscara da senha para exibir os primeiros 4 caracteres
        String mascaraSenha = cpfNumerico.length() <= 4
                ? cpfNumerico
                : cpfNumerico.substring(0, 4) + "*".repeat(cpfNumerico.length() - 4);
        usuario.setSenhaComMascara(mascaraSenha);

        usuario.setCpf(pacienteRequest.getCpf());
        usuario.setDataNascimento(pacienteRequest.getDataNascimento());
        usuario.setNome(pacienteRequest.getNome());
        usuario.setTelefone(pacienteRequest.getTelefone());


        // Recupera o perfil do paciente e o adiciona
        Perfil perfilPaciente = perfilRepository.findByNomePerfil("PACIENTE");
        Set<Perfil> perfis = new HashSet<>();
        if (perfilPaciente != null) {
            perfis.add(perfilPaciente);
        }
        usuario.setPerfilList(perfis);

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
                usuario
        );
    }

    public PacienteResponse toResponse(Paciente paciente) {
        if (paciente == null) {
            return null;
        }

//        Long usuarioId = paciente.getUsuario() != null ? paciente.getUsuario().getId(): null;

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
                usuarioMapper.toResponse(paciente.getUsuario())
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
            if (enderecoRequest.getComplemento() != null)
                paciente.getEndereco().setComplemento(enderecoRequest.getComplemento());
            if (enderecoRequest.getBairro() != null) paciente.getEndereco().setBairro(enderecoRequest.getBairro());
            if (enderecoRequest.getCidade() != null) paciente.getEndereco().setCidade(enderecoRequest.getCidade());
            if (enderecoRequest.getEstado() != null) paciente.getEndereco().setEstado(enderecoRequest.getEstado());
            if (enderecoRequest.getPtoReferencia() != null)
                paciente.getEndereco().setPtoReferencia(enderecoRequest.getPtoReferencia());
        }

        // Atualizar os campos do usuário associado
        Usuario usuario = paciente.getUsuario();
        if (request.getUsuario() != null) {
            UsuarioRequest usuarioRequest = request.getUsuario();
            if (usuarioRequest.getNome() != null) usuario.setNome(usuarioRequest.getNome());
            if (usuarioRequest.getEmail() != null) usuario.setEmail(usuarioRequest.getEmail());
            if (usuarioRequest.getCpf() != null) usuario.setCpf(usuarioRequest.getCpf());
            if (usuarioRequest.getDataNascimento() != null)
                usuario.setDataNascimento(usuarioRequest.getDataNascimento());
            if (usuarioRequest.getTelefone() != null) usuario.setTelefone(usuarioRequest.getTelefone());
            if (usuarioRequest.getPassword() != null) usuario.setPassword(usuarioRequest.getPassword());
            if (usuarioRequest.getSenhaComMascara() != null)
                usuario.setSenhaComMascara(usuarioRequest.getSenhaComMascara());
            if (usuarioRequest.getNomePerfil() != null) {
                Perfil perfil = perfilRepository.findByNomePerfil(usuarioRequest.getNomePerfil());
                if (perfil != null) {
                    Set<Perfil> perfis = new HashSet<>();
                    perfis.add(perfil);
                    usuario.setPerfilList(perfis);
                }
            }
        }
    }

    public PacienteGetRequest getRequestToResponse(Paciente paciente) {
        PacienteGetRequest response = new PacienteGetRequest();
        response.setId(paciente.getId());
        response.setNome(paciente.getNome());
        response.setTelefone(paciente.getTelefone());
        response.setEmail(paciente.getEmail());
        response.setIdade(calcularIdade(paciente.getDataNascimento()));
        response.setConvenio(paciente.getConvenio());
        response.setCpf(paciente.getCpf());
        response.setDataNascimento(paciente.getDataNascimento());
        response.setContatoEmergencia(paciente.getContatoEmergencia());
        response.setListaAlergias(paciente.getListaAlergias());
        response.setListaCuidados(paciente.getListaCuidados());
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