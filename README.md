# crudfullstackSpring — Projeto de Microserviços

## 📋 Visão Geral
Projeto educacional demonstrando arquitetura de microserviços com Spring Boot.

## 🏗️ Arquitetura
```text
[ cliente / frontend :4200 ]
│
▼
[ gateway-service :8080 ]  ← ponto de entrada único
│    │    │    │    │
▼    ▼    ▼    ▼    ▼
:8081 :8082 :8083 :8084 :8085 :8086
matr  pess  curs  disc  prof  turma
```

## 🚀 Como executar

### Com Docker (todos os serviços de uma vez):
```bash
docker-compose up --build
```

### Individualmente (desenvolvimento):

```bash
cd microservicos/matricula-service && mvn spring-boot:run
cd microservicos/pessoa-service    && mvn spring-boot:run
cd microservicos/curso-service     && mvn spring-boot:run
cd microservicos/disciplina-service && mvn spring-boot:run
cd microservicos/professor-service && mvn spring-boot:run
cd microservicos/turma-service     && mvn spring-boot:run
cd microservicos/gateway-service   && mvn spring-boot:run
```

## 📡 Serviços e Endpoints

| Serviço | Porta | Base URL | H2 Console |
| --- | --- | --- | --- |
| gateway-service | 8080 | /gateway/{entidade}s | — |
| matricula-service | 8081 | /api/matriculas | /h2-console |
| pessoa-service | 8082 | /api/pessoas | /h2-console |
| curso-service | 8083 | /api/cursos | /h2-console |
| disciplina-service | 8084 | /api/disciplinas | /h2-console |
| professor-service | 8085 | /api/professores | /h2-console |
| turma-service | 8086 | /api/turmas | /h2-console |

## 🔄 Alterações realizadas (histórico da atividade)

### Nível 1 — Novos microserviços criados

- **`pessoa-service`** (porta 8082): CRUD completo de Pessoa (nome, email, cpf, dataNascimento, ativo). Consultas por email e CPF.
- **`curso-service`** (porta 8083): CRUD completo de Curso (nome, descrição, cargaHoraria, ativo).
- **`disciplina-service`** (porta 8084): CRUD completo de Disciplina (nome, cargaHoraria, cursoId, ativo). Consulta por cursoId.
- **`professor-service`** (porta 8085): CRUD completo de Professor (nome, email, especialidade, ativo). Consulta por especialidade.

### Nível 2 — Comunicação entre serviços

- **`matricula-service`** ganhou o endpoint `GET /api/matriculas/{id}/detalhada` que retorna `MatriculaDetalhadaDTO` enriquecido com `nomePessoa` (buscado do pessoa-service :8082) e `nomeCurso` (buscado do curso-service :8083) via `RestTemplate`.
- Adicionado `RestTemplateConfig.java` para configurar o bean `RestTemplate`.

### Nível 3 — Tratamento de erros

- **`GlobalExceptionHandler.java`** adicionado em todos os microserviços: captura `RuntimeException` e `Exception` e retorna JSON padronizado com `status`, `mensagem` e `timestamp` em vez da Whitelabel Error Page padrão do Spring.
- **Fallback de resiliência:** se `pessoa-service` ou `curso-service` estiver fora do ar, o endpoint `/detalhada` retorna `"indisponível"` no campo correspondente em vez de propagar o erro.

### Nível 4 — Docker

- `Dockerfile` criado para cada microserviço usando build multi-stage (Maven + JRE slim).
- `docker-compose.yml` atualizado para incluir todos os 6 microserviços além do backend e frontend já existentes. Todos na rede `app-network`. Comando: `docker-compose up --build`.

### Nível 5 — API Gateway

- **`gateway-service`** (porta 8080) criado como ponto de entrada único. Roteia requisições GET/POST/PUT/DELETE para todos os microserviços via `RestTemplate`. Implementado sem dependências externas (apenas `spring-boot-starter-web`).