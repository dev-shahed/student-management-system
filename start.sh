#!/bin/bash

# Render.com start script for Spring Boot application in subdirectory
echo "🚀 Starting Student Management System..."
echo "📁 Current directory: $(pwd)"
echo "📂 Directory contents: $(ls -la)"

# Check if we're already in smsystem-backend or need to navigate
if [ -f "target/smsystem-0.0.1-SNAPSHOT.jar" ]; then
    echo "✅ Found JAR file in current directory"
elif [ -f "smsystem-backend/target/smsystem-0.0.1-SNAPSHOT.jar" ]; then
    echo "📁 Navigating to smsystem-backend directory..."
    cd smsystem-backend
else
    echo "❌ JAR file not found! Checking for build..."
    if [ -f "pom.xml" ]; then
        echo "🔨 Found pom.xml, building application..."
        chmod +x ./mvnw
        ./mvnw clean package -DskipTests -B
    elif [ -f "smsystem-backend/pom.xml" ]; then
        echo "🔨 Found pom.xml in subdirectory, building application..."
        cd smsystem-backend
        chmod +x ./mvnw
        ./mvnw clean package -DskipTests -B
    else
        echo "❌ No pom.xml found! Cannot build application."
        exit 1
    fi
fi

# Final check for JAR file
if [ ! -f "target/smsystem-0.0.1-SNAPSHOT.jar" ]; then
    echo "❌ JAR file still not found after build attempt!"
    echo "📂 Target directory contents: $(ls -la target/ || echo 'Target directory not found')"
    exit 1
fi

# Set JVM options for better performance on limited resources
export JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

# Start the application
echo "🎯 Starting Spring Boot application on port ${PORT:-8080}..."
echo "🌐 Health check available at: /api/auth/health"
echo "📚 Swagger UI available at: /swagger-ui/index.html"

exec java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar target/smsystem-0.0.1-SNAPSHOT.jar
