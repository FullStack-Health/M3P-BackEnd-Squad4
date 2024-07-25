package br.senai.lab365.LABMedical.repositories;

import br.senai.lab365.LABMedical.entities.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long>{
}
