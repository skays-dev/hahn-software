FROM eclipse-temurin:17

WORKDIR /app

COPY target/Hahn-software-project-0.0.1-SNAPSHOT.jar /app/Hahn-software-project.jar

EXPOSE 8080
 
ENTRYPOINT ["java", "-jar", "Hahn-software-project.jar", "--spring.profiles.active=docker"]
