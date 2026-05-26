# 🔐 Task 05 — Restringir Acesso ao Console H2

## Contexto

**Problema detectado na engenharia reversa:**

No `SecurityConfig.java`, o console H2 está configurado como rota pública:

```java
.requestMatchers("/h2-console/**").permitAll()  // SEM autenticação
```

E no `application.properties`:
```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

O console H2 permite **visualizar e executar SQL diretamente no banco**.
Em um ambiente de desenvolvimento compartilhado ou homologação, isso expõe todos os dados.

**Comportamento atual:**
- Qualquer pessoa (sem token) pode acessar `http://localhost:8080/h2-console`
- Consegue se conectar ao banco com `sa/sa` e executar qualquer SQL

---

## Objetivo

Separar a configuração de segurança do H2 entre ambientes de desenvolvimento e produção,
e — no mínimo — exigir autenticação para acessar o console.

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/

OPÇÃO A (mínima — desabilitar o console em produção):

  No arquivo src/main/resources/application.properties, altere:
    spring.h2.console.enabled=false

  Crie um arquivo src/main/resources/application-dev.properties com:
    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console

  Para rodar em modo dev, adicione o argumento: --spring.profiles.active=dev

OPÇÃO B (intermediária — manter mas restringir ao perfil dev):

  Em SecurityConfig.java, envolva a liberação do H2 com um perfil:
  
  Injete:
    @Value("${spring.h2.console.enabled:false}")
    private boolean h2ConsoleEnabled;

  E na configuração de segurança, libere o /h2-console somente se habilitado.

OPÇÃO C (completa — autenticar acesso ao H2):

  Em SecurityConfig.java, substitua:
    .requestMatchers("/h2-console/**").permitAll()
  por:
    .requestMatchers("/h2-console/**").hasRole("PROFESSOR")

  Isso exigirá que o usuário seja PROFESSOR para acessar.
  ATENÇÃO: O console H2 usa frames, então o headers.frameOptions deve
  permanecer desabilitado.

RECOMENDAÇÃO: Implemente a OPÇÃO A para produção e OPÇÃO C para desenvolvimento.

Após a mudança, execute:
1. `mvn compile` → sem erros
2. Tente acessar /h2-console sem token → deve retornar 401 ou redirecionar
3. Tente acessar com token de PROFESSOR → deve funcionar (OPÇÃO C)
```

---

## Arquivos a modificar

- [`config/SecurityConfig.java`](../src/main/java/com/exemplo/crudmongo/config/SecurityConfig.java)
- [`resources/application.properties`](../src/main/resources/application.properties)

## Checklist

- [ ] H2 console não acessível publicamente sem autenticação
- [ ] Em ambiente dev, console H2 ainda funcionando para desenvolvimento
- [ ] `mvn compile` sem erros
- [ ] Acesso sem token → negado
