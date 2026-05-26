# 📋 Task 14 — Implementar Relacionamentos JPA Reais com @ManyToOne

## Contexto

**Problema arquitetural detectado na engenharia reversa:**

Os relacionamentos entre entidades são feitos via **IDs simples (Long)**, não via JPA real:

```java
// Model/Matricula.java — relacionamento fraco (apenas IDs)
private Long pessoaId;   // referência manual — sem integridade referencial
private Long cursoId;    // referência manual — sem integridade referencial

// Model/Avaliacao.java — relacionamento fraco (apenas IDs)
private Long pessoaId;
private Long disciplinaId;
```

**Consequências:**
- É possível salvar `pessoaId = 9999` mesmo que a Pessoa não exista
- Ao deletar uma Pessoa, suas Matrículas e Avaliações ficam com IDs órfãos
- Não há `JOIN` automático via JPA — dados relacionados não são retornados juntos

---

## Objetivo

Substituir os campos `Long pessoaId` / `Long cursoId` / `Long disciplinaId` por
relacionamentos JPA reais com `@ManyToOne`.

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/

ATENÇÃO: Esta é uma mudança estrutural significativa. Execute depois das tasks
de validação (08) e exception handler (07), pois impacta vários arquivos.

PASSO 1 — Atualize Model/Matricula.java:
  Remova:
    private Long pessoaId;
    private Long cursoId;

  Adicione:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

  Importe:
    import jakarta.persistence.ManyToOne;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.FetchType;
    import com.exemplo.crudmongo.Model.Pessoa;
    import com.exemplo.crudmongo.Model.Curso;

  Atualize getters e setters para os novos campos.

PASSO 2 — Atualize Model/Avaliacao.java da mesma forma:
  Remova:
    private Long pessoaId;
    private Long disciplinaId;

  Adicione:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

PASSO 3 — Atualize MatriculaService.java:
  Injete PessoaRepository e CursoRepository via construtor.

  No método salvar():
    - Antes de salvar, busque a Pessoa e o Curso pelos IDs recebidos no body
    - Se não encontrar, lance RecursoNaoEncontradoException
    - Defina matricula.setPessoa(pessoa) e matricula.setCurso(curso)

  No método atualizar():
    - Mapeie m.setPessoa(...) e m.setCurso(...) em vez de m.setPessoaId(...)

PASSO 4 — Repita para AvaliacaoService.java com Pessoa e Disciplina.

PASSO 5 — Atualize MatriculaDataLoader e AvaliacaoDataLoader:
  Use findById() para buscar Pessoa/Curso/Disciplina reais e associar via setter.
  Exemplo:
    Pessoa pessoa = pessoaRepository.findById(pessoaId).orElse(null);
    if (pessoa != null) {
        matricula.setPessoa(pessoa);
        ...
    }

PASSO 6 — Execute `mvn compile` e verifique.

DICA: Com @ManyToOne, o JSON de resposta do GET passará a incluir o objeto
completo de Pessoa e Curso dentro da Matrícula, não apenas os IDs.
Para evitar serialização circular, adicione @JsonIgnoreProperties({"hibernateLazyInitializer"})
nas entidades referenciadas ou use DTOs.
```

---

## Arquivos a modificar

- [`Model/Matricula.java`](../src/main/java/com/exemplo/crudmongo/Model/Matricula.java)
- [`Model/Avaliacao.java`](../src/main/java/com/exemplo/crudmongo/Model/Avaliacao.java)
- [`service/MatriculaService.java`](../src/main/java/com/exemplo/crudmongo/service/MatriculaService.java)
- [`service/AvaliacaoService.java`](../src/main/java/com/exemplo/crudmongo/service/AvaliacaoService.java)
- [`config/MatriculaDataLoader.java`](../src/main/java/com/exemplo/crudmongo/config/MatriculaDataLoader.java)
- [`config/AvaliacaoDataLoader.java`](../src/main/java/com/exemplo/crudmongo/config/AvaliacaoDataLoader.java)

## Dependências

> Esta task é mais avançada. Recomenda-se executar após:
> - Task 07 (Exception Handler)
> - Task 08 (Validação)
> - Task 10 (DataLoader Fix)

## Checklist

- [ ] `@ManyToOne` + `@JoinColumn` em `Matricula` (Pessoa e Curso)
- [ ] `@ManyToOne` + `@JoinColumn` em `Avaliacao` (Pessoa e Disciplina)
- [ ] Services resolvendo entidades pelo ID antes de salvar
- [ ] DataLoaders atualizados
- [ ] `mvn compile` sem erros
- [ ] Matrícula com `pessoaId` inexistente → HTTP 404
- [ ] GET de Matrícula retorna dados completos de Pessoa e Curso aninhados
