package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.exemplo.crudmongo.Model.Avaliacao;
import com.exemplo.crudmongo.repository.AvaliacaoRepository;

@Service
public class AvaliacaoService {
    private final AvaliacaoRepository repository;
    
    public AvaliacaoService(AvaliacaoRepository repository) {
        this.repository = repository;
    }

    public List<Avaliacao> listarTodas() {
        return repository.findAll();
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        return repository.save(avaliacao);
    }

    public Avaliacao atualizar(@PathVariable Long id,  Avaliacao novaAvaliacao) {
        return repository.findById(id).map(a -> {
            a.setPessoaId(novaAvaliacao.getPessoaId());
            a.setDisciplinaId(novaAvaliacao.getDisciplinaId());
            a.setNota(novaAvaliacao.getNota());
            a.setData(novaAvaliacao.getData());
            a.setAtivo(novaAvaliacao.isAtivo());
            return repository.save(a);
        }).orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}