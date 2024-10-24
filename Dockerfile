# Dockerfile
FROM openjdk:17-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} 5ARCTIC3-G1-EspritHub.jar
ENTRYPOINT ["java", "-jar", "5ARCTIC3-G1-EspritHub.jar"]