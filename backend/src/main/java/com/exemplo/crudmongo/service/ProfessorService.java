package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.exemplo.crudmongo.Model.Professor;
import com.exemplo.crudmongo.repository.ProfessorRepository;

@Service
public class ProfessorService {
    private final ProfessorRepository repository; 

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public List<Professor> listarTodas() {
        return repository.findAll();
    }

    public Professor salvar(Professor professor) {
        return repository.save(professor);
    }

    public Professor atualizar(@PathVariable Long id,  Professor novoProfessor) {
        return repository.findById(id).map(p -> {
            p.setNome(novoProfessor.getNome());
            p.setArea(novoProfessor.getArea());
            p.setAtivo(novoProfessor.isAtivo());
            return repository.save(p);
        }).orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}