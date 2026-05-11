package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.repository.UsuarioRepository;
import com.exemplo.crudmongo.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioDataLoader {

    @Bean
    CommandLineRunner seedUsuarios(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        return args -> {
            if (usuarioRepository.findByUsername("professor").isEmpty()) {
                usuarioService.salvarUsuario("professor", "prof123", "PROFESSOR");
                System.out.println("? Usuário 'professor' criado (role: PROFESSOR)");
            }
            if (usuarioRepository.findByUsername("usuario").isEmpty()) {
                usuarioService.salvarUsuario("usuario", "user123", "USER");
                System.out.println("? Usuário 'usuario' criado (role: USER)");
            }
        };
    }
}