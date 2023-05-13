FROM eclipse-temurin:17-jdk-focal
ADD target/*.jar factory-0.0.1-SNAPSHOT.jar
EXPOSE 8802
ENTRYPOINT ["java","-jar","factory-0.0.1-SNAPSHOT.jar"]