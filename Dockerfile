#FROM ubuntu:latest
#LABEL authors="solor"
#
#ENTRYPOINT ["top", "-b"]
FROM openjdk:17-slim

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 애플리케이션 설정 파일을 JAR 파일과 같은 디렉토리로 복사
COPY src/main/resources/application.yml /app/config/application.yml

ENTRYPOINT ["java", "-Dspring.config.location=file:/app/config/application.yml", "-jar", "/app.jar"]
