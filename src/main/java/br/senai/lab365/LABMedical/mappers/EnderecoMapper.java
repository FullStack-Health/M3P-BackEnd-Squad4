package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.paciente.EnderecoRequest;
import br.senai.lab365.LABMedical.dtos.paciente.EnderecoResponse;
import br.senai.lab365.LABMedical.entities.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    public Endereco toEntity(EnderecoRequest request) {
        if (request == null) {
            return null;
        }
        return new Endereco(
                null,
                request.getCep(),
                request.getCidade(),
                request.getEstado(),
                request.getRua(),
                request.getNumero(),
                request.getComplemento(),
                request.getBairro(),
                request.getPtoReferencia()
        );
    }

    public EnderecoResponse toResponse(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return new EnderecoResponse(
                endereco.getId(),
                endereco.getCep(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getPtoReferencia()
        );
    }
}
