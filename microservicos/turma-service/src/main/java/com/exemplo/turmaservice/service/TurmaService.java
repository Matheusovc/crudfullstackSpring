package com.exemplo.turmaservice.service;

import com.exemplo.turmaservice.model.Turma;
import com.exemplo.turmaservice.repository.TurmaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    private final TurmaRepository repository;

    public TurmaService(TurmaRepository repository) {
        this.repository = repository;
    }

    public List<Turma> listarTodas() { return repository.findAll(); }
    public Optional<Turma> buscarPorId(Long id) { return repository.findById(id); }
    public List<Turma> listarPorProfessor(Long professorId) { return repository.findByProfessorId(professorId); }
    public List<Turma> listarPorCurso(Long cursoId) { return repository.findByCursoId(cursoId); }
    public List<Turma> listarAtivas() { return repository.findByAtivo(true); }
    public Turma salvar(Turma turma) { return repository.save(turma); }

    public Turma atualizar(Long id, Turma dados) {
        Turma existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada: " + id));
        existente.setNome(dados.getNome());
        existente.setAno(dados.getAno());
        existente.setProfessorId(dados.getProfessorId());
        existente.setCursoId(dados.getCursoId());
        existente.setAtivo(dados.isAtivo());
        return repository.save(existente);
    }

    public void desativar(Long id) {
        Turma existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada: " + id));
        existente.setAtivo(false);
        repository.save(existente);
    }

    public void excluir(Long id) { repository.deleteById(id); }
}
