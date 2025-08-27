#!/bin/bash

# Render.com build script for Spring Boot application in subdirectory
echo "🚀 Starting build process..."
echo "📁 Current directory: $(pwd)"
echo "📂 Directory contents: $(ls -la)"

# Check if we're already in smsystem-backend or need to navigate
if [ -f "pom.xml" ]; then
    echo "✅ Found pom.xml in current directory"
else
    echo "📁 Navigating to smsystem-backend directory..."
    cd smsystem-backend
    if [ ! -f "pom.xml" ]; then
        echo "❌ pom.xml not found in smsystem-backend directory!"
        echo "📂 Current directory contents: $(ls -la)"
        exit 1
    fi
fi

# Make mvnw executable
chmod +x ./mvnw

# Clean and build the application
echo "📦 Building Spring Boot application..."
./mvnw clean package -DskipTests -B

echo "✅ Build completed successfully!"
echo "JAR file location: $(ls -la target/*.jar)"

# Check if JAR file was created
if [ -f "target/smsystem-0.0.1-SNAPSHOT.jar" ]; then
    echo "✅ JAR file created successfully!"
    echo "📏 JAR file size: $(du -h target/smsystem-0.0.1-SNAPSHOT.jar)"
else
    echo "❌ JAR file not found!"
    echo "📂 Target directory contents: $(ls -la target/ || echo 'Target directory not found')"
    exit 1
fi
