# Render.com optimized Dockerfile for subdirectory project
FROM openjdk:12-jdk-slim

# Set working directory
WORKDIR /app

# Install curl for health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the Spring Boot project files
COPY smsystem-backend/pom.xml ./
COPY smsystem-backend/mvnw ./
COPY smsystem-backend/mvnw.cmd ./
COPY smsystem-backend/.mvn ./.mvn

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies (for better Docker layer caching)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY smsystem-backend/src ./src

# Build the application
RUN ./mvnw clean package -DskipTests -B

# Expose port (Render will set PORT env var)
EXPOSE $PORT

# Health check endpoint
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:${PORT:-8080}/api/auth/health || exit 1

# Run the application
CMD ["java", "-Dserver.port=${PORT:-8080}", "-jar", "target/smsystem-0.0.1-SNAPSHOT.jar"]
