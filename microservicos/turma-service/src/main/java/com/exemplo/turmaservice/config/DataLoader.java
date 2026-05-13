package com.exemplo.turmaservice.config;

import com.exemplo.turmaservice.model.Turma;
import com.exemplo.turmaservice.repository.TurmaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final TurmaRepository repository;

    public DataLoader(TurmaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            repository.save(new Turma("Turma A - 2025", 2025, 1L, 1L, true));
            repository.save(new Turma("Turma B - 2025", 2025, 2L, 1L, true));
            repository.save(new Turma("Turma C - 2024", 2024, 3L, 2L, false));
            repository.save(new Turma("Turma D - 2025", 2025, 1L, 3L, true));
            repository.save(new Turma("Turma E - 2026", 2026, 4L, 2L, true));
        }
    }
}
