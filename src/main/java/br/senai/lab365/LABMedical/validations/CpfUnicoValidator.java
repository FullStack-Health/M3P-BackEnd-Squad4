package br.senai.lab365.LABMedical.validations;

import br.senai.lab365.LABMedical.dtos.paciente.PacienteRequest;
import br.senai.lab365.LABMedical.repositories.PacienteRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CpfUnicoValidator implements ConstraintValidator<CpfUnico, PacienteRequest> {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public boolean isValid(PacienteRequest request, ConstraintValidatorContext context) {
        String cpf = request.getCpf();
        Long id = request.getId(); // Supondo que PacienteRequest tenha um ID

        if (cpf == null || cpf.trim().isEmpty()) {
            return true; // Deixar outras anotações lidarem com CPF nulo ou vazio
        }

        // Verificar se o CPF já existe e não pertence ao próprio paciente sendo atualizado
        if (pacienteRepository.existsByCpfAndIdNot(cpf, id)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("CPF já cadastrado")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}

