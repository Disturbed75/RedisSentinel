FROM eclipse-temurin:17-jdk-jammy
EXPOSE 8081
ARG JAR_FILE=target/spring-boot-app-1.0.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]
