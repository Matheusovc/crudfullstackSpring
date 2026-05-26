# 📋 Task 11 — Converter `situacao` de Matrícula para Enum

## Contexto

**Problema detectado na engenharia reversa:**

O campo `situacao` em `Matricula` é um `String` livre, sem validação:

```java
// Model/Matricula.java
private String situacao;  // aceita qualquer string: "ATIVO", "ativo", "cancelado", "xyz"...
```

**Valores esperados (definidos apenas no DataLoader):**
- `"ATIVO"`
- `"TRANCADO"`
- `"CONCLUIDO"`

Atualmente é possível salvar `situacao = "qualquer coisa"` sem nenhum erro.

---

## Objetivo

Criar um enum `SituacaoMatricula` e usá-lo no Model `Matricula` para garantir
que apenas valores válidos sejam aceitos.

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/

PASSO 1 — Crie o enum:
  Crie o arquivo: Model/SituacaoMatricula.java

  package com.exemplo.crudmongo.Model;

  public enum SituacaoMatricula {
      ATIVO,
      TRANCADO,
      CONCLUIDO
  }

PASSO 2 — Atualize o Model Matricula.java:
  Mude o campo:
    private String situacao;
  para:
    @Enumerated(EnumType.STRING)
    private SituacaoMatricula situacao;

  Importe: import jakarta.persistence.EnumType; import jakarta.persistence.Enumerated;

  Atualize getter e setter:
    public SituacaoMatricula getSituacao() { return situacao; }
    public void setSituacao(SituacaoMatricula situacao) { this.situacao = situacao; }

PASSO 3 — Atualize o MatriculaService.java:
  No método atualizar(), mude:
    m.setSituacao(novaMatricula.getSituacao());
  (sem mudança necessária — o tipo já é compatível)

PASSO 4 — Atualize o MatriculaDataLoader.java:
  Mude de String[] para SituacaoMatricula[]:
    SituacaoMatricula[] situacoes = SituacaoMatricula.values();
    matricula.setSituacao(situacoes[faker.number().numberBetween(0, situacoes.length)]);

  Importe: import com.exemplo.crudmongo.Model.SituacaoMatricula;

PASSO 5 — Execute `mvn compile` e teste.

EXEMPLO de body JSON após a mudança:
  POST /api/matricula
  {
    "pessoaId": 1,
    "cursoId": 1,
    "dataMatricula": "2025-03-01",
    "situacao": "ATIVO",    ← deve aceitar
    "ativo": true
  }

  POST /api/matricula
  {
    "situacao": "INVALIDO"  ← deve retornar erro de desserialização JSON
  }
```

---

## Arquivo a criar

- `Model/SituacaoMatricula.java` ← novo

## Arquivos a modificar

- [`Model/Matricula.java`](../src/main/java/com/exemplo/crudmongo/Model/Matricula.java)
- [`config/MatriculaDataLoader.java`](../src/main/java/com/exemplo/crudmongo/config/MatriculaDataLoader.java)

## Checklist

- [ ] `SituacaoMatricula.java` criado com 3 valores (ATIVO, TRANCADO, CONCLUIDO)
- [ ] Campo `situacao` em `Matricula` usando o enum com `@Enumerated(EnumType.STRING)`
- [ ] `MatriculaDataLoader` atualizado
- [ ] `mvn compile` sem erros
- [ ] `POST` com `"situacao": "ATIVO"` → HTTP 200
- [ ] `POST` com `"situacao": "INVALIDO"` → HTTP 400 (erro de desserialização)
