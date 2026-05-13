package com.exemplo.professorservice.repository;

import com.exemplo.professorservice.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByEmail(String email);
    List<Professor> findByEspecialidade(String especialidade);
    List<Professor> findByAtivo(boolean ativo);
}