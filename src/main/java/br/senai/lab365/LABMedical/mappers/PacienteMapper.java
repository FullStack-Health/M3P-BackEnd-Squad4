package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.PacienteRequest;
import br.senai.lab365.LABMedical.entities.Paciente;

import java.time.LocalDate;

public class PacienteMappers {

    public static Paciente map(PacienteRequest source) {
        if (source == null) return null;

        Paciente target = new Paciente();

        target.setNome(source.getNome());
        target.setGenero(source.getGenero());
        target.setDataNascimento(source.getDataNascimento());
        target.setCpf(source.getCpf());
        target.setRg(source.getRg());
        target.setOrgaoExpedidor(source.getOrgaoExpedidor());
        target.setEstadoCivil(source.getEstadoCivil());
        target.setTelefone(source.getTelefone());
        target.setEmail(source.getEmail());
        target.setNaturalidade(source.getNaturalidade());
        target.setContatoEmergencia(source.getContatoEmergencia());
        target.setListaAlergias(source.getListaAlergias());
        target.setListaCuidados(source.getListaCuidados());
        target.setConvenio(source.getConvenio());
        target.setNumeroConvenio(source.getNumeroConvenio());
        target.setValidadeConvenio(source.getValidadeConvenio());
        target.getEndereco().setCep(source.getEndereco().getCep());
        target.getEndereco().setCidade(source.getEndereco().getCidade());
        target.getEndereco().setEstado(source.getEndereco().getEstado());
        target.getEndereco().setRua(source.getEndereco().getRua());
        target.getEndereco().setNumero(source.getEndereco().getNumero());
        target.getEndereco().setComplemento(source.getEndereco().getComplemento());
        target.getEndereco().setBairro(source.getEndereco().getBairro());
        target.getEndereco().setPtoReferencia(source.getEndereco().getPtoReferencia());

        return target;
    }


}
