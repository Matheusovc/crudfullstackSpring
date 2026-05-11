package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.exemplo.crudmongo.Model.Matricula;
import com.exemplo.crudmongo.repository.MatriculaRepository;

@Service
public class MatriculaService {
    private final MatriculaRepository repository;

    public MatriculaService(MatriculaRepository repository) {
        this.repository = repository;
    }

    public List<Matricula> listarTodas() {
        return repository.findAll();
    }

    public Matricula salvar(Matricula matricula) {
        return repository.save(matricula);
    }
     
    public Matricula atualizar(@PathVariable Long id,  Matricula novaMatricula) {
        return repository.findById(id).map(m -> {
            m.setPessoaId(novaMatricula.getPessoaId());
            m.setCursoId(novaMatricula.getCursoId());
            m.setDataMatricula(novaMatricula.getDataMatricula());
            m.setAtivo(novaMatricula.isAtivo());
            return repository.save(m);
        }).orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}