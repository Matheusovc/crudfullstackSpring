package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.exemplo.crudmongo.Model.Turma;
import com.exemplo.crudmongo.repository.TurmaRepository;

@Service
public class TurmaService {
    private final TurmaRepository repository; 

    public TurmaService(TurmaRepository repository) {
        this.repository = repository;
    }

    public List<Turma> listarTodas() {
        return repository.findAll();
    }

    public Turma salvar(Turma turma) {
        return repository.save(turma);
    }

    public Turma atualizar(@PathVariable Long id,  Turma novaTurma) {
        return repository.findById(id).map(t -> {
            t.setNome(novaTurma.getNome());
            t.setAno(novaTurma.getAno());
            t.setAtivo(novaTurma.isAtivo());
            return repository.save(t);
        }).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}