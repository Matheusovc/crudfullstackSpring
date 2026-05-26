# 📁 Pasta de Prompts — Sistema de Cadastro Acadêmico

Esta pasta contém tasks de desenvolvimento organizadas a partir da engenharia reversa do sistema.  
Cada arquivo representa uma tarefa independente que pode ser executada separadamente.

---

## 🗂️ Índice de Tasks

### 🏗️ Arquitetura
| Arquivo | Descrição | Prioridade |
|---|---|---|
| `01_arquitetura_nova_entidade.md` | Guia para adicionar uma nova entidade seguindo o padrão do projeto | 🟢 Referência |
| `02_arquitetura_padronizar_endpoints.md` | Padronizar nomenclatura de endpoints (plural vs singular) | 🟡 Média |
| `03_arquitetura_ordem_dataloaders.md` | Garantir ordem de execução dos DataLoaders com `@Order` | 🟡 Média |

### 🔐 Segurança
| Arquivo | Descrição | Prioridade |
|---|---|---|
| `04_seguranca_jwt_chave_externa.md` | Externalizar chave JWT para variável de ambiente | 🔴 Alta |
| `05_seguranca_h2_console.md` | Restringir acesso ao console H2 em produção | 🟡 Média |
| `06_seguranca_refresh_token.md` | Implementar refresh token JWT | 🟢 Baixa |

### ⚙️ Lógica
| Arquivo | Descrição | Prioridade |
|---|---|---|
| `07_logica_exception_handler.md` | Criar `@ControllerAdvice` com tratamento global de exceções | 🔴 Alta |
| `08_logica_validacao_campos.md` | Adicionar `@Valid` e Bean Validation nas entidades | 🟡 Média |
| `09_logica_soft_delete.md` | Implementar soft delete real usando o campo `ativo` | 🟡 Média |
| `10_logica_pessoadata_loader_fix.md` | Corrigir volume excessivo no PessoaDataLoader (500k → 200) | 🔴 Alta |

### 📋 Negócio
| Arquivo | Descrição | Prioridade |
|---|---|---|
| `11_negocio_enum_situacao_matricula.md` | Converter `situacao` de Matrícula para Enum | 🟢 Baixa |
| `12_negocio_enum_tipo_avaliacao.md` | Converter `tipoAvaliacao` de Avaliação para Enum | 🟢 Baixa |
| `13_negocio_unicidade_matricula.md` | Adicionar restrição de unicidade em Matrícula (Pessoa + Curso) | 🟡 Média |
| `14_negocio_integridade_referencial.md` | Implementar relacionamentos JPA reais com `@ManyToOne` | 🟡 Média |
| `15_negocio_filtro_ativo_listagem.md` | Filtrar registros com `ativo = false` nas listagens | 🟢 Baixa |
| `16_negocio_validacao_nota.md` | Validar que `nota` está entre 0.0 e 10.0 em Avaliação | 🟡 Média |

---

## 📌 Legenda de Prioridade

| Símbolo | Prioridade | Quando executar |
|---|---|---|
| 🔴 Alta | Crítico | Antes de qualquer deploy ou uso em produção |
| 🟡 Média | Importante | Sprint atual ou próxima iteração |
| 🟢 Baixa/Referência | Nice to have | Backlog ou estudo |
