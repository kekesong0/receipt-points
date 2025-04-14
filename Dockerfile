# -------- Stage 1: Build Jar --------
FROM gradle:8.5-jdk17 AS build

WORKDIR /app

# Copy project files into the container
COPY . .

# Use Gradle Wrapper to build the project, skipping tests
RUN ./gradlew clean build -x test

# -------- Stage 2: Run Jar --------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080 to the host
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
