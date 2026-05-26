# ⚙️ Task 09 — Implementar Soft Delete com o Campo `ativo`

## Contexto

**Comportamento inconsistente detectado na engenharia reversa:**

Todas as entidades possuem o campo `boolean ativo`, mas:

| O que existe | O que deveria existir |
|---|---|
| Campo `ativo` apenas armazenado | `ativo` filtrando listagens |
| `excluir()` faz hard delete (`deleteById`) | `excluir()` deveria setar `ativo = false` |
| `GET /api/professor` retorna todos (inclusive `ativo: false`) | Deveria retornar apenas `ativo: true` |

**O campo `ativo` existe mas não tem efeito lógico algum.**

---

## Objetivo

Implementar soft delete real: ao excluir, setar `ativo = false` em vez de remover o registro.
Filtrar automaticamente os registros inativos nas listagens.

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/

Implemente soft delete em TODAS as entidades seguindo estes passos:

PASSO 1 — Adicione método de busca filtrando por `ativo` em cada Repository:

  Em cada Repository (Pessoa, Curso, Professor, Disciplina, Turma, Matricula, Avaliacao):
    List<Entidade> findByAtivoTrue();

  Exemplo para ProfessorRepository.java:
    import java.util.List;
    List<Professor> findByAtivoTrue();

PASSO 2 — Atualize os Services para usar o novo método nas listagens:

  Em cada Service, mude o método listarTodas/listarTodos:
    return repository.findByAtivoTrue();   // antes: repository.findAll()

PASSO 3 — Substitua o hard delete por soft delete em cada Service:

  Em cada Service, mude o método excluir():

  Antes (hard delete):
    public void excluir(Long id) {
        repository.deleteById(id);
    }

  Depois (soft delete):
    public void excluir(Long id) {
        repository.findById(id).map(entidade -> {
            entidade.setAtivo(false);
            return repository.save(entidade);
        }).orElseThrow(() -> new RecursoNaoEncontradoException("Recurso não encontrado"));
    }

  Aplique em: PessoaService, CursoService, ProfessorService, DisciplinaService,
              TurmaService, MatriculaService, AvaliacaoService

PASSO 4 — Garanta que ao criar (salvar) uma entidade, `ativo` seja true por padrão:

  Em cada Model, inicialize o campo:
    private boolean ativo = true;

  Isso evita que novos registros sejam criados com `ativo = false`.

PASSO 5 — (Opcional) Adicione endpoint para reativar um registro:

  Em cada Controller, adicione:
    @PatchMapping("/{id}/reativar")
    @PreAuthorize("hasRole('PROFESSOR')")
    public Entidade reativar(@PathVariable Long id) {
        return service.reativar(id);
    }

  Em cada Service:
    public Entidade reativar(Long id) {
        return repository.findById(id).map(e -> {
            e.setAtivo(true);
            return repository.save(e);
        }).orElseThrow(() -> new RecursoNaoEncontradoException("Recurso não encontrado"));
    }

PASSO 6 — Execute `mvn compile` e teste o soft delete.
```

---

## Arquivos a modificar

### Repositories (adicionar `findByAtivoTrue`)
- `repository/PessoaRepository.java`
- `repository/CursoRepository.java`
- `repository/ProfessorRepository.java`
- `repository/DisciplinaRepository.java`
- `repository/TurmaRepository.java`
- `repository/MatriculaRepository.java`
- `repository/AvaliacaoRepository.java`

### Services (listarTodas + excluir + reativar)
- Todos os Services acima correspondentes

### Controllers (endpoint /reativar — opcional)
- Todos os Controllers correspondentes

## Checklist

- [ ] `findByAtivoTrue()` adicionado em todos os Repositories
- [ ] `listarTodas()` usando `findByAtivoTrue()`
- [ ] `excluir()` fazendo soft delete (setAtivo(false))
- [ ] `private boolean ativo = true` nos Models
- [ ] `mvn compile` sem erros
- [ ] `DELETE /api/professor/1` → registro permanece no banco com `ativo = false`
- [ ] `GET /api/professor` → não retorna o registro deletado
- [ ] `PATCH /api/professor/1/reativar` → registro volta a aparecer (opcional)
