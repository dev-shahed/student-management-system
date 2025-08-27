# Use OpenJDK 17
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Install curl for health check
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy Maven wrapper and project files
COPY smsystem-backend/pom.xml ./
COPY smsystem-backend/mvnw ./
COPY smsystem-backend/mvnw.cmd ./
COPY smsystem-backend/.mvn ./.mvn

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies (Docker layer caching)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY smsystem-backend/src ./src

# Build the application
RUN ./mvnw clean package -DskipTests -B

# Expose default port (Render maps PORT automatically)
EXPOSE 8080

# Health check for Render
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/api/auth/health || exit 1

# Run Spring Boot app using shell form so $PORT expands at runtime
CMD sh -c "java -Dserver.port=${PORT:-8080} -jar target/smsystem-0.0.1-SNAPSHOT.jar"
