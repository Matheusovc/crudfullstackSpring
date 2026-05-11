package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Professor;
import com.exemplo.crudmongo.repository.ProfessorRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class ProfessorDataLoader {
    @Bean
    CommandLineRunner loadProfessorDatabase(ProfessorRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));

                for (int i = 0; i < 20; i++) {
                    Professor professor = new Professor();
                    professor.setNome(faker.name().fullName());
                    professor.setArea(faker.educator().course());
                    professor.setAtivo(true);
                    repository.save(professor);
                }

                System.out.println("Banco de professores populado com 20 registros!");
            }
        };
    }
}
