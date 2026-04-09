#!/bin/bash

echo "🎨 Starting AI Ticketing System Frontend..."
echo ""

cd frontend

# Check if node_modules exists
if [ ! -d "node_modules" ]; then
    echo "📦 Installing dependencies..."
    npm install
fi

echo ""
echo "🚀 Starting React development server..."
echo "Frontend will be available at: http://localhost:3000"
echo ""

npm start
