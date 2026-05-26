# 🏗️ Task 03 — Garantir Ordem de Execução dos DataLoaders

## Contexto

**Problema detectado na engenharia reversa:**

Os DataLoaders são executados via `CommandLineRunner` beans sem `@Order` explícito.
A ordem atual depende do contexto Spring (não determinística entre versões).

**Dependências críticas detectadas:**
- `MatriculaDataLoader` depende de `PessoaRepository.count() > 0` e `CursoRepository.count() > 0`
- `AvaliacaoDataLoader` depende de `PessoaRepository.count() > 0` e `DisciplinaRepository.count() > 0`

Se esses DataLoaders rodarem antes das Pessoas/Cursos/Disciplinas serem criadas,
o `⚠️ Pulando DataLoader` será exibido e nenhum dado será inserido.

**Ordem necessária:**
```
1. UsuarioDataLoader
2. DataLoader (Cursos)
3. PessoaDataLoader
4. ProfessorDataLoader
5. DisciplinaDataLoader
6. TurmaDataLoader
7. MatriculaDataLoader  ← depende de Pessoa e Curso
8. AvaliacaoDataLoader  ← depende de Pessoa e Disciplina
```

---

## Objetivo

Adicionar `@Order(N)` em cada classe DataLoader para garantir a ordem correta de execução.

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/config/

Adicione a anotação @Order(N) em cada DataLoader, importando:
  import org.springframework.core.annotation.Order;

Aplique a seguinte ordem:

@Order(1)  → UsuarioDataLoader
@Order(2)  → DataLoader            (Cursos)
@Order(3)  → PessoaDataLoader
@Order(4)  → ProfessorDataLoader
@Order(5)  → DisciplinaDataLoader
@Order(6)  → TurmaDataLoader
@Order(7)  → MatriculaDataLoader
@Order(8)  → AvaliacaoDataLoader

Exemplo de uso:
  @Configuration
  @Order(7)
  public class MatriculaDataLoader { ... }

Após a mudança:
1. Execute `mvn compile` para verificar que não há erros
2. Execute a aplicação e verifique nos logs que todos os DataLoaders exibem ✅
3. Confirme que MatriculaDataLoader e AvaliacaoDataLoader NÃO exibem o ⚠️ de "Pulando"
```

---

## Arquivos a modificar

- `config/UsuarioDataLoader.java`
- `config/DataLoader.java`
- `config/PessoaDataLoader.java`
- `config/ProfessorDataLoader.java`
- `config/DisciplinaDataLoader.java`
- `config/TurmaDataLoader.java`
- `config/MatriculaDataLoader.java`
- `config/AvaliacaoDataLoader.java`

## Checklist

- [ ] `@Order` adicionado em todos os 8 DataLoaders
- [ ] `import org.springframework.core.annotation.Order` presente em cada arquivo
- [ ] `mvn compile` sem erros
- [ ] Aplicação iniciada → todos os DataLoaders exibem mensagem ✅ nos logs
- [ ] Nenhum DataLoader exibe a mensagem ⚠️ "Pulando" por falta de dados
