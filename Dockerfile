FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim AS runtime
WORKDIR /app
COPY --from=build /app/target/AppointmentSchedulingApplication.jar ./AppointmentSchedulingApplication.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "AppointmentSchedulingApplication.jar"]
