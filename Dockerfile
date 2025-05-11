# Stage 1: Build com Maven + Temurin 21
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime com OpenJDK 21
FROM openjdk:21-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar

# Expõe a porta da aplicação (sem host:container)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "AppointmentSchedulingApplication.jar"]
