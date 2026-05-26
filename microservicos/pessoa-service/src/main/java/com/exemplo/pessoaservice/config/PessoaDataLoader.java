package com.exemplo.pessoaservice.config;

import com.exemplo.pessoaservice.model.Pessoa;
import com.exemplo.pessoaservice.repository.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PessoaDataLoader {

    @Bean
    CommandLineRunner carregarPessoas(PessoaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Pessoa("Ana Silva", 20, "ana.silva@faculdade.br", true));
                repository.save(new Pessoa("Bruno Souza", 22, "bruno.souza@faculdade.br", true));
                repository.save(new Pessoa("Carla Mendes", 21, "carla.mendes@faculdade.br", true));
                repository.save(new Pessoa("Diego Costa", 23, "diego.costa@faculdade.br", false));
            }
        };
    }
}
