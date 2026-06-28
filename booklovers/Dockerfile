# ==========================
# Stage 1: Build the project
# ==========================
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# ==========================
# Stage 2: Run the application
# ==========================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the generated JAR
COPY --from=build /app/target/booklovers-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]