# Run Without Docker - Quick Start

If you don't have Docker or PostgreSQL installed, you can run the application with H2 in-memory database.

## Prerequisites

1. Java 17+ installed
2. Maven installed
3. Node.js 16+ installed
4. OpenAI API Key

## Step-by-Step Instructions

### 1. Set OpenAI API Key

**Windows PowerShell:**
```powershell
$env:OPENAI_API_KEY="sk-your-actual-api-key-here"
```

**Windows CMD:**
```cmd
set OPENAI_API_KEY=sk-your-actual-api-key-here
```

**Linux/Mac:**
```bash
export OPENAI_API_KEY=sk-your-actual-api-key-here
```

### 2. Start Backend (Terminal 1)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

Wait for this message:
```
Started TicketingApplication in X seconds
```

Backend is now running at: http://localhost:8080

### 3. Start Frontend (Terminal 2 - New Window)

```bash
cd frontend
npm install
npm start
```

Frontend will open automatically at: http://localhost:3000

## Test the Application

1. Browser opens at http://localhost:3000
2. Click "Create Ticket"
3. Fill in the form:
   ```
   Name: John Doe
   Email: john@example.com
   Subject: Cannot login to my account
   Description: I've been trying to login for an hour but keep getting errors. This is very frustrating!
   ```
4. Click "Submit Ticket"
5. Watch AI analyze it:
   - Category: TECHNICAL
   - Sentiment: FRUSTRATED
   - AI generates a professional response
6. Click "View Tickets" to see all tickets

## H2 Database Console (Optional)

Access the database console at: http://localhost:8080/h2-console

- JDBC URL: `jdbc:h2:mem:ticketing_db`
- Username: `sa`
- Password: (leave empty)

## Important Notes

⚠️ **H2 is in-memory**: All data is lost when you stop the backend server. This is perfect for development and testing.

✅ **No installation needed**: H2 is included in the project dependencies.

🔄 **Fresh start**: Every time you restart the backend, you get a clean database.

## Switching to PostgreSQL Later

When you're ready to use PostgreSQL:

1. Install PostgreSQL or Docker
2. Create database: `CREATE DATABASE ticketing_db;`
3. Run without the `-Dspring-boot.run.profiles=h2` flag:
   ```bash
   mvn spring-boot:run
   ```

## Troubleshooting

### Backend won't start
- Check Java version: `java -version` (need 17+)
- Check Maven: `mvn -version`
- Ensure OPENAI_API_KEY is set in the same terminal

### Frontend won't start
- Check Node.js: `node -version` (need 16+)
- Delete `node_modules` and run `npm install` again

### OpenAI API errors
- Verify your API key is correct
- Check you have credits at: https://platform.openai.com/usage

### Port already in use
- Backend: Change port in `application-h2.yml`
- Frontend: It will prompt you to use a different port

## What's Next?

- Try different ticket types (billing, technical, feature requests)
- Check the AI-generated responses
- Update ticket statuses
- View the H2 console to see the database
- Customize the AI prompts in `AIService.java`
