FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY target/*.jar /app/main-service.jar
ENTRYPOINT ["java","-jar","/app/main-service.jar"]