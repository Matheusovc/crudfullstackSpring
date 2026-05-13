package com.exemplo.disciplinaservice.config;

import com.exemplo.disciplinaservice.model.Disciplina;
import com.exemplo.disciplinaservice.repository.DisciplinaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final DisciplinaRepository repository;

    public DataLoader(DisciplinaRepository repository) { this.repository = repository; }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            repository.save(new Disciplina("Programação 1", 60, 1L, true));
            repository.save(new Disciplina("Banco de Dados", 80, 1L, true));
            repository.save(new Disciplina("Redes", 60, 2L, true));
        }
    }
}