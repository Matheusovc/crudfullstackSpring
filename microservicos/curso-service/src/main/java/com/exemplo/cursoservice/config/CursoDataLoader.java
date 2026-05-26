package com.exemplo.cursoservice.config;

import com.exemplo.cursoservice.model.Curso;
import com.exemplo.cursoservice.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CursoDataLoader {

    @Bean
    CommandLineRunner carregarCursos(CursoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Curso("Sistemas de Informacao", 3200, true));
                repository.save(new Curso("Engenharia de Software", 3600, true));
                repository.save(new Curso("Analise e Desenvolvimento de Sistemas", 2400, true));
                repository.save(new Curso("Redes de Computadores", 2200, false));
            }
        };
    }
}
