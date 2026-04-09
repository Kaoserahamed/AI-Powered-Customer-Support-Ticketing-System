@echo off
echo ========================================
echo AI-Powered Ticketing System Launcher
echo ========================================
echo.

REM Check if OPENAI_API_KEY is set
if "%OPENAI_API_KEY%"=="" (
    echo WARNING: OPENAI_API_KEY is not set!
    echo Please set it: set OPENAI_API_KEY=your-api-key
    echo.
    pause
)

echo Starting PostgreSQL with Docker...
docker-compose up -d
timeout /t 5 /nobreak > nul

echo.
echo Starting Backend (Spring Boot)...
start "Backend Server" cmd /k "mvn spring-boot:run"

echo.
echo Waiting for backend to start...
timeout /t 15 /nobreak > nul

echo.
echo Starting Frontend (React)...
cd frontend
if not exist "node_modules" (
    echo Installing frontend dependencies...
    call npm install
)
start "Frontend Server" cmd /k "npm start"

echo.
echo ========================================
echo Both servers are starting!
echo Backend: http://localhost:8080
echo Frontend: http://localhost:3000
echo ========================================
echo.
echo Press any key to exit this window...
pause > nul
