package com.exemplo.crudmongo.config;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exemplo.crudmongo.Model.Turma;
import com.exemplo.crudmongo.repository.TurmaRepository;
import com.github.javafaker.Faker;

@Configuration
public class TurmaDataLoader{
    @Bean
    CommandLineRunner loadTurmaDatabase(TurmaRepository repository)
    {
        return args -> {
            if (repository.count() == 0)
            {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i < 20; i++)
                {
                    Turma turma = new Turma();
                    turma.setNome(faker.educator().course() + " - " + faker.options().option("M", "V", "N"));
                    turma.setAno(faker.number().numberBetween(2024, 2027));
                    turma.setAtivo(true);
                    repository.save(turma);
                }

               System.out.println("Banco de turmas populado com 20 registros!");
            } else {
                System.out.println("Banco de turmas ja contem dados, nao foi necessario repopular.");
            }
        };
    }
}
