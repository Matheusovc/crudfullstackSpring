package com.exemplo.turmaservice.config;

import com.exemplo.turmaservice.model.Turma;
import com.exemplo.turmaservice.repository.TurmaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TurmaDataLoader {

    @Bean
    CommandLineRunner carregarTurmas(TurmaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Turma("1A", 2024, true));
                repository.save(new Turma("2B", 2024, true));
                repository.save(new Turma("3C", 2025, true));
                repository.save(new Turma("Egressos", 2023, false));
            }
        };
    }
}
