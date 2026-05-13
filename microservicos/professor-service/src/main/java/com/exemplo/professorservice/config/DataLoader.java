package com.exemplo.professorservice.config;

import com.exemplo.professorservice.model.Professor;
import com.exemplo.professorservice.repository.ProfessorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final ProfessorRepository repository;

    public DataLoader(ProfessorRepository repository) { this.repository = repository; }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            repository.save(new Professor("Dr. João", "joao@example.com", "Banco de Dados", true));
            repository.save(new Professor("Dra. Silvia", "silvia@example.com", "Engenharia de Software", true));
            repository.save(new Professor("Prof. Marcos", "marcos@example.com", "Redes", true));
        }
    }
}