# =============================================================================
# Frontend Angular 15 — Dockerfile (multi-stage build)
# =============================================================================
# Stage 1: Compila o Angular com Node.js
# Stage 2: Serve os arquivos estáticos com Nginx Alpine
# =============================================================================

# ─── Stage 1: Build Angular ───────────────────────────────────────────────────
FROM node:18-alpine AS build-stage

WORKDIR /app

# Copia os manifestos de dependências primeiro (cache de camadas)
COPY package*.json ./

# Instala dependências (--legacy-peer-deps para Angular 15)
RUN npm install --legacy-peer-deps

# Copia o código-fonte e compila em modo produção
COPY . .
RUN npx ng build --configuration production

# ─── Stage 2: Nginx para servir os arquivos compilados ────────────────────────
FROM nginx:1.25-alpine

# Copia a configuração customizada do Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# O outputPath do Angular 15 é dist/frontend/ (conforme angular.json)
COPY --from=build-stage /app/dist/frontend/ /usr/share/nginx/html/

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
