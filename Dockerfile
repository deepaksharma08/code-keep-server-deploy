FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install eclipse-temurin-17-jdk -y
COPY . .

RUN  ./mvnw package

FROM eclipse-temurin:17-jdk
EXPOSE 8080

COPY --from=target /target/code-snippet-manager-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]