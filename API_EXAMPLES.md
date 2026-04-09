# API Examples

## Create a Ticket

```bash
curl -X POST http://localhost:8080/api/tickets \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "John Doe",
    "customerEmail": "john@example.com",
    "subject": "Cannot login to my account",
    "description": "I have been trying to login for the past hour but keep getting an error message. This is very frustrating!"
  }'
```

Response:
```json
{
  "id": 1,
  "customerName": "John Doe",
  "customerEmail": "john@example.com",
  "subject": "Cannot login to my account",
  "description": "I have been trying to login for the past hour...",
  "category": "TECHNICAL",
  "status": "OPEN",
  "sentiment": "FRUSTRATED",
  "aiSuggestedResponse": "We sincerely apologize for the login issues you're experiencing. Our technical team is investigating this immediately. Please try clearing your browser cache or using a different browser while we resolve this.",
  "createdAt": "2026-04-09T10:30:00",
  "updatedAt": "2026-04-09T10:30:00"
}
```

## Get All Tickets

```bash
curl http://localhost:8080/api/tickets
```

## Get Ticket by ID

```bash
curl http://localhost:8080/api/tickets/1
```

## Update Ticket Status

```bash
curl -X PUT "http://localhost:8080/api/tickets/1/status?status=IN_PROGRESS"
```

## Example Tickets for Testing

### Angry Customer (Billing Issue)
```json
{
  "customerName": "Sarah Smith",
  "customerEmail": "sarah@example.com",
  "subject": "Charged twice for subscription",
  "description": "I was charged $99 twice this month! This is unacceptable. I want a refund immediately!"
}
```

### Happy Customer (Feature Request)
```json
{
  "customerName": "Mike Johnson",
  "customerEmail": "mike@example.com",
  "subject": "Love the new dashboard!",
  "description": "The new dashboard is amazing! Would love to see dark mode added as well."
}
```

### Neutral Customer (General Query)
```json
{
  "customerName": "Emily Brown",
  "customerEmail": "emily@example.com",
  "subject": "Question about pricing plans",
  "description": "Can you explain the difference between the Pro and Enterprise plans?"
}
```
