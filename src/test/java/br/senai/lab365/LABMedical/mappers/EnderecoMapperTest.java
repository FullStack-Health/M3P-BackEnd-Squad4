package br.senai.lab365.LABMedical.mappers;

import br.senai.lab365.LABMedical.dtos.paciente.EnderecoRequest;
import br.senai.lab365.LABMedical.dtos.paciente.EnderecoResponse;
import br.senai.lab365.LABMedical.entities.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoMapperTest {

    private EnderecoMapper enderecoMapper;

    @BeforeEach
    void setUp() {
        enderecoMapper = new EnderecoMapper();
    }

    @Test
    void toEntity_DeveRetornarEnderecoQuandoRequestValido() {
        // Arrange
        EnderecoRequest request = new EnderecoRequest(
                "12345-678",
                "São Paulo",
                "SP",
                "Rua Exemplo",
                "123",
                "Apto 45",
                "Bairro Exemplo",
                "Ponto de Referência Exemplo"
        );

        // Act
        Endereco endereco = enderecoMapper.toEntity(request);

        // Assert
        assertNotNull(endereco);
        assertEquals(request.getCep(), endereco.getCep());
        assertEquals(request.getCidade(), endereco.getCidade());
        assertEquals(request.getEstado(), endereco.getEstado());
        assertEquals(request.getRua(), endereco.getRua());
        assertEquals(request.getNumero(), endereco.getNumero());
        assertEquals(request.getComplemento(), endereco.getComplemento());
        assertEquals(request.getBairro(), endereco.getBairro());
        assertEquals(request.getPtoReferencia(), endereco.getPtoReferencia());
    }

    @Test
    void toEntity_DeveRetornarNuloQuandoRequestNulo() {
        // Act & Assert
        assertNull(enderecoMapper.toEntity(null));
    }

    @Test
    void toResponse_DeveRetornarEnderecoResponseQuandoEnderecoValido() {
        // Arrange
        Endereco endereco = new Endereco(
                1L,
                "12345-678",
                "São Paulo",
                "SP",
                "Rua Exemplo",
                "123",
                "Apto 45",
                "Bairro Exemplo",
                "Ponto de Referência Exemplo"
        );

        // Act
        EnderecoResponse response = enderecoMapper.toResponse(endereco);

        // Assert
        assertNotNull(response);
        assertEquals(endereco.getId(), response.getId());
        assertEquals(endereco.getCep(), response.getCep());
        assertEquals(endereco.getCidade(), response.getCidade());
        assertEquals(endereco.getEstado(), response.getEstado());
        assertEquals(endereco.getRua(), response.getRua());
        assertEquals(endereco.getNumero(), response.getNumero());
        assertEquals(endereco.getComplemento(), response.getComplemento());
        assertEquals(endereco.getBairro(), response.getBairro());
        assertEquals(endereco.getPtoReferencia(), response.getPtoReferencia());
    }

    @Test
    void toResponse_DeveRetornarNuloQuandoEnderecoNulo() {
        // Act & Assert
        assertNull(enderecoMapper.toResponse(null));
    }
}


