package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.PacienteRequest;
import br.senai.lab365.LABMedical.dtos.PacienteResponse;
import br.senai.lab365.LABMedical.entities.Paciente;
import br.senai.lab365.LABMedical.entities.Endereco;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    private final EnderecoMapper enderecoMapper;

    public PacienteMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public Paciente toEntity(PacienteRequest pacienteRequest) {
        if (pacienteRequest == null) {
            return null;
        }
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
                enderecoMapper.toEntity(pacienteRequest.getEndereco())
        );
    }

    public PacienteResponse toResponse(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        return new PacienteResponse(
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
                enderecoMapper.toResponse(paciente.getEndereco())
        );
    }
}
