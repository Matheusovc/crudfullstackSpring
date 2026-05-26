# ⚙️ Task 10 — Corrigir Volume Excessivo no PessoaDataLoader [ALTA PRIORIDADE]

## Contexto

**Bug crítico de performance detectado na engenharia reversa:**

No arquivo `PessoaDataLoader.java`, o loop está configurado para inserir **500.000 registros**
em um banco H2 in-memory — enquanto o comentário do código afirma "200 registros":

```java
// PessoaDataLoader.java — LINHA PROBLEMÁTICA
for (int i = 0; i < 500000; i++) {   // ← 500.000 registros!
    Pessoa pessoa = new Pessoa();
    // ...
    repository.save(pessoa);          // ← sem batch, uma INSERT por vez
}

System.out.println("✅ Banco de pessoas populado com 200 registros!");  // ← mentira
```

**Impactos:**
- Inicialização da aplicação pode levar **vários minutos** ou travar
- Estouro de memória heap do H2 in-memory
- Cada `repository.save()` é uma transação separada (sem `saveAll()` em batch)

---

## Objetivo

Corrigir o número de registros para um valor razoável (200) e otimizar a inserção
usando `saveAll()` para batch insert.

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/src/main/java/com/exemplo/crudmongo/config/PessoaDataLoader.java

Faça as seguintes correções:

CORREÇÃO 1 — Reduza o loop de 500.000 para 200:
  Mude:
    for (int i = 0; i < 500000; i++) {
  Para:
    for (int i = 0; i < 200; i++) {

CORREÇÃO 2 — Use List + saveAll() para batch insert (mais eficiente):
  Importe: import java.util.ArrayList; import java.util.List;

  Substitua o loop inteiro por:
    List<Pessoa> pessoas = new ArrayList<>();
    for (int i = 0; i < 200; i++) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(faker.name().fullName());
        pessoa.setIdade(faker.number().numberBetween(18, 80));
        pessoa.setEmail(faker.internet().emailAddress());
        pessoa.setAtivo(faker.bool().bool());
        pessoas.add(pessoa);
    }
    repository.saveAll(pessoas);

CORREÇÃO 3 — Corrija a mensagem de log:
  Mude:
    System.out.println("✅ Banco de pessoas populado com 200 registros!");
  Para:
    System.out.println("✅ Banco de pessoas populado com " + pessoas.size() + " registros!");

APÓS AS CORREÇÕES:
1. Execute `mvn compile` → sem erros
2. Suba a aplicação e meça o tempo de inicialização
3. Verifique nos logs: "✅ Banco de pessoas populado com 200 registros!"
4. Confirme que GET /api/pessoas retorna 200 registros
```

---

## Arquivo a modificar

- [`config/PessoaDataLoader.java`](../src/main/java/com/exemplo/crudmongo/config/PessoaDataLoader.java)

## Antes vs Depois

```java
// ANTES (problemático)
for (int i = 0; i < 500000; i++) {
    Pessoa pessoa = new Pessoa();
    // ...
    repository.save(pessoa);  // uma transação por vez
}

// DEPOIS (correto)
List<Pessoa> pessoas = new ArrayList<>();
for (int i = 0; i < 200; i++) {
    Pessoa pessoa = new Pessoa();
    // ...
    pessoas.add(pessoa);
}
repository.saveAll(pessoas);  // batch insert
```

## Checklist

- [ ] Loop alterado de 500.000 para 200
- [ ] `saveAll()` implementado em vez de `save()` por iteração
- [ ] Mensagem de log corrigida e dinâmica
- [ ] `mvn compile` sem erros
- [ ] Aplicação inicializa em tempo razoável (< 30 segundos)
- [ ] `GET /api/pessoas` retorna exatamente 200 registros
