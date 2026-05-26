# 📋 Task 12 — Converter `tipoAvaliacao` de Avaliação para Enum

## Contexto

**Problema detectado na engenharia reversa:**

O campo `tipoAvaliacao` em `Avaliacao` é um `String` livre, sem validação:

```java
// Model/Avaliacao.java
private String tipoAvaliacao;  // aceita qualquer string
```

**Valores esperados (definidos apenas no DataLoader):**
- `"Prova"`
- `"Trabalho"`
- `"Seminário"`
- `"Projeto"`
- `"Exercício"`

Atualmente é possível salvar `tipoAvaliacao = "qualquer coisa"` sem nenhum erro.

---

## Objetivo

Criar um enum `TipoAvaliacao` e usá-lo no Model `Avaliacao` para garantir
que apenas tipos válidos sejam aceitos.

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/

PASSO 1 — Crie o enum:
  Crie o arquivo: Model/TipoAvaliacao.java

  package com.exemplo.crudmongo.Model;

  public enum TipoAvaliacao {
      PROVA,
      TRABALHO,
      SEMINARIO,
      PROJETO,
      EXERCICIO
  }

  Nota: use nomes sem acentos para evitar problemas de encoding.

PASSO 2 — Atualize o Model Avaliacao.java:
  Mude o campo:
    private String tipoAvaliacao;
  para:
    @Enumerated(EnumType.STRING)
    private TipoAvaliacao tipoAvaliacao;

  Importe: import jakarta.persistence.EnumType; import jakarta.persistence.Enumerated;

  Atualize getter e setter:
    public TipoAvaliacao getTipoAvaliacao() { return tipoAvaliacao; }
    public void setTipoAvaliacao(TipoAvaliacao tipoAvaliacao) { this.tipoAvaliacao = tipoAvaliacao; }

PASSO 3 — Atualize o AvaliacaoService.java:
  No método atualizar(), a linha:
    a.setTipoAvaliacao(novaAvaliacao.getTipoAvaliacao());
  permanece igual — sem mudança necessária.

PASSO 4 — Atualize o AvaliacaoDataLoader.java:
  Mude de String[] para TipoAvaliacao[]:
    TipoAvaliacao[] tipos = TipoAvaliacao.values();
    avaliacao.setTipoAvaliacao(tipos[faker.number().numberBetween(0, tipos.length)]);

  Importe: import com.exemplo.crudmongo.Model.TipoAvaliacao;

PASSO 5 — Execute `mvn compile` e teste.

EXEMPLO de body JSON após a mudança:
  POST /api/avaliacao
  {
    "pessoaId": 1,
    "disciplinaId": 1,
    "nota": 8.5,
    "tipoAvaliacao": "PROVA",    ← deve aceitar
    "data": "2025-06-15",
    "ativo": true
  }

  POST /api/avaliacao
  {
    "tipoAvaliacao": "INVALIDO"  ← deve retornar erro de desserialização JSON
  }
```

---

## Arquivo a criar

- `Model/TipoAvaliacao.java` ← novo

## Arquivos a modificar

- [`Model/Avaliacao.java`](../src/main/java/com/exemplo/crudmongo/Model/Avaliacao.java)
- [`config/AvaliacaoDataLoader.java`](../src/main/java/com/exemplo/crudmongo/config/AvaliacaoDataLoader.java)

## Checklist

- [ ] `TipoAvaliacao.java` criado com 5 valores
- [ ] Campo `tipoAvaliacao` em `Avaliacao` usando o enum com `@Enumerated(EnumType.STRING)`
- [ ] `AvaliacaoDataLoader` atualizado para usar o enum
- [ ] `mvn compile` sem erros
- [ ] `POST` com `"tipoAvaliacao": "PROVA"` → HTTP 200
- [ ] `POST` com `"tipoAvaliacao": "INVALIDO"` → HTTP 400
