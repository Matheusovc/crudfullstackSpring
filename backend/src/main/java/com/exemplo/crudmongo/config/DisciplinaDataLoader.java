package com.exemplo.crudmongo.config;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exemplo.crudmongo.Model.Disciplina;
import com.exemplo.crudmongo.repository.DisciplinaRepository;
import com.github.javafaker.Faker;

@Configuration
public class DisciplinaDataLoader{
    @Bean
    CommandLineRunner loadDisciplinaDatabase(DisciplinaRepository repository)
    {
        return args -> {
            if (repository.count() == 0)
            {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i < 20; i++)
                {
                    Disciplina disciplina = new Disciplina();
                    disciplina.setNome(faker.educator().course());
                    disciplina.setCargaHoraria(faker.number().numberBetween(15, 80));
                    disciplina.setProfessorId(faker.number().numberBetween(1L, 20L));
                    disciplina.setAtivo(true);
                    repository.save(disciplina);
                }

                System.out.println("Banco de disciplinas populado com 20 registros!");
            } 
            else 
            {
                System.out.println("Banco de disciplinas ja contem dados, nao foi necessario repopular.");
            }
        };
    }
}
