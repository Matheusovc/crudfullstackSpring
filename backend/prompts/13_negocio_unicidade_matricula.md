# 📋 Task 13 — Adicionar Unicidade na Matrícula (Pessoa + Curso)

## Contexto

**Regra de negócio ausente detectada na engenharia reversa:**

Atualmente é possível matricular a mesma Pessoa no mesmo Curso múltiplas vezes:

```java
// MatriculaService.java — nenhuma verificação de duplicidade
public Matricula salvar(Matricula matricula) {
    return repository.save(matricula);  // sempre salva, sem verificar duplicata
}
```

**Exemplo do problema:**
```
POST /api/matricula { "pessoaId": 1, "cursoId": 1, ... } → HTTP 200 (id: 1)
POST /api/matricula { "pessoaId": 1, "cursoId": 1, ... } → HTTP 200 (id: 2) ← DUPLICATA
POST /api/matricula { "pessoaId": 1, "cursoId": 1, ... } → HTTP 200 (id: 3) ← DUPLICATA
```

---

## Objetivo

Impedir que a mesma Pessoa seja matriculada no mesmo Curso mais de uma vez
(quando a matrícula estiver ATIVA).

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/

PASSO 1 — Adicione método de busca no MatriculaRepository:
  Em repository/MatriculaRepository.java, adicione:
    import java.util.Optional;

    Optional<Matricula> findByPessoaIdAndCursoId(Long pessoaId, Long cursoId);

    // Versão que verifica apenas matrículas ativas:
    boolean existsByPessoaIdAndCursoIdAndAtivoTrue(Long pessoaId, Long cursoId);

PASSO 2 — Adicione a verificação no MatriculaService:
  No método salvar(), adicione antes do repository.save():

    public Matricula salvar(Matricula matricula) {
        boolean jaMatriculado = repository.existsByPessoaIdAndCursoIdAndAtivoTrue(
            matricula.getPessoaId(),
            matricula.getCursoId()
        );
        if (jaMatriculado) {
            throw new IllegalStateException(
                "Pessoa " + matricula.getPessoaId() +
                " já está matriculada no curso " + matricula.getCursoId()
            );
        }
        return repository.save(matricula);
    }

  Importe: (nenhum import adicional necessário)

PASSO 3 — Adicione handler para IllegalStateException no GlobalExceptionHandler:
  (Somente se a task 07 já foi executada)

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<Map<String, Object>> handleConflict(IllegalStateException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
          "timestamp", LocalDateTime.now().toString(),
          "status", 409,
          "error", "Conflito de dados",
          "message", e.getMessage()
      ));
  }

PASSO 4 — (Opcional) Adicione constraint de unicidade no Model:
  Em Model/Matricula.java, adicione na anotação @Table:

    @Table(
        name = "matricula",
        uniqueConstraints = {
            @UniqueConstraint(
                name = "uk_matricula_pessoa_curso",
                columnNames = {"pessoa_id", "curso_id"}
            )
        }
    )

  ATENÇÃO: Isso adiciona constraint no banco H2. Combine com o soft delete (task 09)
  para que matrículas inativas não bloqueiem novas matrículas.

PASSO 5 — Execute `mvn compile` e teste.
```

---

## Arquivos a modificar

- [`repository/MatriculaRepository.java`](../src/main/java/com/exemplo/crudmongo/repository/MatriculaRepository.java)
- [`service/MatriculaService.java`](../src/main/java/com/exemplo/crudmongo/service/MatriculaService.java)
- [`exception/GlobalExceptionHandler.java`](../src/main/java/com/exemplo/crudmongo/exception/GlobalExceptionHandler.java) ← se task 07 foi feita

## Dependências

> Esta task funciona melhor se a **Task 07 (Exception Handler)** e a **Task 09 (Soft Delete)** já foram executadas.

## Checklist

- [ ] `existsByPessoaIdAndCursoIdAndAtivoTrue()` adicionado ao `MatriculaRepository`
- [ ] `MatriculaService.salvar()` verifica duplicidade antes de salvar
- [ ] Duplicate → HTTP 409 Conflict com mensagem clara
- [ ] `mvn compile` sem erros
- [ ] Duas matrículas com mesma Pessoa + Curso → segunda retorna HTTP 409
- [ ] Matrícula com Pessoa + Curso diferentes → ambas aceitas (HTTP 200)
