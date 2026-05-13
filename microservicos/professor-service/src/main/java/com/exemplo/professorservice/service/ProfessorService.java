package com.exemplo.professorservice.service;

import com.exemplo.professorservice.model.Professor;
import com.exemplo.professorservice.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) { this.repository = repository; }

    public List<Professor> listarTodas() { return repository.findAll(); }
    public Optional<Professor> buscarPorId(Long id) { return repository.findById(id); }

    public Optional<Professor> buscarPorEmail(String email) { return repository.findByEmail(email); }
    public List<Professor> listarPorEspecialidade(String especialidade) { return repository.findByEspecialidade(especialidade); }

    public Professor salvar(Professor entidade) { return repository.save(entidade); }

    public Professor atualizar(Long id, Professor dados) {
        Professor existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Professor não encontrada: " + id));
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        existente.setEspecialidade(dados.getEspecialidade());
        existente.setAtivo(dados.isAtivo());
        return repository.save(existente);
    }

    public void desativar(Long id) {
        Professor existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Professor não encontrada: " + id));
        existente.setAtivo(false);
        repository.save(existente);
    }

    public void excluir(Long id) { repository.deleteById(id); }
}