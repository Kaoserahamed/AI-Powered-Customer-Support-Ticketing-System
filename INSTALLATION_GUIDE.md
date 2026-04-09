# Complete Installation Guide

## Required Software

### 1. Install Java 17 or Higher

**Windows:**
- Download from: https://adoptium.net/
- Install and verify: `java -version`

**Linux:**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

**Mac:**
```bash
brew install openjdk@17
```

### 2. Install Maven

**Windows:**
- Download from: https://maven.apache.org/download.cgi
- Extract and add to PATH
- Verify: `mvn -version`

**Linux:**
```bash
sudo apt install maven
```

**Mac:**
```bash
brew install maven
```

### 3. Install Node.js (for Frontend)

**All Platforms:**
- Download from: https://nodejs.org/ (LTS version)
- Verify: `node -version` and `npm -version`

### 4. Get OpenAI API Key

1. Go to: https://platform.openai.com/
2. Sign up or login
3. Navigate to API Keys section
4. Create new API key
5. Copy and save it securely

## Database Options

### Option A: H2 In-Memory Database (Easiest - No Installation)

Use the H2 profile (data resets on restart):

**Windows:**
```bash
set OPENAI_API_KEY=sk-your-api-key
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

**Linux/Mac:**
```bash
export OPENAI_API_KEY=sk-your-api-key
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

### Option B: PostgreSQL with Docker

**Install Docker:**
- Windows/Mac: https://www.docker.com/products/docker-desktop
- Linux: `sudo apt install docker.io docker-compose`

**Start Database:**
```bash
docker compose up -d
```

### Option C: PostgreSQL Direct Installation

**Windows:**
- Download from: https://www.postgresql.org/download/windows/

**Linux:**
```bash
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
```

**Mac:**
```bash
brew install postgresql
brew services start postgresql
```

**Create Database:**
```sql
psql -U postgres
CREATE DATABASE ticketing_db;
\q
```

## Running the Application

### Step 1: Set Environment Variable

**Windows (PowerShell):**
```powershell
$env:OPENAI_API_KEY="sk-your-actual-api-key-here"
```

**Windows (CMD):**
```cmd
set OPENAI_API_KEY=sk-your-actual-api-key-here
```

**Linux/Mac:**
```bash
export OPENAI_API_KEY=sk-your-actual-api-key-here
```

### Step 2: Start Backend

**With H2 (No PostgreSQL needed):**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

**With PostgreSQL:**
```bash
mvn spring-boot:run
```

Wait for: `Started TicketingApplication in X seconds`

### Step 3: Start Frontend (New Terminal)

```bash
cd frontend
npm install
npm start
```

Browser will open automatically at: http://localhost:3000

## Verify Installation

### Backend Health Check:
```bash
curl http://localhost:8080/api/tickets
```

Should return: `[]` (empty array)

### Frontend:
Open: http://localhost:3000

### H2 Console (if using H2):
Open: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:ticketing_db`
- Username: `sa`
- Password: (leave empty)

## Testing the Application

1. Go to http://localhost:3000
2. Click "Create Ticket"
3. Fill in:
   - Name: John Doe
   - Email: john@example.com
   - Subject: Cannot login to my account
   - Description: I've been trying to login for an hour. Very frustrating!
4. Click "Submit Ticket"
5. AI will analyze and show:
   - Category: TECHNICAL
   - Sentiment: FRUSTRATED
   - Suggested Response

## Troubleshooting

### "OPENAI_API_KEY not set"
- Make sure you set the environment variable in the same terminal where you run mvn
- Restart terminal after setting

### "Port 8080 already in use"
- Change port in `application.yml`: `server.port: 8081`
- Update frontend proxy in `frontend/package.json`

### "Cannot connect to database"
- If using PostgreSQL, ensure it's running
- Use H2 profile instead: `-Dspring-boot.run.profiles=h2`

### Frontend won't start
- Delete `node_modules` folder
- Run `npm install` again
- Check Node.js version: `node -version` (should be 16+)

### OpenAI API Errors
- Verify API key is correct
- Check you have credits: https://platform.openai.com/usage
- Free tier has rate limits

## Project Structure

```
ai-ticketing-system/
├── src/main/java/com/support/     # Backend code
│   ├── entity/                     # Database models
│   ├── dto/                        # Request/Response objects
│   ├── repository/                 # Data access
│   ├── service/                    # Business logic
│   │   ├── AIService.java         # AI integration
│   │   └── TicketService.java     # Ticket management
│   ├── controller/                 # REST API
│   └── config/                     # Configuration
├── frontend/                       # React frontend
│   ├── src/
│   │   ├── components/            # React components
│   │   ├── App.js                 # Main app
│   │   └── index.js               # Entry point
│   └── package.json
├── pom.xml                         # Maven dependencies
└── docker-compose.yml              # PostgreSQL setup
```

## Next Steps

1. Customize AI prompts in `AIService.java`
2. Add authentication (Spring Security + JWT)
3. Deploy to cloud (Heroku, AWS, Azure)
4. Add email notifications
5. Create admin dashboard

## Support

- Spring Boot Docs: https://spring.io/projects/spring-boot
- Spring AI Docs: https://docs.spring.io/spring-ai/reference/
- React Docs: https://react.dev/
- OpenAI API: https://platform.openai.com/docs
