FROM openjdk:8-jdk-alpine
EXPOSE 8090
COPY target/*.jar /app.jar
CMD java -jar /app.jar
