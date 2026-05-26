# 🔐 Task 04 — Externalizar Chave Secreta JWT [ALTA PRIORIDADE]

## Contexto

**Vulnerabilidade crítica detectada na engenharia reversa:**

No arquivo `JwtUtil.java`, a chave secreta JWT está **hardcoded** diretamente no código-fonte:

```java
// PROBLEMA: chave exposta no código-fonte
private final SecretKey key = Keys.hmacShaKeyFor(
    "minha-chave-secreta-super-segura-32bytes!!".getBytes()
);
```

**Riscos:**
- Qualquer pessoa com acesso ao repositório Git conhece a chave secreta
- Todos os tokens JWT gerados podem ser forjados por qualquer pessoa
- Em caso de vazamento da chave, todos os tokens precisam ser invalidados

---

## Objetivo

Mover a chave JWT para uma variável de ambiente ou `application.properties`,
sem expô-la no código-fonte versionado.

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/

PASSO 1 — Adicione a chave ao application.properties:
  No arquivo src/main/resources/application.properties, adicione:
    jwt.secret=minha-chave-secreta-super-segura-32bytes!!
    jwt.expiration=3600000

  IMPORTANTE: Adicione application.properties ao .gitignore
  para que a chave real nunca seja versionada em produção.
  Para desenvolvimento local, mantenha um arquivo application.properties.example
  com o valor placeholder.

PASSO 2 — Injete o valor no JwtUtil:
  Abra: src/main/java/com/exemplo/crudmongo/config/JwtUtil.java

  Adicione no topo da classe:
    @Value("${jwt.secret}")
    private String secretString;

    @Value("${jwt.expiration}")
    private long EXPIRATION;

  Importe: import org.springframework.beans.factory.annotation.Value;

  Mude a criação da SecretKey para ser feita sob demanda (lazy):
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretString.getBytes());
    }

  Substitua todas as referências a `key` por `getKey()` nos métodos:
    - generateToken(...)
    - extractClaim(...)

PASSO 3 — Remova o campo `key` hardcoded da classe.

PASSO 4 — Crie um arquivo .env.example na raiz do projeto com:
    JWT_SECRET=sua-chave-aqui-minimo-32-caracteres
    JWT_EXPIRATION=3600000

PASSO 5 — Execute `mvn compile` e verifique que não há erros.
PASSO 6 — Suba a aplicação e teste o login via POST /api/auth/login.
```

---

## Arquivos a modificar

- [`config/JwtUtil.java`](../src/main/java/com/exemplo/crudmongo/config/JwtUtil.java)
- [`resources/application.properties`](../src/main/resources/application.properties)
- [`.gitignore`](../.gitignore) ← verificar se application.properties está listado

## Checklist

- [ ] `jwt.secret` movido para `application.properties`
- [ ] `jwt.expiration` movido para `application.properties`
- [ ] `JwtUtil.java` não contém mais nenhuma string literal de chave
- [ ] `@Value` injetando corretamente os valores
- [ ] `mvn compile` sem erros
- [ ] Login funcional via `POST /api/auth/login`
- [ ] Token gerado válido para autenticação nos endpoints protegidos
- [ ] `.gitignore` atualizado (se necessário)
