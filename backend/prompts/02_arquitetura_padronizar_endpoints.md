# 🏗️ Task 02 — Padronizar Nomenclatura de Endpoints

## Contexto

**Inconsistência detectada na engenharia reversa:**

| Entidade | Endpoint atual | Padrão esperado |
|---|---|---|
| Pessoa | `/api/pessoas` ← PLURAL | `/api/pessoa` |
| Curso | `/api/curso` ← singular | ✅ correto |
| Professor | `/api/professor` ← singular | ✅ correto |
| Disciplina | `/api/disciplina` ← singular | ✅ correto |
| Turma | `/api/turma` ← singular | ✅ correto |
| Matrícula | `/api/matricula` ← singular | ✅ correto |
| Avaliação | `/api/avaliacao` ← singular | ✅ correto |

A entidade `Pessoa` usa `/api/pessoas` (plural) enquanto todas as demais usam singular.
Isso quebra a uniformidade da API.

---

## Objetivo

Corrigir o endpoint de `Pessoa` de `/api/pessoas` para `/api/pessoa`, mantendo consistência
com todos os outros endpoints do sistema.

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/controller/PessoaController.java

Altere a anotação:
  @RequestMapping("/api/pessoas")
para:
  @RequestMapping("/api/pessoa")

Após a mudança:
1. Execute `mvn compile` para verificar que não há erros
2. Confirme que os outros controllers mantêm seus endpoints no singular:
   - /api/curso
   - /api/professor
   - /api/disciplina
   - /api/turma
   - /api/matricula
   - /api/avaliacao

IMPORTANTE: Se houver algum cliente (frontend) que consuma /api/pessoas,
documente a quebra de contrato neste arquivo antes de aplicar a mudança.
```

---

## Arquivo a modificar

- [`PessoaController.java`](../src/main/java/com/exemplo/crudmongo/controller/PessoaController.java) — linha com `@RequestMapping`

## Impacto

- ⚠️ **Breaking change** na API: qualquer cliente que use `/api/pessoas` precisará ser atualizado.
- Sem impacto no banco de dados ou na lógica de negócio.

## Checklist

- [ ] `@RequestMapping("/api/pessoas")` alterado para `@RequestMapping("/api/pessoa")`
- [ ] `mvn compile` sem erros
- [ ] Testado `GET /api/pessoa` com token JWT → HTTP 200
- [ ] Testado `GET /api/pessoas` → deve retornar HTTP 404 (rota removida)
