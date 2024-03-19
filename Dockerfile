FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install -y maven
COPY amazon-corretto-17.0.10.8.1-linux-x64.tar.gz /tmp/
RUN tar -xf /tmp/amazon-corretto-17.0.10.8.1-linux-x64.tar.gz -C /opt/
ENV JAVA_HOME /opt/amazon-corretto-17.0.10.8.1-linux-x64
ENV PATH $PATH:$JAVA_HOME/bin
COPY . .

RUN mvn clean package

FROM eclipse-temurin:17-jdk
EXPOSE 8080

COPY --from=build /target/code-snippet-manager-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]