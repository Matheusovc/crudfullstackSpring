# =============================================================================
# Dockerfile raiz — Backend Monolítico (Spring Boot + Java 17)
# =============================================================================
# Este arquivo é um atalho para buildar o backend monolítico a partir da raiz.
# O docker-compose usa ./backend/Dockerfile como contexto principal.
#
# Uso standalone:
#   docker build -t crud-springboot .
#   docker run -p 8080:8080 crud-springboot
# =============================================================================

FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY backend/pom.xml .
RUN mvn dependency:go-offline -q
COPY backend/src ./src
RUN mvn clean package -DskipTests -q

FROM eclipse-temurin:17
RUN groupadd -r spring && useradd -r -g spring spring \
    && mkdir -p /data && chown spring:spring /data
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN chown spring:spring app.jar
USER spring:spring
EXPOSE 8080
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]
