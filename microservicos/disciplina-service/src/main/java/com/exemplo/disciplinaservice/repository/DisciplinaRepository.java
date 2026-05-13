package com.exemplo.disciplinaservice.repository;

import com.exemplo.disciplinaservice.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByCursoId(Long cursoId);
    List<Disciplina> findByAtivo(boolean ativo);
}