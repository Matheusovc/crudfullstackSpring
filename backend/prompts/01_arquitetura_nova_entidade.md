# 🏗️ Task 01 — Adicionar Nova Entidade ao Sistema

## Contexto

O projeto segue uma arquitetura em 5 camadas obrigatórias para cada entidade de domínio.
Este prompt serve como guia/template para criar qualquer nova entidade seguindo o padrão existente.

**Padrão detectado no projeto:** `Model → Repository → Service → Controller → DataLoader`

---

## Objetivo

Criar uma nova entidade chamada `<NomeDaEntidade>` com todas as camadas necessárias,
seguindo exatamente o mesmo padrão das entidades `Curso` e `Pessoa` já existentes.

---

## Prompt para o agente

```
Você está trabalhando no projeto Spring Boot localizado em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/

Crie uma nova entidade chamada `<NomeDaEntidade>` seguindo EXATAMENTE o padrão abaixo:

### 1. Model (pacote: Model/)
- Anotação @Entity + @Table(name = "<nome_tabela>")
- Campo @Id + @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id
- Atributos coerentes com o domínio acadêmico
- Campo boolean ativo em todas as entidades
- Getters e setters explícitos (sem Lombok)
- Construtor vazio obrigatório

### 2. Repository (pacote: repository/)
- Interface que extends JpaRepository<<NomeDaEntidade>, Long>
- Anotação @Repository
- Sem métodos adicionais (apenas os herdados do JpaRepository)

### 3. Service (pacote: service/)
- Anotação @Service
- Injeção do Repository via CONSTRUTOR (campo final, sem @Autowired)
- Métodos obrigatórios:
  - public List<<NomeDaEntidade>> listarTodas()
  - public <NomeDaEntidade> salvar(<NomeDaEntidade> entidade)
  - public <NomeDaEntidade> atualizar(Long id, <NomeDaEntidade> novaEntidade)
    → usar findById(id).map(...).orElseThrow(RuntimeException)
    → mapear campo a campo (nunca sobrescrever o ID)
  - public void excluir(Long id)

### 4. Controller (pacote: controller/)
- Anotações: @RestController + @RequestMapping("/api/<nomeentidade>") + @CrossOrigin(origins = "*")
- Injeção do Service via CONSTRUTOR
- Endpoints:
  - GET /api/<nomeentidade>       → @PreAuthorize("hasAnyRole('PROFESSOR', 'ALUNO')")
  - POST /api/<nomeentidade>      → @PreAuthorize("hasRole('PROFESSOR')")
  - PUT /api/<nomeentidade>/{id}  → @PreAuthorize("hasRole('PROFESSOR')")
  - DELETE /api/<nomeentidade>/{id} → @PreAuthorize("hasRole('PROFESSOR')")

### 5. DataLoader (pacote: config/)
- Anotação @Configuration
- Método @Bean CommandLineRunner
- Verificar repository.count() == 0 antes de popular
- Usar JavaFaker com Locale.forLanguageTag("pt-BR")
- Inserir no mínimo 20 registros realistas
- Exibir mensagem ✅ ao final ou ℹ️ se já populado

### Restrições a respeitar:
- NUNCA acessar Repository diretamente no Controller
- NUNCA usar @Autowired em campo (sempre via construtor)
- NUNCA implementar DataLoader com @Component (usar @Bean dentro de @Configuration)
- O nome do endpoint deve ser no singular (ex: /api/turma, não /api/turmas)
```

---

## Checklist de Verificação

- [ ] `<NomeDaEntidade>.java` criado no pacote `Model/`
- [ ] `<NomeDaEntidade>Repository.java` criado no pacote `repository/`
- [ ] `<NomeDaEntidade>Service.java` criado no pacote `service/`
- [ ] `<NomeDaEntidade>Controller.java` criado no pacote `controller/`
- [ ] `<NomeDaEntidade>DataLoader.java` criado no pacote `config/`
- [ ] `mvn compile` executado sem erros
- [ ] Endpoints testados no Postman com token JWT de PROFESSOR e de ALUNO
