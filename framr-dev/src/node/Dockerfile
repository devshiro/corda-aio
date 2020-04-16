FROM openjdk:8-jdk-alpine
COPY . /app
EXPOSE 10002 11000 10003
WORKDIR /app
ENTRYPOINT ["java","-jar","corda.jar"]