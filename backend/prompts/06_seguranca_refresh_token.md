# 🔐 Task 06 — Implementar Refresh Token JWT

## Contexto

**Limitação detectada na engenharia reversa:**

O token JWT atual expira em **1 hora** (3.600.000 ms) e **não há mecanismo de renovação**.
Quando o token expira, o usuário precisa fazer login novamente.

```java
// JwtUtil.java
private final long EXPIRATION = 1000 * 60 * 60; // 1 hora — sem renovação
```

**Comportamento atual:**
- Token expira → todas as requisições retornam 401/403
- Usuário precisa fazer novo login manualmente
- Não existe endpoint `/api/auth/refresh`

---

## Objetivo

Implementar um mecanismo de refresh token para renovar o JWT sem exigir novo login.

---

## Prompt para o agente

```
No projeto Spring Boot em:
/crudfullstackSpring/backend/

Implemente um sistema de refresh token seguindo estes passos:

PASSO 1 — Adicionar campo refreshToken no modelo Usuario:
  Em Model/Usuario.java, adicione:
    private String refreshToken;
    private java.time.LocalDateTime refreshTokenExpiry;
  Com getters e setters correspondentes.

PASSO 2 — Atualizar JwtUtil.java:
  Adicione um método para gerar refresh token (string aleatória UUID):
    public String generateRefreshToken() {
        return java.util.UUID.randomUUID().toString();
    }

  Adicione configuração de expiração do refresh token (ex: 7 dias):
    @Value("${jwt.refresh.expiration:604800000}")
    private long REFRESH_EXPIRATION; // 7 dias em ms

PASSO 3 — Atualizar UsuarioService.java:
  No método salvarUsuario(), gere e salve o refreshToken.
  Adicione método:
    public Usuario renovarRefreshToken(String refreshToken)
    → busca usuário pelo refreshToken
    → valida se não expirou (comparar LocalDateTime.now())
    → gera novo refreshToken e atualiza no banco
    → retorna o usuário atualizado

PASSO 4 — Atualizar AuthController.java:
  No endpoint /login, além do token JWT, retorne também o refreshToken:
    response.put("token", jwtToken);
    response.put("refreshToken", refreshToken);
    response.put("role", role);

  Adicione endpoint:
    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        // Valida refreshToken no banco
        // Gera novo JWT
        // Retorna novo { token, refreshToken }
    }

  Libere /api/auth/refresh como rota pública no SecurityConfig.java:
    .requestMatchers("/api/auth/refresh").permitAll()

PASSO 5 — Atualizar application.properties:
  jwt.expiration=3600000        # 1 hora (token de acesso)
  jwt.refresh.expiration=604800000  # 7 dias (refresh token)

PASSO 6 — Execute `mvn compile` e teste o fluxo completo.
```

---

## Fluxo esperado após a implementação

```
1. POST /api/auth/login → { token: "eyJ...", refreshToken: "uuid-...", role: "PROFESSOR" }
2. Use o token nas requisições normais (Authorization: Bearer <token>)
3. Token expira após 1h → próxima requisição retorna 401
4. Cliente chama: POST /api/auth/refresh { "refreshToken": "uuid-..." }
5. Resposta: { token: "novo-eyJ...", refreshToken: "novo-uuid-..." }
6. Continue usando o novo token
```

## Checklist

- [ ] Campo `refreshToken` e `refreshTokenExpiry` em `Usuario`
- [ ] Método `generateRefreshToken()` em `JwtUtil`
- [ ] Endpoint `POST /api/auth/refresh` implementado
- [ ] Refresh token armazenado no banco ao fazer login
- [ ] Refresh token inválido/expirado retorna 401
- [ ] `mvn compile` sem erros
- [ ] Fluxo completo testado no Postman
