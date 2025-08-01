# Use lightweight JDK image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy the built JAR file into container
COPY target/*.jar app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
