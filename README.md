# AI-Powered Customer Support Ticketing System

A modern, intelligent ticketing system that leverages Google Gemini AI to automatically categorize support tickets, analyze customer sentiment, and generate professional response suggestions.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)
![React](https://img.shields.io/badge/React-18.2.0-blue)
![Google AI](https://img.shields.io/badge/Google%20Gemini-AI-red)

## 🌟 Features

- **🤖 AI-Powered Analysis**: Automatic ticket categorization using Google Gemini AI
- **😊 Sentiment Detection**: Real-time customer emotion analysis (Angry, Frustrated, Neutral, Satisfied, Happy)
- **💬 Smart Responses**: AI-generated professional response suggestions for support agents
- **⚡ Performance Optimized**: Intelligent caching reduces API costs and improves response time
- **🎨 Modern UI**: Beautiful, responsive React frontend with gradient design
- **📊 Real-time Dashboard**: View and manage all tickets with status tracking
- **🔄 Status Management**: Easy ticket status updates (Open, In Progress, Resolved, Closed)

## 🏗️ Architecture

### Backend
- **Framework**: Spring Boot 3.2.0
- **AI Integration**: Google Gemini API via Spring WebFlux
- **Database**: H2 (development) / PostgreSQL (production)
- **Caching**: Spring Cache with Caffeine
- **API**: RESTful endpoints with validation

### Frontend
- **Framework**: React 18.2.0
- **HTTP Client**: Axios
- **Styling**: Custom CSS with modern gradients
- **Design**: Responsive, mobile-first approach

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Node.js 16+
- Maven 3.6+
- Google AI API Key ([Get one here](https://ai.google.dev/))

### 1. Clone the Repository
```bash
git clone https://github.com/Kaoserahamed/AI-Powered-Customer-Support-Ticketing-System.git
cd AI-Powered-Customer-Support-Ticketing-System
```

### 2. Configure Google AI API Key
Update `src/main/resources/application-h2.yml`:
```yaml
google:
  ai:
    api-key: YOUR_GOOGLE_AI_API_KEY
```

### 3. Start Backend
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

### 4. Start Frontend
```bash
cd frontend
npm install
npm start
```

### 5. Access Application
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api/tickets
- **H2 Console**: http://localhost:8080/h2-console

## 📖 Documentation

- [Installation Guide](INSTALLATION_GUIDE.md) - Detailed setup instructions
- [API Examples](API_EXAMPLES.md) - REST API usage examples
- [Demo Guide](DEMO_GUIDE.md) - How to run demo data
- [Quick Start](QUICK_START.md) - Fast setup guide
- [Bangla Guide](SETUP_GUIDE_BANGLA.md) - বাংলা সেটআপ গাইড

## 🎯 Demo

Run the demo script to create sample tickets:

```bash
# Install demo dependencies
npm install

# Create 10 sample tickets with AI analysis
npm run demo

# Or test with a single ticket
npm test
```

## 📊 API Endpoints

### Create Ticket
```bash
POST /api/tickets
Content-Type: application/json

{
  "customerName": "John Doe",
  "customerEmail": "john@example.com",
  "subject": "Cannot login to my account",
  "description": "I've been trying to login for an hour. Very frustrating!"
}
```

### Get All Tickets
```bash
GET /api/tickets
```

### Get Ticket by ID
```bash
GET /api/tickets/{id}
```

### Update Ticket Status
```bash
PUT /api/tickets/{id}/status?status=RESOLVED
```

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Backend Framework | Spring Boot 3.2.0 |
| AI Integration | Google Gemini API |
| Database | H2 / PostgreSQL |
| Caching | Spring Cache (Caffeine) |
| Frontend | React 18.2.0 |
| HTTP Client | Axios |
| Build Tool | Maven |
| Java Version | 17+ |

## 📁 Project Structure

```
├── src/main/java/com/support/
│   ├── config/          # Configuration classes
│   ├── controller/      # REST controllers
│   ├── dto/            # Data Transfer Objects
│   ├── entity/         # JPA entities
│   ├── repository/     # Data access layer
│   └── service/        # Business logic & AI integration
├── frontend/
│   ├── public/         # Static files
│   └── src/
│       ├── components/ # React components
│       ├── App.js      # Main application
│       └── index.js    # Entry point
├── demo-data.js        # Demo data generator
└── pom.xml            # Maven configuration
```

## 🔧 Configuration

### Database Configuration
**H2 (Development)**:
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:ticketing_db
    username: sa
    password:
```

**PostgreSQL (Production)**:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ticketing_db
    username: postgres
    password: your_password
```

### AI Configuration
```yaml
google:
  ai:
    api-key: ${GOOGLE_AI_KEY}
```

## 🚀 Deployment

### Docker Support
```bash
# Start PostgreSQL
docker-compose up -d

# Build application
mvn clean package

# Run
java -jar target/ai-ticketing-system-1.0.0.jar
```

### Production Considerations
- Use PostgreSQL instead of H2
- Set up proper API key management
- Configure CORS for your domain
- Enable HTTPS
- Set up monitoring and logging

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

**Kaoserahamed**
- GitHub: [@Kaoserahamed](https://github.com/Kaoserahamed)

## 🙏 Acknowledgments

- Google Gemini AI for powerful language understanding
- Spring Boot team for the excellent framework
- React team for the amazing frontend library

## 📧 Support

For support, email kaoserahamed@example.com or open an issue in the GitHub repository.

---

⭐ If you find this project useful, please consider giving it a star!

**Built with ❤️ using Spring Boot, React, and Google Gemini AI**
