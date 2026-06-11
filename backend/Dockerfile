# =============================================================================
# Backend Monolítico — Dockerfile (multi-stage build)
# =============================================================================
# Stage 1: Build com Maven + JDK 17
# Stage 2: Runtime com JRE 17 Alpine (imagem ~80% menor)
# =============================================================================

# ─── Stage 1: Build ───────────────────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o pom.xml primeiro para aproveitar o cache de camadas do Docker.
# As dependências só são baixadas novamente se o pom.xml mudar.
COPY pom.xml .
RUN mvn dependency:go-offline -q -Dhttps.protocols=TLSv1.2

# Copia o código-fonte e compila o JAR
COPY src ./src
RUN mvn clean package -DskipTests -q -Dhttps.protocols=TLSv1.2

# ─── Stage 2: Runtime ─────────────────────────────────────────────────────────
FROM azul/zulu-openjdk-alpine:17-jre

# Cria usuário e grupo não-root para executar a aplicação
RUN addgroup -S spring && adduser -S -G spring spring \
    && mkdir -p /data && chown spring:spring /data

WORKDIR /app

# Copia apenas o JAR compilado
COPY --from=build /app/target/*.jar app.jar
RUN chown spring:spring app.jar

USER spring:spring

EXPOSE 8080

# Flags JVM otimizadas para containers:
#   UseContainerSupport  → respeita os limites de memória do Docker
#   MaxRAMPercentage     → usa no máximo 75% da RAM do container
#   urandom              → acelera o startup (evita bloqueio de entropia)
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]
