package com.exemplo.matriculaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * =====================================================
 * MICROSERVIÇO: matricula-service
 * =====================================================
 *
 * Este é um serviço independente responsável APENAS
 * pelo gerenciamento de matrículas.
 *
 * Para executar:
 *   mvn spring-boot:run
 *
 * Acesso:
 *   API:        http://localhost:8081/api/matriculas
 *   H2 Console: http://localhost:8081/h2-console
 *
 * =====================================================
 * DIFERENÇAS em relação ao monolito:
 * =====================================================
 *
 * MONOLITO                        | MICROSERVIÇO
 * --------------------------------|----------------------------
 * Uma aplicação, todas entidades  | Uma aplicação por entidade
 * Um banco compartilhado          | Banco exclusivo (matriculadb)
 * Porta 8080                      | Porta 8081
 * Comunica internamente (chamada  | Comunica via HTTP REST
 *   de método Java)               |   com outros serviços
 *
 * =====================================================
 */
@SpringBootApplication
public class MatriculaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatriculaServiceApplication.class, args);
    }
}
