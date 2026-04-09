# Demo Guide - AI-Powered Ticketing System

## Quick Demo

### Prerequisites
- Backend running on http://localhost:8080
- Frontend running on http://localhost:3000

### Step 1: Install Demo Dependencies

```bash
npm install
```

### Step 2: Test Single Ticket (Quick Test)

```bash
npm test
```

This will:
- Create one test ticket
- Show the AI analysis results in the console
- Verify the system is working

### Step 3: Generate Demo Data (Full Demo)

```bash
npm run demo
```

This will:
- Create 10 diverse sample tickets
- Each ticket will be analyzed by Google Gemini AI
- Show real-time progress in the console
- Display summary statistics

**Note:** The demo takes about 20 seconds (2 seconds between each ticket to avoid API rate limits)

## Sample Tickets Included

The demo creates tickets with various scenarios:

1. **Angry Customer** - Login issues (TECHNICAL, FRUSTRATED)
2. **Billing Issue** - Double charge (BILLING, ANGRY)
3. **Happy Customer** - Feature appreciation (GENERAL, HAPPY)
4. **General Inquiry** - Pricing questions (GENERAL, NEUTRAL)
5. **Bug Report** - App crashes (BUG_REPORT, FRUSTRATED)
6. **Feature Request** - API documentation (FEATURE_REQUEST, NEUTRAL)
7. **Account Issue** - Locked account (ACCOUNT, FRUSTRATED)
8. **Feature Request** - Excel export (FEATURE_REQUEST, NEUTRAL)
9. **Positive Feedback** - Thank you message (GENERAL, HAPPY)
10. **Sales Inquiry** - Upgrade request (GENERAL, SATISFIED)

## What to Test

### 1. AI Categorization
- Check if tickets are correctly categorized
- Categories: TECHNICAL, BILLING, GENERAL, FEATURE_REQUEST, BUG_REPORT, ACCOUNT, OTHER

### 2. Sentiment Analysis
- Verify sentiment detection accuracy
- Sentiments: ANGRY, FRUSTRATED, NEUTRAL, SATISFIED, HAPPY

### 3. AI-Generated Responses
- Review the suggested responses
- Check if they're professional and contextual

### 4. Frontend Features
- View all tickets in the list
- Click on a ticket to see details
- Update ticket status
- Check responsive design on mobile

### 5. Performance
- Check caching (create duplicate tickets - should be faster)
- Monitor response times

## Manual Testing

You can also test manually through the frontend:

1. Go to http://localhost:3000
2. Click "Create Ticket"
3. Fill in the form with different scenarios:

**Test Case 1: Angry Customer**
```
Name: John Doe
Email: john@example.com
Subject: This is ridiculous!
Description: I've been waiting for 3 days for a response. Your service is terrible and I want my money back immediately!
```

**Test Case 2: Happy Customer**
```
Name: Jane Smith
Email: jane@example.com
Subject: Amazing product!
Description: I just wanted to say your product has transformed our workflow. The team loves it and we're seeing great results. Thank you!
```

**Test Case 3: Technical Issue**
```
Name: Bob Johnson
Email: bob@example.com
Subject: Error 500 when saving
Description: Every time I try to save my work, I get an Error 500. I'm using Chrome on Mac. Can you help?
```

## Expected Results

### Console Output Example:
```
[1/10] Creating ticket: "Cannot login to my account"
   Customer: Sarah Johnson
   ✅ Created successfully!
   📋 Ticket ID: #1
   🏷️  Category: TECHNICAL
   😊 Sentiment: FRUSTRATED
   💬 AI Response: "We sincerely apologize for the login issues..."
```

### Frontend Display:
- Tickets appear in a grid layout
- Color-coded status badges
- Category and sentiment tags
- AI-generated responses visible in details view

## Troubleshooting

### "Backend is not running" Error
```bash
# Start backend
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

### "Module not found" Error
```bash
# Install dependencies
npm install
```

### Slow AI Responses
- Normal: Google Gemini API takes 1-2 seconds per request
- First request might be slower (cold start)
- Subsequent identical requests are cached (instant)

### No Tickets Showing in Frontend
- Refresh the page
- Check browser console for errors
- Verify backend is running: http://localhost:8080/api/tickets

## API Testing with cURL

You can also test the API directly:

```bash
# Create a ticket
curl -X POST http://localhost:8080/api/tickets \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Test User",
    "customerEmail": "test@example.com",
    "subject": "Test Subject",
    "description": "Test description"
  }'

# Get all tickets
curl http://localhost:8080/api/tickets

# Get specific ticket
curl http://localhost:8080/api/tickets/1

# Update ticket status
curl -X PUT "http://localhost:8080/api/tickets/1/status?status=RESOLVED"
```

## Demo Video Script

If you're recording a demo:

1. **Introduction** (30 sec)
   - Show the landing page
   - Explain the AI-powered features

2. **Create Ticket** (1 min)
   - Fill in a frustrated customer ticket
   - Submit and show AI analysis

3. **View Tickets** (1 min)
   - Show the ticket list
   - Highlight different categories and sentiments

4. **Ticket Details** (1 min)
   - Open a ticket
   - Show AI-generated response
   - Update status

5. **Run Demo Script** (30 sec)
   - Show console output
   - Refresh frontend to show all tickets

## Next Steps

After testing:
- Customize AI prompts in `GoogleAIService.java`
- Add more ticket categories
- Implement email notifications
- Add user authentication
- Deploy to production

## Support

If you encounter issues:
1. Check both backend and frontend are running
2. Verify Google AI API key is configured
3. Check browser console for errors
4. Review backend logs for exceptions
