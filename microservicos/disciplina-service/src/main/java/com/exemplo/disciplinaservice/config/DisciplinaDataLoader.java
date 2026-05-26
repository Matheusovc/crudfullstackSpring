package com.exemplo.disciplinaservice.config;

import com.exemplo.disciplinaservice.model.Disciplina;
import com.exemplo.disciplinaservice.repository.DisciplinaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisciplinaDataLoader {

    @Bean
    CommandLineRunner carregarDisciplinas(DisciplinaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Disciplina("Programacao Orientada a Objetos", true));
                repository.save(new Disciplina("Banco de Dados", true));
                repository.save(new Disciplina("Arquitetura de Software", true));
                repository.save(new Disciplina("Redes", false));
            }
        };
    }
}
