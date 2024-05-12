# Dockerfile
FROM openjdk:17-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} EspritHub.jar
ENTRYPOINT ["java", "-jar", "app.jar"]