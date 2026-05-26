package com.exemplo.authservice.config;

import com.exemplo.authservice.model.Usuario;
import com.exemplo.authservice.repository.UsuarioRepository;
import com.exemplo.authservice.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cria usuarios padrao na inicializacao do auth-service.
 *
 *  username: professor | senha: prof123   | role: PROFESSOR
 *  username: aluno     | senha: aluno123  | role: ALUNO
 */
@Configuration
public class UsuarioDataLoader {

    @Bean
    CommandLineRunner seedUsuarios(UsuarioRepository repository, UsuarioService service) {
        return args -> {
            if (repository.findByUsername("professor").isEmpty()) {
                service.registrar("professor", "prof123", "PROFESSOR");
                System.out.println("[auth-service] Usuario 'professor' criado (role: PROFESSOR)");
            }
            if (repository.findByUsername("aluno").isEmpty()) {
                service.registrar("aluno", "aluno123", "ALUNO");
                System.out.println("[auth-service] Usuario 'aluno' criado (role: ALUNO)");
            }
            if (repository.findByUsername("coordenador").isEmpty()) {
                service.registrar("coordenador", "coord123", "PROFESSOR");
                System.out.println("[auth-service] Usuario 'coordenador' criado (role: PROFESSOR)");
            }
        };
    }
}
