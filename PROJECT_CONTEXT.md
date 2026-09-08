# PROJECT CONTEXT — AI Code Review Platform

This is the source of truth for the project. Read this first in any new conversation.

---

## Project Name

AI Code Review Platform

## Project Goal

Build a full-stack web application where a user can submit Java code and receive an automated code review.

## Technology Stack

- Backend: Java 25, Spring Boot 3.5.6, Maven 3.9.16
- Frontend: React (from Day 9)
- Database: PostgreSQL (from Day 2)
- Static Analysis: JavaParser (from Day 5)
- AI: TBD (Day 7)
- Version Control: Git + GitHub
- Containerization: Docker (if time permits)
- IDE: VS Code
- OS: Windows 11

## Package Name (Java)

com.aicodereview.platform

## 14-Day Plan

- Day 1: Spring Boot backend + health endpoint + Git/GitHub
- Day 2: PostgreSQL + JPA
- Day 3: Registration + login + JWT
- Day 4: Code submission API
- Day 5: JavaParser static analysis
- Day 6: More analysis rules + scoring
- Day 7: AI integration
- Day 8: Review APIs + backend MVP completion
- Day 9: React frontend
- Day 10: Monaco code editor
- Day 11: Review results / dashboard
- Day 12: Testing + bug fixing
- Day 13: UI polish + documentation
- Day 14: GitHub cleanup + deployment + README + resume prep

## Current Day

Day 6 — COMPLETE

## Completed Features

