# Quick Start Guide

## Prerequisites

1. Java 17+
2. Node.js 16+
3. Docker (for PostgreSQL)
4. OpenAI API Key

## Setup in 3 Steps

### Step 1: Set API Key

**Windows:**
```bash
set OPENAI_API_KEY=sk-your-api-key-here
```

**Linux/Mac:**
```bash
export OPENAI_API_KEY=sk-your-api-key-here
```

### Step 2: Start Database

```bash
docker-compose up -d
```

### Step 3: Run Application

**Option A - Automatic (Windows):**
```bash
start-all.bat
```

**Option B - Manual:**

Terminal 1 (Backend):
```bash
mvn spring-boot:run
```

Terminal 2 (Frontend):
```bash
cd frontend
npm install
npm start
```

## Access Application

- Frontend: http://localhost:3000
- Backend API: http://localhost:8080/api/tickets

## Test the System

1. Open http://localhost:3000
2. Click "Create Ticket"
3. Fill in the form with a sample issue
4. Submit and watch AI analyze it!
5. Click "View Tickets" to see all tickets

## Sample Test Data

Try creating a ticket with:
- Subject: "Cannot login to my account"
- Description: "I've been trying to login for an hour but keep getting errors. This is very frustrating!"

The AI will:
- Categorize it as TECHNICAL
- Detect FRUSTRATED sentiment
- Generate a professional response

## Troubleshooting

**Port 8080 already in use?**
- Change port in `src/main/resources/application.yml`

**Database connection error?**
- Ensure Docker is running: `docker ps`
- Check PostgreSQL is up: `docker-compose logs`

**OpenAI API error?**
- Verify API key is set correctly
- Check you have credits: https://platform.openai.com/usage
