# Day 3 CI/CD: Build / Test / Deploy Pipeline

## Repository
`AI-Powered-Customer-Support-Ticketing-System` — intelligent ticketing with Google Gemini AI

## Pipeline Overview
GitHub Actions CI/CD pipeline for Spring Boot 3.2 + React 18 application.

### Trigger
- Push/Pull Request to `main`
- Manual workflow dispatch

### Stages

#### 1. Lint & Format
- Backend: Checkstyle / Spotless (Maven plugin)
- Frontend: ESLint + Prettier

#### 2. Test
- Backend: `mvn test` (JUnit 5, Spring Boot test)
- Frontend: `npm test` (React test suite)
- Coverage: JaCoCo (backend), Jest/Vitest coverage (frontend)

#### 3. Build
- Backend: `mvn clean package` (produces `ai-ticketing-system-1.0.0.jar`)
- Frontend: `npm run build` (Vite production build)
- Docker image: `docker build -t ai-ticketing-system .`

#### 4. Security Scan
- Dependency vulnerability check (`mvn dependency:check`, `npm audit`)
- No Google AI API keys committed
- Secret detection

#### 5. Deploy (on merge to `main`)
- Build and push Docker image
- Deploy with `docker-compose up -d` (PostgreSQL + app)
- Health check: `GET /api/tickets` and H2 console

### Quality Gates
- All unit tests pass
- Build artifacts produced successfully
- No hardcoded secrets

### Artifacts
- JAR file (`target/ai-ticketing-system-1.0.0.jar`)
- Docker image
- Test reports (JaCoCo XML, frontend coverage)

## Day 3 Outcome
CI/CD pipeline documented. No code changes. 0 PRs. Ready for Day 4 (project restructuring).
<!-- Feature branch commit for PR -->
