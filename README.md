# Backend Spring Boot

CRUD com MongoDB Atlas

## Endpoints criados e exemplos de uso

Os endpoints das novas entidades seguem o padrão REST com autenticação JWT. Para testar operações de criação, atualização e exclusão, faça login como professor:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"professor","password":"prof123"}'
```

Use o valor retornado em `token` no header:

```bash
Authorization: Bearer SEU_TOKEN
```

### Professor

- `GET /api/professores`
- `POST /api/professores`
- `PUT /api/professores/{id}`
- `DELETE /api/professores/{id}`

```bash
curl -X POST http://localhost:8080/api/professores \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"nome":"Professor Teste","area":"Fisica","ativo":true}'
```

### Disciplina

- `GET /api/disciplinas`
- `POST /api/disciplinas`
- `PUT /api/disciplinas/{id}`
- `DELETE /api/disciplinas/{id}`

```bash
curl -X POST http://localhost:8080/api/disciplinas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"nome":"Disciplina Teste","cargaHoraria":60,"professorId":1,"ativo":true}'
```

### Turma

- `GET /api/turmas`
- `POST /api/turmas`
- `PUT /api/turmas/{id}`
- `DELETE /api/turmas/{id}`

```bash
curl -X POST http://localhost:8080/api/turmas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"nome":"Turma Teste","ano":2026,"ativo":true}'
```

### Matricula

- `GET /api/matriculas`
- `POST /api/matriculas`
- `PUT /api/matriculas/{id}`
- `DELETE /api/matriculas/{id}`

```bash
curl -X POST http://localhost:8080/api/matriculas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"pessoaId":1,"cursoId":1,"dataMatricula":"2026-05-10","ativo":true}'
```

### Avaliacao

- `GET /api/avaliacoes`
- `POST /api/avaliacoes`
- `PUT /api/avaliacoes/{id}`
- `DELETE /api/avaliacoes/{id}`

```bash
curl -X POST http://localhost:8080/api/avaliacoes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"pessoaId":1,"disciplinaId":1,"nota":8.5,"data":"2026-05-10","ativo":true}'
```

Exemplo de atualização:

```bash
curl -X PUT http://localhost:8080/api/turmas/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN" \
  -d '{"nome":"Turma Atualizada","ano":2027,"ativo":false}'
```

Exemplo de exclusão:

```bash
curl -X DELETE http://localhost:8080/api/turmas/1 \
  -H "Authorization: Bearer SEU_TOKEN"
```
