# ⚙️ Task 08 — Adicionar Validação de Campos com Bean Validation

## Contexto

**Problema detectado na engenharia reversa:**

Nenhuma entidade possui validações de entrada (`@NotNull`, `@NotBlank`, `@Min`, etc.).
Qualquer JSON — mesmo vazio `{}` — é aceito e salvo no banco.

**Exemplos de dados inválidos aceitos atualmente:**
```json
POST /api/professor → {}                          ← aceito (todos nulos)
POST /api/avaliacao → { "nota": -50 }             ← aceito (nota negativa)
POST /api/pessoa    → { "email": "não-é-email" }  ← aceito (email inválido)
POST /api/matricula → { "dataMatricula": "abc" }  ← aceito (data inválida)
```

---

## Objetivo

Adicionar `@Valid` nos Controllers e anotações de validação nas entidades Model,
usando a API Jakarta Bean Validation (já inclusa via `spring-boot-starter-web`).

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/

PASSO 1 — Adicione validações no Model/Pessoa.java:
  import jakarta.validation.constraints.*;

  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @Min(value = 1, message = "Idade mínima é 1")
  @Max(value = 120, message = "Idade máxima é 120")
  private int idade;

  @Email(message = "E-mail inválido")
  @NotBlank(message = "E-mail é obrigatório")
  private String email;

PASSO 2 — Adicione validações no Model/Curso.java:
  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @Min(value = 1, message = "Carga horária mínima é 1 hora")
  @Max(value = 9999, message = "Carga horária máxima é 9999 horas")
  private int cargaHoraria;

PASSO 3 — Adicione validações no Model/Professor.java:
  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @Min(value = 18, message = "Idade mínima é 18 anos")
  @Max(value = 80, message = "Idade máxima é 80 anos")
  private int idade;

  @Email(message = "E-mail inválido")
  @NotBlank(message = "E-mail é obrigatório")
  private String email;

  @NotBlank(message = "Área de atuação é obrigatória")
  private String area;

PASSO 4 — Adicione validações no Model/Disciplina.java:
  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @Min(value = 1, message = "Carga horária mínima é 1 hora")
  private int cargaHoraria;

PASSO 5 — Adicione validações no Model/Turma.java:
  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @Min(value = 2000, message = "Ano mínimo é 2000")
  @Max(value = 2100, message = "Ano máximo é 2100")
  private int ano;

  @NotBlank(message = "Semestre é obrigatório")
  private String semestre;

  @NotBlank(message = "Turno é obrigatório")
  private String turno;

PASSO 6 — Adicione validações no Model/Matricula.java:
  @NotNull(message = "ID da pessoa é obrigatório")
  private Long pessoaId;

  @NotNull(message = "ID do curso é obrigatório")
  private Long cursoId;

  @NotBlank(message = "Data de matrícula é obrigatória")
  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Data deve estar no formato YYYY-MM-DD")
  private String dataMatricula;

PASSO 7 — Adicione validações no Model/Avaliacao.java:
  @NotNull(message = "ID da pessoa é obrigatório")
  private Long pessoaId;

  @NotNull(message = "ID da disciplina é obrigatório")
  private Long disciplinaId;

  @DecimalMin(value = "0.0", message = "Nota mínima é 0.0")
  @DecimalMax(value = "10.0", message = "Nota máxima é 10.0")
  private double nota;

  @NotBlank(message = "Tipo de avaliação é obrigatório")
  private String tipoAvaliacao;

  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Data deve estar no formato YYYY-MM-DD")
  private String data;

PASSO 8 — Adicione @Valid nos Controllers (em todos os @PostMapping e @PutMapping):
  Exemplo em ProfessorController.java:
    public Professor criar(@Valid @RequestBody Professor professor)
    public Professor atualizar(@PathVariable Long id, @Valid @RequestBody Professor professor)

  Repita para: PessoaController, CursoController, DisciplinaController,
               TurmaController, MatriculaController, AvaliacaoController

PASSO 9 — Adicione no GlobalExceptionHandler (task 07) o handler de validação:
  import org.springframework.web.bind.MethodArgumentNotValidException;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException e) {
      List<String> erros = e.getBindingResult().getFieldErrors()
          .stream()
          .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
          .collect(Collectors.toList());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
          "timestamp", LocalDateTime.now().toString(),
          "status", 400,
          "error", "Dados inválidos",
          "campos", erros
      ));
  }

PASSO 10 — Execute `mvn compile` e teste com dados inválidos.
```

---

## Checklist

- [ ] Validações adicionadas em todos os 7 Models de domínio
- [ ] `@Valid` adicionado em todos os `@PostMapping` e `@PutMapping` dos Controllers
- [ ] Handler `MethodArgumentNotValidException` no `GlobalExceptionHandler`
- [ ] `mvn compile` sem erros
- [ ] `POST /api/professor {}` → HTTP 400 com lista de campos inválidos
- [ ] `POST /api/avaliacao { "nota": -5 }` → HTTP 400 "Nota mínima é 0.0"
- [ ] `POST /api/pessoa { "email": "invalido" }` → HTTP 400 "E-mail inválido"
