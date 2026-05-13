package com.exemplo.pessoaservice.config;

import com.exemplo.pessoaservice.model.Pessoa;
import com.exemplo.pessoaservice.repository.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final PessoaRepository repository;

    public DataLoader(PessoaRepository repository) { this.repository = repository; }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            repository.save(new Pessoa("Ana Silva", "ana@example.com", "111.111.111-11", "1990-01-01", true));
            repository.save(new Pessoa("Carlos Souza", "carlos@example.com", "222.222.222-22", "1995-05-05", true));
            repository.save(new Pessoa("Maria Oliveira", "maria@example.com", "333.333.333-33", "2000-10-10", true));
        }
    }
}