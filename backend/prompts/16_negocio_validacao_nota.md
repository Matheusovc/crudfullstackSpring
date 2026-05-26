# 📋 Task 16 — Validar Range de Nota em Avaliação (0.0 a 10.0)

## Contexto

**Regra de negócio ausente detectada na engenharia reversa:**

O campo `nota` em `Avaliacao` é um `double` sem validação de range:

```java
// Model/Avaliacao.java
private double nota;  // aceita -999.9, 200.0, qualquer valor double
```

**Dados inválidos aceitos atualmente:**
```json
POST /api/avaliacao → { "nota": -5.0 }    ← aceito (nota negativa)
POST /api/avaliacao → { "nota": 100.0 }   ← aceito (nota acima de 10)
POST /api/avaliacao → { "nota": 0.0 }     ← aceito (zero — válido)
POST /api/avaliacao → { "nota": 10.0 }    ← aceito (dez — válido)
```

**Regra de negócio esperada:** `0.0 ≤ nota ≤ 10.0`

---

## Objetivo

Garantir que o campo `nota` só aceite valores entre 0.0 e 10.0 (inclusive).

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/

PASSO 1 — Adicione validação no Model/Avaliacao.java:
  Importe: import jakarta.validation.constraints.DecimalMax;
           import jakarta.validation.constraints.DecimalMin;

  No campo nota, adicione:
    @DecimalMin(value = "0.0", inclusive = true, message = "Nota mínima é 0.0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Nota máxima é 10.0")
    private double nota;

PASSO 2 — Adicione @Valid no AvaliacaoController (se task 08 não foi feita):
  No arquivo controller/AvaliacaoController.java, adicione @Valid nos métodos
  que recebem @RequestBody Avaliacao:

    public Avaliacao criar(@Valid @RequestBody Avaliacao avaliacao)
    public Avaliacao atualizar(@PathVariable Long id, @Valid @RequestBody Avaliacao avaliacao)

  Importe: import jakarta.validation.Valid;

PASSO 3 — Garanta que GlobalExceptionHandler trata MethodArgumentNotValidException:
  (Se task 07 e 08 já foram feitas, isso já está resolvido)
  Caso contrário, adicione tratamento básico no AvaliacaoController:

  @ExceptionHandler(MethodArgumentNotValidException.class)
  → Essa anotação só funciona em @ControllerAdvice. Use a task 07 para isso.

PASSO 4 — Corrija o AvaliacaoDataLoader.java:
  Garanta que o Faker gera notas entre 0.0 e 10.0:
    avaliacao.setNota(Math.round(faker.number().randomDouble(1, 0, 10) * 10.0) / 10.0);

  Verifique que o valor gerado está dentro do range (0.0 a 10.0 inclusive).

PASSO 5 — Execute `mvn compile` e teste.
```

---

## Arquivo a modificar

- [`Model/Avaliacao.java`](../src/main/java/com/exemplo/crudmongo/Model/Avaliacao.java)
- [`controller/AvaliacaoController.java`](../src/main/java/com/exemplo/crudmongo/controller/AvaliacaoController.java)
- [`config/AvaliacaoDataLoader.java`](../src/main/java/com/exemplo/crudmongo/config/AvaliacaoDataLoader.java) ← verificar

## Dependências

> Esta task funciona melhor combinada com:
> - **Task 07** (Exception Handler) — para retornar HTTP 400 corretamente
> - **Task 08** (Validação de Campos) — que já adiciona outras validações nos Models

## Casos de teste esperados

| Input | Resultado esperado |
|---|---|
| `"nota": 0.0` | HTTP 200 ✅ |
| `"nota": 5.5` | HTTP 200 ✅ |
| `"nota": 10.0` | HTTP 200 ✅ |
| `"nota": -0.1` | HTTP 400 ❌ "Nota mínima é 0.0" |
| `"nota": 10.1` | HTTP 400 ❌ "Nota máxima é 10.0" |
| `"nota": 100` | HTTP 400 ❌ "Nota máxima é 10.0" |

## Checklist

- [ ] `@DecimalMin(0.0)` e `@DecimalMax(10.0)` no campo `nota` de `Avaliacao`
- [ ] `@Valid` no `AvaliacaoController` nos métodos POST e PUT
- [ ] `mvn compile` sem erros
- [ ] `POST /api/avaliacao { "nota": -1 }` → HTTP 400
- [ ] `POST /api/avaliacao { "nota": 11 }` → HTTP 400
- [ ] `POST /api/avaliacao { "nota": 7.5 }` → HTTP 200
