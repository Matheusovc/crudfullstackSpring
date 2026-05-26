# 📋 Task 15 — Filtrar Registros Inativos nas Listagens

## Contexto

**Comportamento inconsistente detectado na engenharia reversa:**

O campo `boolean ativo` existe em todas as entidades, mas **os endpoints GET
retornam todos os registros, incluindo os com `ativo = false`**:

```java
// Todos os Services — comportamento atual
public List<Professor> listarTodos() {
    return repository.findAll();  // retorna TUDO — inclusive inativos
}
```

**Situação real:**
```
GET /api/professor → retorna 50 professores, incluindo os com ativo: false
                  → o consumidor da API recebe professores "inativos" misturados
```

> **Nota:** Esta task é a versão simplificada da Task 09 (Soft Delete).
> Se você já executou a Task 09, esta task já está resolvida.
> Execute esta task apenas se quiser filtrar as listagens sem mudar o comportamento do DELETE.

---

## Objetivo

Fazer com que os endpoints `GET /api/<entidade>` retornem apenas registros com `ativo = true`,
mantendo o hard delete existente (sem implementar soft delete completo).

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/

PASSO 1 — Adicione método de busca em cada Repository:

  Em cada Repository (Pessoa, Curso, Professor, Disciplina, Turma, Matricula, Avaliacao),
  adicione o método:
    List<Entidade> findByAtivoTrue();

  Exemplo para DisciplinaRepository.java:
    package com.exemplo.crudmongo.repository;
    import com.exemplo.crudmongo.Model.Disciplina;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import java.util.List;

    @Repository
    public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
        List<Disciplina> findByAtivoTrue();
    }

  Repita para: PessoaRepository, CursoRepository, ProfessorRepository,
               TurmaRepository, MatriculaRepository, AvaliacaoRepository

PASSO 2 — Atualize os Services para usar findByAtivoTrue() nas listagens:

  Em cada Service, mude o método listarTodas/listarTodos:
    Antes: return repository.findAll();
    Depois: return repository.findByAtivoTrue();

  Aplique em: PessoaService, CursoService, ProfessorService, DisciplinaService,
              TurmaService, MatriculaService, AvaliacaoService

PASSO 3 — Garanta que novos registros sejam criados com ativo = true por padrão:

  Em cada Model, inicialize o campo ativo:
    private boolean ativo = true;

  Repita em: Pessoa, Curso, Professor, Disciplina, Turma, Matricula, Avaliacao

PASSO 4 — Execute `mvn compile` e teste.
```

---

## Impacto

| Endpoint | Antes | Depois |
|---|---|---|
| `GET /api/professor` | Retorna todos (ativos + inativos) | Retorna apenas `ativo: true` |
| `POST /api/professor` | Cria com `ativo: false` se omitido | Cria com `ativo: true` por padrão |
| `DELETE /api/professor/1` | Remove do banco | Remove do banco (sem mudança) |

## Checklist

- [ ] `findByAtivoTrue()` adicionado em todos os 7 Repositories
- [ ] Todos os Services usando `findByAtivoTrue()` no método de listagem
- [ ] `private boolean ativo = true` inicializado nos Models
- [ ] `mvn compile` sem erros
- [ ] `GET /api/professor` retorna apenas professores com `ativo: true`
- [ ] Criar professor sem campo `ativo` → criado com `ativo: true` automaticamente
