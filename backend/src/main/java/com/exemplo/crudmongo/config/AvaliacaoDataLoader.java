package com.exemplo.crudmongo.config;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exemplo.crudmongo.Model.Avaliacao;
import com.exemplo.crudmongo.repository.AvaliacaoRepository;
import com.github.javafaker.Faker;

@Configuration
public class AvaliacaoDataLoader {
    @Bean
    CommandLineRunner loadAvaliacaoDatabase(AvaliacaoRepository repository)
    {
        return args -> {
            if (repository.count() == 0)
            {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i < 20; i++)
                {
                    Avaliacao avaliacao = new Avaliacao();
                    avaliacao.setPessoaId(faker.number().numberBetween(1L, 200L));
                    avaliacao.setDisciplinaId(faker.number().numberBetween(1L, 20L));
                    avaliacao.setNota(faker.number().randomDouble(1, 0, 10));
                    avaliacao.setData(String.format(
                            "%d-%02d-%02d",
                            faker.number().numberBetween(2023, 2027),
                            faker.number().numberBetween(1, 13),
                            faker.number().numberBetween(1, 29)
                    ));
                    avaliacao.setAtivo(true);
                    repository.save(avaliacao);
                }
                
                System.out.println("Banco de avaliacoes populado com 20 registros!");
            }
            else
            {
                System.out.println("Banco de avaliacoes ja contem dados, nao foi necessario repopular.");
            }
        };
    }
}
