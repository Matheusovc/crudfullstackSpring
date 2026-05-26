# ⚙️ Task 07 — Criar Handler Global de Exceções [ALTA PRIORIDADE]

## Contexto

**Problema crítico detectado na engenharia reversa:**

Atualmente, **não existe nenhum `@ControllerAdvice`** no projeto.
Quando um erro ocorre (ex: entidade não encontrada), o resultado é:

```
Esperado:  HTTP 404 Not Found  { "error": "Professor não encontrado" }
Atual:     HTTP 500 Internal Server Error  { "timestamp": ..., "error": "Internal Server Error" }
```

**Código problemático em todos os Services:**
```java
.orElseThrow(() -> new RuntimeException("Professor não encontrado"));
```

O `RuntimeException` sem handler vira sempre um HTTP 500.

**Casos não tratados detectados:**
| Situação | Status atual | Status correto |
|---|---|---|
| Entidade não encontrada (GET/PUT/DELETE) | 500 | 404 |
| Delete de ID inexistente | 500 | 404 |
| Token JWT inválido/expirado | 403 | 401 |
| Role inválida no registro | 500 | 400 |
| Username duplicado | 500 | 409 Conflict |

---

## Objetivo

Criar uma classe `GlobalExceptionHandler` com `@ControllerAdvice` para retornar
respostas HTTP semânticas e padronizadas.

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/

PASSO 1 — Crie uma exceção customizada para "não encontrado":
  Crie o arquivo: exception/RecursoNaoEncontradoException.java

  package com.exemplo.crudmongo.exception;

  public class RecursoNaoEncontradoException extends RuntimeException {
      public RecursoNaoEncontradoException(String mensagem) {
          super(mensagem);
      }
  }

PASSO 2 — Crie o handler global:
  Crie o arquivo: exception/GlobalExceptionHandler.java

  package com.exemplo.crudmongo.exception;

  import org.springframework.http.HttpStatus;
  import org.springframework.http.ResponseEntity;
  import org.springframework.web.bind.annotation.ExceptionHandler;
  import org.springframework.web.bind.annotation.RestControllerAdvice;
  import java.time.LocalDateTime;
  import java.util.Map;

  @RestControllerAdvice
  public class GlobalExceptionHandler {

      @ExceptionHandler(RecursoNaoEncontradoException.class)
      public ResponseEntity<Map<String, Object>> handleNaoEncontrado(RecursoNaoEncontradoException e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
              "timestamp", LocalDateTime.now().toString(),
              "status", 404,
              "error", "Recurso não encontrado",
              "message", e.getMessage()
          ));
      }

      @ExceptionHandler(IllegalArgumentException.class)
      public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
              "timestamp", LocalDateTime.now().toString(),
              "status", 400,
              "error", "Requisição inválida",
              "message", e.getMessage()
          ));
      }

      @ExceptionHandler(RuntimeException.class)
      public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
              "timestamp", LocalDateTime.now().toString(),
              "status", 500,
              "error", "Erro interno",
              "message", e.getMessage()
          ));
      }
  }

PASSO 3 — Substitua RuntimeException por RecursoNaoEncontradoException em TODOS os Services:

  Nos arquivos service/PessoaService.java, service/CursoService.java,
  service/ProfessorService.java, service/DisciplinaService.java,
  service/TurmaService.java, service/MatriculaService.java,
  service/AvaliacaoService.java:

  Troque:
    .orElseThrow(() -> new RuntimeException("Entidade não encontrada"))
  por:
    .orElseThrow(() -> new RecursoNaoEncontradoException("Entidade não encontrada"))

  Adicione o import:
    import com.exemplo.crudmongo.exception.RecursoNaoEncontradoException;

PASSO 4 — Atualize AuthController para usar IllegalArgumentException:
  Substitua:
    throw new RuntimeException("Role inválida. Use ALUNO ou PROFESSOR.");
  por:
    throw new IllegalArgumentException("Role inválida. Use ALUNO ou PROFESSOR.");

PASSO 5 — Execute `mvn compile` e teste os cenários de erro.
```

---

## Arquivos a criar

- `exception/RecursoNaoEncontradoException.java` ← novo
- `exception/GlobalExceptionHandler.java` ← novo

## Arquivos a modificar

- `service/PessoaService.java`
- `service/CursoService.java`
- `service/ProfessorService.java`
- `service/DisciplinaService.java`
- `service/TurmaService.java`
- `service/MatriculaService.java`
- `service/AvaliacaoService.java`
- `controller/AuthController.java`

## Checklist

- [ ] `RecursoNaoEncontradoException.java` criado
- [ ] `GlobalExceptionHandler.java` criado com `@RestControllerAdvice`
- [ ] Todos os Services usando `RecursoNaoEncontradoException`
- [ ] `mvn compile` sem erros
- [ ] `GET /api/professor/99999` → HTTP 404 com JSON de erro
- [ ] `DELETE /api/professor/99999` → HTTP 404 com JSON de erro
- [ ] `PUT /api/professor/99999` → HTTP 404 com JSON de erro
