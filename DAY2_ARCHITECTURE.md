# Day 2 Architecture: Service Boundaries, Database Schema, API Contracts

## Repository
`AI-Powered-Customer-Support-Ticketing-System` — intelligent ticketing with Google Gemini AI

## Proposed Concept
Intelligent support platform with AI chatbot (RAG), sentiment analysis, auto-routing, SLA tracking, knowledge base, customer self-service portal, and agent workspace.

## Service Boundaries
| Service | Responsibility | Technology |
|---------|---------------|------------|
| Ticket Service | Ticket CRUD, state machine, assignment, comments, attachments | Spring Boot 3.2, JPA, PostgreSQL |
| AI Service | Ticket categorization, sentiment analysis, response suggestions, RAG | Spring WebFlux, Google Gemini |
| Knowledge Base Service | Article management, search, versioning, publishing | Spring Boot, PostgreSQL |
| Notification Service | Email, SMS, push notifications for ticket events | Spring Boot, SMTP/Twilio/Firebase |
| SLA Service | Response/resolution timers, breach detection, escalation | Spring Boot, scheduled tasks |
| Frontend | React dashboard, ticket workspace, self-service portal | React 18, Axios, custom CSS |

## Database Schema (PostgreSQL)
- `users` — id, email, password_hash, role (customer/agent/admin), created_at
- `tickets` — id, customer_id, agent_id, subject, description, status (open/in_progress/resolved/closed), priority, category, tags, created_at, updated_at, resolved_at
- `ticket_comments` — id, ticket_id, author_id, body, is_internal, created_at
- `attachments` — id, ticket_id, filename, mime_type, size, url, uploaded_at
- `sla_policies` — id, name, response_time_min, resolution_time_min, business_hours, escalation_rules
- `sla_timers` — id, ticket_id, policy_id, response_deadline, resolution_deadline, breached, escalated
- `knowledge_articles` — id, title, content, category, status (draft/published/archived), version, created_at, updated_at
- `satisfaction_ratings` — id, ticket_id, rating (1-5), comment, created_at

## API Contracts
### Tickets
- `POST /api/tickets` — {customerName, customerEmail, subject, description} → {ticket}
- `GET /api/tickets` → list (filtered by role)
- `GET /api/tickets/{id}` → {ticket, comments, attachments}
- `PUT /api/tickets/{id}/status?status=RESOLVED` → {ticket}
- `PUT /api/tickets/{id}/assign?agentId={id}` → {ticket}
- `POST /api/tickets/{id}/comments` — {body, isInternal} → {comment}
- `POST /api/tickets/{id}/attachments` — {file} → {attachment}

### AI
- `POST /api/ai/categorize` — {ticketId} → {category, confidence}
- `POST /api/ai/sentiment` — {ticketId} → {sentiment, score}
- `POST /api/ai/suggest-response` — {ticketId} → {suggestedText}
- `POST /api/ai/rag-query` — {question, documentIds} → {answer, sources}

### Knowledge Base
- `GET /api/kb/articles` → list
- `POST /api/kb/articles` → {article}
- `GET /api/kb/articles/{id}` → {article}
- `PUT /api/kb/articles/{id}` → {article}
- `DELETE /api/kb/articles/{id}` → 204
- `GET /api/kb/search?q={query}` → list

### SLA
- `GET /api/sla/timers` → list
- `GET /api/sla/breaches` → list
- `POST /api/sla/escalate` — {ticketId} → 204

## Technical Notes
- H2 for development, PostgreSQL for production — schema alignment needed
- Google Gemini API key required; no sandbox/mock mode yet
- No Dockerfile for backend; containerization planned
- No authentication/authorization yet — RBAC planned for Day 2+
- No SLA tracking yet — timers and escalation planned

## Day 2 Outcome
Architecture design documented. No code changes. 0 PRs. Ready for Day 3 (CI/CD pipeline).