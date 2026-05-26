# 📚 Documentação de Endpoints — Sistema de Cadastro Acadêmico

> **Base URL:** `http://localhost:8080`
> **Autenticação:** Bearer JWT Token (obrigatório em todos os endpoints)
>
> **Roles disponíveis:**
> - `PROFESSOR` — pode criar, listar, atualizar e excluir
> - `ALUNO` — pode apenas listar (GET)

---

## 🔐 Autenticação

### Login
```
POST /api/auth/login
```
**Body (JSON):**
```json
{
  "username": "professor@escola.com",
  "password": "123456"
}
```
**Resposta:**
```json
{
  "token": "eyJhbGci..."
}
```
Use o token retornado no header: `Authorization: Bearer <token>`

---

## 👤 Pessoa

| Método | Endpoint | Role | Descrição |
|--------|----------|------|-----------|
| GET | `/api/pessoa` | ALUNO, PROFESSOR | Lista todas as pessoas |
| POST | `/api/pessoa` | PROFESSOR | Cria uma nova pessoa |
| PUT | `/api/pessoa/{id}` | PROFESSOR | Atualiza uma pessoa pelo ID |
| DELETE | `/api/pessoa/{id}` | PROFESSOR | Exclui uma pessoa pelo ID |

**Exemplo de body (POST/PUT):**
```json
{
  "nome": "João da Silva",
  "idade": 22,
  "email": "joao@email.com",
  "ativo": true
}
```

---

## 📘 Curso

| Método | Endpoint | Role | Descrição |
|--------|----------|------|-----------|
| GET | `/api/curso` | ALUNO, PROFESSOR | Lista todos os cursos |
| POST | `/api/curso` | PROFESSOR | Cria um novo curso |
| PUT | `/api/curso/{id}` | PROFESSOR | Atualiza um curso pelo ID |
| DELETE | `/api/curso/{id}` | PROFESSOR | Exclui um curso pelo ID |

**Exemplo de body (POST/PUT):**
```json
{
  "nome": "Engenharia de Software",
  "cargaHoraria": 3600,
  "ativo": true
}
```

---

## 🎓 Professor

| Método | Endpoint | Role | Descrição |
|--------|----------|------|-----------|
| GET | `/api/professor` | ALUNO, PROFESSOR | Lista todos os professores |
| POST | `/api/professor` | PROFESSOR | Cria um novo professor |
| PUT | `/api/professor/{id}` | PROFESSOR | Atualiza um professor pelo ID |
| DELETE | `/api/professor/{id}` | PROFESSOR | Exclui um professor pelo ID |

**Exemplo de body (POST/PUT):**
```json
{
  "nome": "Maria Oliveira",
  "idade": 42,
  "email": "maria.oliveira@escola.com",
  "area": "Matemática",
  "ativo": true
}
```

---

## 📖 Disciplina

| Método | Endpoint | Role | Descrição |
|--------|----------|------|-----------|
| GET | `/api/disciplina` | ALUNO, PROFESSOR | Lista todas as disciplinas |
| POST | `/api/disciplina` | PROFESSOR | Cria uma nova disciplina |
| PUT | `/api/disciplina/{id}` | PROFESSOR | Atualiza uma disciplina pelo ID |
| DELETE | `/api/disciplina/{id}` | PROFESSOR | Exclui uma disciplina pelo ID |

**Exemplo de body (POST/PUT):**
```json
{
  "nome": "Cálculo I",
  "cargaHoraria": 60,
  "descricao": "Fundamentos do cálculo diferencial e integral.",
  "ativo": true
}
```

---

## 🏫 Turma

| Método | Endpoint | Role | Descrição |
|--------|----------|------|-----------|
| GET | `/api/turma` | ALUNO, PROFESSOR | Lista todas as turmas |
| POST | `/api/turma` | PROFESSOR | Cria uma nova turma |
| PUT | `/api/turma/{id}` | PROFESSOR | Atualiza uma turma pelo ID |
| DELETE | `/api/turma/{id}` | PROFESSOR | Exclui uma turma pelo ID |

**Exemplo de body (POST/PUT):**
```json
{
  "nome": "Turma A - Noite",
  "ano": 2025,
  "semestre": "1º Semestre",
  "turno": "Noite",
  "ativo": true
}
```

---

## 📋 Matrícula

| Método | Endpoint | Role | Descrição |
|--------|----------|------|-----------|
| GET | `/api/matricula` | ALUNO, PROFESSOR | Lista todas as matrículas |
| POST | `/api/matricula` | PROFESSOR | Cria uma nova matrícula |
| PUT | `/api/matricula/{id}` | PROFESSOR | Atualiza uma matrícula pelo ID |
| DELETE | `/api/matricula/{id}` | PROFESSOR | Exclui uma matrícula pelo ID |

**Exemplo de body (POST/PUT):**
```json
{
  "pessoaId": 1,
  "cursoId": 2,
  "dataMatricula": "2025-02-01",
  "situacao": "ATIVO",
  "ativo": true
}
```
> **Situações válidas:** `ATIVO`, `TRANCADO`, `CONCLUIDO`

---

## 📝 Avaliação

| Método | Endpoint | Role | Descrição |
|--------|----------|------|-----------|
| GET | `/api/avaliacao` | ALUNO, PROFESSOR | Lista todas as avaliações |
| POST | `/api/avaliacao` | PROFESSOR | Cria uma nova avaliação |
| PUT | `/api/avaliacao/{id}` | PROFESSOR | Atualiza uma avaliação pelo ID |
| DELETE | `/api/avaliacao/{id}` | PROFESSOR | Exclui uma avaliação pelo ID |

**Exemplo de body (POST/PUT):**
```json
{
  "pessoaId": 1,
  "disciplinaId": 3,
  "nota": 8.5,
  "tipoAvaliacao": "Prova",
  "data": "2025-06-15",
  "ativo": true
}
```
> **Tipos válidos:** `Prova`, `Trabalho`, `Seminário`, `Projeto`, `Exercício`

---

## 🧪 Testando com Postman

### Passo a passo:
1. **Registre/autentique** em `POST /api/auth/login` com as credenciais de um usuário PROFESSOR.
2. **Copie o token** JWT retornado.
3. Em cada requisição, adicione o header:
   - `Authorization: Bearer SEU_TOKEN_AQUI`
4. Para **GET**, não é necessário body.
5. Para **POST** e **PUT**, defina `Content-Type: application/json` e envie o body JSON conforme os exemplos acima.
6. Para **DELETE**, basta o ID na URL.

### Exemplo completo — Criar e atualizar um Professor:
```
# 1. Criar professor
POST http://localhost:8080/api/professor
Authorization: Bearer <token>
Content-Type: application/json

{
  "nome": "Carlos Mendes",
  "idade": 38,
  "email": "carlos.mendes@escola.com",
  "area": "Física",
  "ativo": true
}

# 2. Atualizar professor (use o ID retornado acima)
PUT http://localhost:8080/api/professor/1
Authorization: Bearer <token>
Content-Type: application/json

{
  "nome": "Carlos Mendes",
  "idade": 39,
  "email": "carlos.mendes@escola.com",
  "area": "Física Quântica",
  "ativo": true
}

# 3. Listar todos
GET http://localhost:8080/api/professor
Authorization: Bearer <token>

# 4. Excluir
DELETE http://localhost:8080/api/professor/1
Authorization: Bearer <token>
```
