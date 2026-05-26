#!/bin/bash
# ──────────────────────────────────────────────────────────────────
# start-all.sh — Inicia todos os microservicos em background
# Uso: ./start-all.sh
# ──────────────────────────────────────────────────────────────────

BASE_DIR="$(cd "$(dirname "$0")" && pwd)"
LOG_DIR="$BASE_DIR/logs"

mkdir -p "$LOG_DIR"

echo ""
echo "🚀 Iniciando microservicos academicos..."
echo ""

# Funcao auxiliar
start_service() {
  local name="$1"
  local dir="$BASE_DIR/$name"
  local port="$2"
  local log="$LOG_DIR/$name.log"

  echo "  ▶ $name (porta $port)..."
  cd "$dir"
  mvn spring-boot:run > "$log" 2>&1 &
  echo "    PID=$! | log: logs/$name.log"
}

start_service "matricula-service"  8081
sleep 2
start_service "pessoa-service"     8082
sleep 2
start_service "curso-service"      8083
sleep 2
start_service "disciplina-service" 8084
sleep 2
start_service "professor-service"  8085
sleep 2
start_service "turma-service"      8086
sleep 3
start_service "api-gateway"        8080

echo ""
echo "⏳ Aguardando servicos iniciarem (30s)..."
sleep 30

echo ""
echo "✅ Servicos disponiveis:"
echo "   API Gateway       -> http://localhost:8080"
echo "   matricula-service -> http://localhost:8081"
echo "   pessoa-service    -> http://localhost:8082"
echo "   curso-service     -> http://localhost:8083"
echo "   disciplina-service-> http://localhost:8084"
echo "   professor-service -> http://localhost:8085"
echo "   turma-service     -> http://localhost:8086"
echo ""
echo "📋 Endpoints via Gateway:"
echo "   GET http://localhost:8080/api/matriculas"
echo "   GET http://localhost:8080/api/pessoas"
echo "   GET http://localhost:8080/api/cursos"
echo "   GET http://localhost:8080/api/disciplinas"
echo "   GET http://localhost:8080/api/professores"
echo "   GET http://localhost:8080/api/turmas"
echo ""
echo "Para parar: kill \$(lsof -ti:8080,8081,8082,8083,8084,8085,8086)"
