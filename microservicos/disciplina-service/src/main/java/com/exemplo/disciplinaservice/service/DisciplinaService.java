package com.exemplo.disciplinaservice.service;

import com.exemplo.disciplinaservice.model.Disciplina;
import com.exemplo.disciplinaservice.repository.DisciplinaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {
    private final DisciplinaRepository repository;

    public DisciplinaService(DisciplinaRepository repository) { this.repository = repository; }

    public List<Disciplina> listarTodas() { return repository.findAll(); }
    public Optional<Disciplina> buscarPorId(Long id) { return repository.findById(id); }

    public List<Disciplina> listarPorCurso(Long cursoId) { return repository.findByCursoId(cursoId); }

    public Disciplina salvar(Disciplina entidade) { return repository.save(entidade); }

    public Disciplina atualizar(Long id, Disciplina dados) {
        Disciplina existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Disciplina não encontrada: " + id));
        existente.setNome(dados.getNome());
        existente.setCargaHoraria(dados.getCargaHoraria());
        existente.setCursoId(dados.getCursoId());
        existente.setAtivo(dados.isAtivo());
        return repository.save(existente);
    }

    public void desativar(Long id) {
        Disciplina existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Disciplina não encontrada: " + id));
        existente.setAtivo(false);
        repository.save(existente);
    }

    public void excluir(Long id) { repository.deleteById(id); }
}