# Focus Learn

Focus Learn is a distraction-free educational platform for curated learning journeys with integrated video playback, time-stamped notes, progress tracking, and forking of public journeys.

This repository contains two main folders:
- `backend/` - Spring Boot REST API (Java)
- `frontend/` - React + TypeScript SPA

Main features:
- User authentication (JWT)
- Create, edit, fork journeys composed of ordered steps (YouTube videos or articles)
- Time-stamped notes synchronized with video playback
- Progress tracking and dashboard analytics
- YouTube playlist import

Architecture:
- Frontend communicates with backend via REST; video playback is client-side direct from YouTube; backend stores metadata, notes, and progress.

See each folder's README for run instructions.

## Quick start (Docker)

1. Build backend jar:

	mvn -f backend clean package -DskipTests

2. Start services:

```powershell
docker-compose up --build
```

- Frontend: http://localhost:3000
- Backend: http://localhost:8080

## Tests
- Backend: mvn -f backend test
- Frontend: cd frontend && npm test

Notes:
- Set a secure `APP_JWT_SECRET` env var for production.
- Some controllers currently parse Principal.getName() as UUID; map your security principal to user id in `SecurityUserDetailsService` for production.
