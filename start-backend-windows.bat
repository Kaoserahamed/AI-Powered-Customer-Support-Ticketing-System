@echo off
echo ========================================
echo Starting AI Ticketing System Backend
echo ========================================
echo.

REM Check if OPENAI_API_KEY is set
if "%OPENAI_API_KEY%"=="" (
    echo.
    echo ========================================
    echo WARNING: OPENAI_API_KEY is not set!
    echo ========================================
    echo.
    echo To use AI features, please set your OpenAI API key:
    echo   set OPENAI_API_KEY=sk-your-api-key-here
    echo.
    echo For now, starting with H2 in-memory database...
    echo You can test the app structure without AI features.
    echo.
    pause
)

echo Starting backend with H2 database...
echo Backend will be available at: http://localhost:8080
echo H2 Console: http://localhost:8080/h2-console
echo.

C:\Users\Asus\Downloads\apache-maven-3.9.14\bin\mvn.cmd spring-boot:run "-Dspring-boot.run.profiles=h2"
