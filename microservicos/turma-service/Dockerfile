# =============================================================================
# turma-service — Dockerfile (multi-stage build)
# Porta: 8086
# =============================================================================

FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q -Dhttps.protocols=TLSv1.2
COPY src ./src
RUN mvn clean package -DskipTests -q -Dhttps.protocols=TLSv1.2

FROM azul/zulu-openjdk-alpine:17-jre
RUN addgroup -S spring && adduser -S -G spring spring \
    && mkdir -p /data && chown spring:spring /data
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN chown spring:spring app.jar
USER spring:spring
EXPOSE 8086
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]
