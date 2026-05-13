package com.exemplo.cursoservice.config;

import com.exemplo.cursoservice.model.Curso;
import com.exemplo.cursoservice.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final CursoRepository repository;

    public DataLoader(CursoRepository repository) { this.repository = repository; }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            repository.save(new Curso("Engenharia de Software", "Curso de ES", 3200, true));
            repository.save(new Curso("Sistemas de Informação", "Curso de SI", 3000, true));
            repository.save(new Curso("Ciência da Computação", "Curso de CC", 3400, true));
        }
    }
}