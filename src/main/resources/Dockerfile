FROM ubuntu:latest
LABEL authors="aaron"
FROM maven:4.0.0-openjdk-21 AS build

WORKDIR /app

COPY /appointmentScheduling/pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from-build /app/target/AppointmentSchedulingApplication.jar ./AppointmentSchedulingApplication.jar

EXPOSE 8080:8080


ENTRYPOINT ["java", "-jar", "AppointmentSchedulingApplication.jar"]