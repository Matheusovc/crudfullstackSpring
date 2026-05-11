package com.exemplo.crudmongo.config;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exemplo.crudmongo.Model.Matricula;
import com.exemplo.crudmongo.repository.MatriculaRepository;
import com.github.javafaker.Faker;

@Configuration
public class MatriculaDataLoader{
    @Bean
    CommandLineRunner loadMatriculaDatabase(MatriculaRepository repository)
    {
        return args -> {
            if (repository.count() == 0)
            {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i < 20; i++)
                {
                    Matricula matricula = new Matricula();
                    matricula.setPessoaId(faker.number().numberBetween(1L, 200L));
                    matricula.setCursoId(faker.number().numberBetween(1L, 20L));
                    matricula.setDataMatricula(String.format(
                            "%d-%02d-%02d",
                            faker.number().numberBetween(2023, 2027),
                            faker.number().numberBetween(1, 13),
                            faker.number().numberBetween(1, 29)
                    ));
                    matricula.setAtivo(true);
                    repository.save(matricula);
                }

               System.out.println("Banco de matriculas populado com 20 registros!");
            } else {
                System.out.println("Banco de matriculas ja contem dados, nao foi necessario repopular.");
            }
        };
    }
}
