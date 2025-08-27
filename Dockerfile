# Multi-stage build for optimal image size
FROM openjdk:12-jdk-slim AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (for better caching)
COPY smsystem-backend/pom.xml ./
COPY smsystem-backend/mvnw ./
COPY smsystem-backend/mvnw.cmd ./
COPY smsystem-backend/.mvn ./.mvn

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY smsystem-backend/src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM openjdk:12-jre-slim

# Set working directory
WORKDIR /app

# Create non-root user for security
RUN groupadd -r springuser && useradd --no-log-init -r -g springuser springuser

# Copy JAR file from builder stage
COPY --from=builder /app/target/smsystem-0.0.1-SNAPSHOT.jar app.jar

# Change ownership to non-root user
RUN chown springuser:springuser app.jar

# Switch to non-root user
USER springuser

# Expose port (Railway will set PORT env var)
EXPOSE $PORT

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:$PORT/api/auth/health || exit 1

# Run the application
ENTRYPOINT ["java", "-Dserver.port=${PORT:-8080}", "-jar", "app.jar"]