- Spring Boot 3.5.6 project created with Maven
- Java 25 configured in pom.xml
- Main application class: AiCodeReviewPlatformApplication.java
- HealthController exposing GET /api/health
- application.yml configured with port 8080, app name, and PostgreSQL datasource
- .gitignore in place (Maven, IDE, OS, secrets)
- Health endpoint verified working
- Git initialized locally
- PostgreSQL 18.6 installed locally on port 5432
- Database `ai_code_review` and user `appuser` created
- spring-boot-starter-data-jpa and PostgreSQL JDBC driver added to pom.xml
- User JPA entity created with id, username, email, passwordHash, createdAt
- UserRepository interface extending JpaRepository
- UserController exposing GET /api/users
- Hibernate auto-created `users` table on startup
- /api/health verified: 200 {"status":"UP","message":"Backend is running"}
- /api/users verified: 200 []
- spring-boot-starter-security, jjwt, and spring-boot-starter-validation added to pom.xml
- JwtService generates and validates HS384 JWTs (jwt.expiration = 24h)
- Auth DTOs created: RegisterRequest, LoginRequest, AuthResponse with bean validation
- AuthService hashes passwords with BCrypt and issues JWTs on register/login
- AuthController exposes POST /api/auth/register and POST /api/auth/login
- JwtAuthFilter runs on every request, reads the Bearer token, sets the security context
- SecurityConfig: public /api/auth/** and /api/health; everything else requires a valid JWT
- End-to-end tested: /api/users returns 403 without token, 200 with token; login returns a fresh token
- Submission JPA entity created with id, code, user, submittedAt
- SubmissionRepository created with custom query to find by user (ordered by date)
- SubmissionController exposes POST /api/submissions (create) and GET /api/submissions (list)
- SubmissionService handles associating the code with the authenticated user from the security context
- End-to-end tested: can submit code and retrieve it using a JWT token
- javaparser-core 3.26.2 added to pom.xml
- Created analysis package: AnalysisIssue, AnalysisResult, CodeAnalyzerService, AnalysisController
- CodeAnalyzerService parses submitted code with JavaParser and detects unused imports
- AnalysisController exposes POST /api/analysis/{submissionId}
- /api/analysis/** protected by JWT (inherits from SecurityConfig's anyRequest().authenticated())
- End-to-end tested: registered test user, submitted code with an unused import, confirmed 403 without token and 200 with token, correctly flagged "Unused import: java.util.List"
- AnalysisIssue updated with a severity field (LOW/MEDIUM/HIGH)
- AnalysisResult updated with a score field (0-100)
- ScoringService created: deducts 3/7/15 points per LOW/MEDIUM/HIGH issue, floor of 0
- CodeAnalyzerService expanded with checkLongMethods, checkNamingConvention, checkHardcodedSecrets (in addition to existing checkUnusedImports and checkEmptyCatchBlocks)
- End-to-end tested: submitted code with an unused import, empty catch block, non-camelCase method name, and a hardcoded password — all 4 issues correctly detected with matching severities (LOW, MEDIUM, LOW, HIGH), final score computed correctly as 72/100

## Remaining Features

See 14-day plan above.

## Current Folder Structure

ai-code-review-platform/

├── .git
├── .gitignore
├── pom.xml
├── PROJECT_CONTEXT.md
├── src/main/java/com/aicodereview/platform/
│   ├── AiCodeReviewPlatformApplication.java
│   ├── health/
│   │   └── HealthController.java
│   ├── user/
│   │   ├── User.java
│   │   ├── UserRepository.java
│   │   └── UserController.java
│   ├── auth/
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── AuthResponse.java
│   │   ├── AuthService.java
│   │   └── AuthController.java
│   ├── security/
│   │   ├── JwtService.java
│   │   ├── JwtAuthFilter.java
│   │   └── SecurityConfig.java
│   ├── submission/
│   │   ├── Submission.java
│   │   ├── SubmissionRepository.java
│   │   ├── SubmissionService.java
│   │   └── SubmissionController.java
│   └── analysis/
│       ├── AnalysisIssue.java
│       ├── AnalysisResult.java
│       ├── ScoringService.java
│       ├── CodeAnalyzerService.java
│       └── AnalysisController.java
├── src/main/resources/
│   └── application.yml
└── target/ (git-ignored)

## Important Technical Decisions

- Java 25 (Temurin) confirmed: 25.0.3
- Maven 3.9.16 confirmed
- Spring Boot 3.5.6 chosen
- Port 8080 for backend
- Branch name: master
- Package structure: com.aicodereview.platform
- Git user: Disha <disha02005@gmail.com>
- Database: PostgreSQL 18.6 installed locally
- DB name: ai_code_review
- DB user: appuser
- Database credentials are stored only in local configuration and are NOT stored in this project context file
- Hibernate ddl-auto: update (auto-creates tables, never drops data)
- Scoring weights: LOW = -3, MEDIUM = -7, HIGH = -15, score floor = 0

## Commands Reference

### Run the backend

```powershell
mvn spring-boot:run
```

### Stop the backend

Ctrl + C in its terminal

### Test health endpoint

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/health" -Method GET -UseBasicParsing
```

### Register a user

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/auth/register" -Method POST -UseBasicParsing -Headers @{"Content-Type"="application/json"} -Body '{"username": "yourUsername", "email": "you@example.com", "password": "YourPassword1!"}'
```

### Log in

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/auth/login" -Method POST -UseBasicParsing -Headers @{"Content-Type"="application/json"} -Body '{"username": "yourUsername", "password": "YourPassword1!"}'
```

### Submit code (requires JWT)

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/submissions" -Method POST -UseBasicParsing -Headers @{Authorization = "Bearer YOUR_TOKEN"; "Content-Type"="application/json"} -Body '{"code": "your java code here"}'
```

### Analyze a submission (requires JWT)

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/analysis/SUBMISSION_ID" -Method POST -UseBasicParsing -Headers @{Authorization = "Bearer YOUR_TOKEN"}
```

### Pretty-print an analysis response

```powershell
$result = Invoke-WebRequest -Uri "http://localhost:8080/api/analysis/SUBMISSION_ID" -Method POST -UseBasicParsing -Headers @{Authorization = "Bearer YOUR_TOKEN"}
$result.Content | ConvertFrom-Json | ConvertTo-Json -Depth 5
```

### Commit changes

```powershell
git add .
git commit -m "Day X: <what you did>"
git push
```