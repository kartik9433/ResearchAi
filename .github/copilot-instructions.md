# ResearchAI Coding Instructions

## Project Overview
ResearchAI is a full-stack chatbot application built with:
- Frontend: React + Vite
- Backend: Spring Boot
- API Integration: Google's Gemini AI

## Architecture

### Frontend (React + Vite)
- Entry point: `src/main.jsx`
- Main component hierarchy:
  - `App.jsx` → `Screen/Screen.jsx` → `Message/Message.jsx` + `Response/Response.jsx`
- Components are organized in `src/components/` with co-located CSS files

### Backend (Spring Boot)
- Core package: `chatbot.example.Chatbot`
- Layered architecture:
  - Controller (`ChatbotController.java`): Handles HTTP requests at `/chatbot/ask`
  - Service (`ChatbotService.java`): Manages Gemini API integration
  - Repository (`ChatbotRepository.java`): Data persistence layer
  - Model (`Message.java`): Data structures

## Key Integration Points
1. Frontend-Backend Communication:
   - Backend CORS configured for `http://localhost:5173` (Vite dev server)
   - API endpoint: POST `/chatbot/ask`
   - Request format: `{ "userMessage": "string" }`

2. Gemini AI Integration:
   - API endpoint: `https://generativelanguage.googleapis.com/v1beta/models`
   - Authentication: API key from `application.properties`
   - Model: `gemini-2.5-flash`

## Development Workflow
1. Start the backend:
   ```
   ./mvnw spring-boot:run
   ```
2. Start the frontend (in separate terminal):
   ```
   npm run dev
   ```

## Configuration
- Backend configuration: `src/main/resources/application.properties`
  - Required: `gemini.api.key` for Gemini API access
- Frontend dev server: `vite.config.js`

## Testing
- Backend tests location: `src/test/java/chatbot/example/Chatbot/`
- Run tests: `./mvnw test`

## Conventions
- Component organization: Each React component has its own directory with co-located CSS
- API error handling: Service layer handles Gemini API errors with specific error messages
- Environment variables: Use `application.properties` for backend, `.env` for frontend