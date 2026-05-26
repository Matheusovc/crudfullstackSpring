package com.exemplo.alunoservice.config;

import com.exemplo.alunoservice.model.Aluno;
import com.exemplo.alunoservice.repository.AlunoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlunoDataLoader {

    @Bean
    CommandLineRunner carregarAlunos(AlunoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Aluno("Ana Lima", 20, "ana.lima@faculdade.br", "2024001", true));
                repository.save(new Aluno("Bruno Souza", 22, "bruno.souza@faculdade.br", "2024002", true));
                repository.save(new Aluno("Carla Mendes", 21, "carla.mendes@faculdade.br", "2024003", true));
                repository.save(new Aluno("Diego Costa", 23, "diego.costa@faculdade.br", "2024004", true));
                repository.save(new Aluno("Elena Rocha", 19, "elena.rocha@faculdade.br", "2024005", true));
                repository.save(new Aluno("Felipe Nunes", 25, "felipe.nunes@faculdade.br", "2024006", true));
                repository.save(new Aluno("Gabriela Pinto", 20, "gabriela.pinto@faculdade.br", "2024007", true));
                repository.save(new Aluno("Henrique Alves", 22, "henrique.alves@faculdade.br", "2024008", true));
                System.out.println("[aluno-service] ✅ 8 alunos carregados na porta 8084.");
            } else {
                System.out.println("[aluno-service] ℹ️ Banco de alunos já populado.");
            }
        };
    }
}
