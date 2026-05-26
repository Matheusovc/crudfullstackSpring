package com.exemplo.professorservice.config;

import com.exemplo.professorservice.model.Professor;
import com.exemplo.professorservice.repository.ProfessorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfessorDataLoader {

    @Bean
    CommandLineRunner carregarProfessores(ProfessorRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Professor("Mariana Lopes", 38, "mariana.lopes@faculdade.br", "Java", true));
                repository.save(new Professor("Rafael Alves", 41, "rafael.alves@faculdade.br", "Banco de Dados", true));
                repository.save(new Professor("Patricia Gomes", 36, "patricia.gomes@faculdade.br", "Redes", true));
                repository.save(new Professor("Carlos Lima", 45, "carlos.lima@faculdade.br", "Gestao", false));
            }
        };
    }
}
