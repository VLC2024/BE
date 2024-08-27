#FROM ubuntu:latest
#LABEL authors="solor"
#
#ENTRYPOINT ["top", "-b"]

FROM openjdk:17-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]