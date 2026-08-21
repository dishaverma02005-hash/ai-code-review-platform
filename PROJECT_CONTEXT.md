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
Day 2 — COMPLETE

## Completed Features
- Spring Boot 3.5.6 project created with Maven
- Java 25 configured in pom.xml
- Main application class: AiCodeReviewPlatformApplication.java
- HealthController exposing GET /api/health
- application.yml configured (port 8080, app name, PostgreSQL datasource)
- .gitignore in place (Maven, IDE, OS, secrets)
- Health endpoint verified working
- Git initialized locally
- First commit made: c89f4a7
- PostgreSQL installed locally (port 5432, password 'postgres')
- Database `ai_code_review` and user `appuser` created
- spring-boot-starter-data-jpa and postgresql JDBC driver added to pom.xml
- User JPA entity created with id, username, email, passwordHash, createdAt
- UserRepository interface extending JpaRepository
- UserController exposing GET /api/users (returns all users as JSON)
- Hibernate auto-created `users` table on startup
- /api/health verified: 200 {"status":"UP","message":"Backend is running"}
- /api/users verified: 200 []

## Remaining Features
See 14-day plan above.

## Current Folder Structure
ai-code-review-platform/
├── .git, .gitignore, pom.xml, PROJECT_CONTEXT.md
├── src/main/java/com/aicodereview/platform/
│   ├── AiCodeReviewPlatformApplication.java
│   ├── health/HealthController.java
│   └── user/
│       ├── User.java
│       ├── UserRepository.java
│       └── UserController.java
├── src/main/resources/application.yml
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
- DB user: appuser / app_password_123 (dev only)
- Hibernate ddl-auto: update (auto-creates tables, never drops data)

## Commands Reference

### Run the backend
mvn spring-boot:run

### Compile only (no run)
mvn -DskipTests compile

### Test endpoints
Invoke-WebRequest -Uri http://localhost:8080/api/health -Method GET
Invoke-WebRequest -Uri http://localhost:8080/api/users  -Method GET

### Stop the server
Press Ctrl + C in the terminal where the server is running.

### Git basics
- git status (see what changed)
- git add . (stage all changes)
- git commit -m 'message' (commit staged changes)
- git log --oneline (see commit history)
- git push -u origin master (push to GitHub)

## Errors and Solutions

### Problem: mvn dependency:resolve failed with MissingProjectException
Cause: No pom.xml yet.
Solution: Created pom.xml in STEP 5.

### Problem: target/ files were staged in git add
Cause: target/ existed before .gitignore was added.
Solution: Ran git rm -r --cached target/ to untrack them.

### Problem: could not connect to PostgreSQL on startup
Cause: Wrong password, or database/user not yet created.
Solution: Re-ran the CREATE DATABASE / CREATE USER / GRANT commands in SQL Shell (psql).

## Git / GitHub Status
- Git installed: yes (2.53.0)
- Local repo: initialized
- First commit: c89f4a7
- Day 2 commit: pending (next step)

## Next Task
Day 3 — Registration + login + JWT
- POST /api/users/register (hash password with BCrypt, save to DB)
- POST /api/users/login   (verify password, return JWT)
- Add spring-boot-starter-security + jjwt dependencies
- Configure a SecurityFilterChain
- Protect GET /api/users behind authentication

---

NEVER store passwords, API keys, JWT secrets, or DB credentials in this file.
