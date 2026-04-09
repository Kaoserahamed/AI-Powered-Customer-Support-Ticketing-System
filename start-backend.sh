#!/bin/bash

echo "🚀 Starting AI Ticketing System Backend..."
echo ""
echo "Prerequisites:"
echo "1. PostgreSQL should be running on localhost:5432"
echo "2. Database 'ticketing_db' should exist"
echo "3. OPENAI_API_KEY environment variable should be set"
echo ""

# Check if OPENAI_API_KEY is set
if [ -z "$OPENAI_API_KEY" ]; then
    echo "⚠️  WARNING: OPENAI_API_KEY is not set!"
    echo "Please set it: export OPENAI_API_KEY=your-api-key"
    echo ""
fi

# Start PostgreSQL with Docker if docker-compose.yml exists
if [ -f "docker-compose.yml" ]; then
    echo "📦 Starting PostgreSQL with Docker..."
    docker-compose up -d
    echo "Waiting for PostgreSQL to be ready..."
    sleep 5
fi

echo "🔨 Building application..."
mvn clean install -DskipTests

echo ""
echo "🎯 Starting Spring Boot application..."
mvn spring-boot:run
