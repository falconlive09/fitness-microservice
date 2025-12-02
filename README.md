# 🏃‍♂️ AI-Powered Fitness Tracker - Microservices Architecture

A production-grade, full-stack fitness tracking application built with microservices architecture, featuring AI-driven personalized health recommendations. This project demonstrates modern cloud-native patterns including service discovery, API gateway, event-driven communication, and OAuth 2.0 security.

---

## 🎯 Project Overview

This application allows users to log fitness activities (running, walking, cycling, etc.) and receive AI-generated personalized health recommendations. Built using microservices architecture, it demonstrates scalability, fault tolerance, and modern distributed system design patterns.

### Key Highlights

- **Microservices Architecture** with independent deployment and scaling
- **AI Integration** using Google Gemini API for personalized recommendations
- **Event-Driven Communication** with RabbitMQ for asynchronous processing
- **OAuth 2.0 PKCE** authentication flow with Keycloak
- **Service Discovery** with Netflix Eureka
- **Centralized Configuration** with Spring Cloud Config Server
- **API Gateway** for unified access and security

---

### Microservices Breakdown

| Service | Port | Database | Purpose |
|---------|------|----------|---------|
| **Eureka Server** | 8761 | - | Service registry and discovery |
| **Config Server** | 8888 | - | Centralized configuration management |
| **API Gateway** | 8080 | - | Single entry point, routing, security |
| **User Service** | 8081 | MySQL | User profile management |
| **Activity Service** | 8082 | MongoDB | Fitness activity logging |
| **AI Service** | 8083 | MongoDB | AI recommendation generation |
| **Keycloak** | 8181 | PostgreSQL | Identity and access management |
| **RabbitMQ** | 5672/15672 | - | Message broker for async communication |

---

## 🚀 Features

### User Management
- OAuth 2.0 PKCE authentication flow
- User profile synchronization between Keycloak and User Service
- Secure JWT-based authorization

### Activity Tracking
- Log multiple fitness activities (running, walking, cycling, etc.)
- Track duration, calories burned, and activity type
- View activity history with detailed metrics

### AI-Powered Recommendations
- Google Gemini AI integration for personalized health insights
- Asynchronous recommendation generation via RabbitMQ
- Context-aware suggestions based on activity patterns

### Microservices Patterns
- **Service Discovery**: Automatic service registration with Eureka
- **API Gateway**: Unified routing and load balancing
- **Config Server**: Externalized configuration with Git backend
- **Circuit Breaker**: Fault tolerance and graceful degradation
- **Event-Driven**: Asynchronous communication with message queues

---

## 🛠️ Tech Stack

### Backend
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Cloud** (Gateway, Config, Netflix Eureka)
- **Spring Data JPA** (User Service)
- **Spring Data MongoDB** (Activity & AI Services)
- **RabbitMQ** (Message Broker)
- **Keycloak** (OAuth 2.0 Server)
- **MySQL** (User data)
- **MongoDB** (Activity & recommendation data)

### Frontend
- **React 18**
- **Redux Toolkit** (State management)
- **Axios** (HTTP client)
- **React Router** (Navigation)
- **Material-UI / Tailwind CSS** (Styling)

### AI & External APIs
- **Google Gemini API** (AI-powered recommendations)

### DevOps & Tools
- **Docker** (Containerization)
- **Maven** (Build tool)
- **Git** (Version control)

---

## 📋 Prerequisites

Before running this project, ensure you have:

- **Java 17+** installed
- **Node.js 18+** and npm
- **Docker & Docker Compose** (recommended)
- **Maven 3.6+**
- **Git**
- **Google Gemini API Key** ([Get one here](https://ai.google.dev/))

---

## ⚙️ Installation & Setup

### 1️⃣ Clone the Repository

git clone https://github.com/falconlive09/fitness-microservice.git
cd fitness-microservice

text

### 2️⃣ Start Infrastructure Services

Using Docker Compose (recommended):

docker-compose up -d

text

This will start:
- MySQL (port 3306)
- MongoDB (port 27017)
- RabbitMQ (ports 5672, 15672)
- Keycloak (port 8181)

### 3️⃣ Configure Keycloak

1. Access Keycloak at `http://localhost:8181`
2. Login with admin credentials (check `docker-compose.yml`)
3. Create a new realm: `fitness-app`
4. Create a client: `fitness-client`
5. Configure redirect URIs: `http://localhost:5173/*`
6. Enable PKCE flow and public client
7. Create test users with credentials

### 4️⃣ Update Configuration

Edit `configserver/src/main/resources/application.yml` or your Git-backed config repository:

gemini:
api:
key: YOUR_GEMINI_API_KEY_HERE

spring:
datasource:
url: jdbc:mysql://localhost:3306/fitnessdb
username: root
password: yourpassword

data:
mongodb:
uri: mongodb://localhost:27017/fitness

keycloak:
realm: fitness-app
auth-server-url: http://localhost:8181
resource: fitness-client

text

### 5️⃣ Start Microservices

Start services in this order:

1. Eureka Server
cd eureka
mvn spring-boot:run

2. Config Server
cd ../configserver
mvn spring-boot:run

3. Gateway
cd ../gateway
mvn spring-boot:run

4. User Service
cd ../userservice
mvn spring-boot:run

5. Activity Service
cd ../activityservice
mvn spring-boot:run

6. AI Service
cd ../aiservice
mvn spring-boot:run

text

### 6️⃣ Start Frontend

cd fitness-app-frontend
npm install
npm run dev

text

Access the application at `http://localhost:5173`

---

## 🧪 Testing the Application

1. **Navigate to** `http://localhost:5173`
2. **Login** using Keycloak credentials
3. **Add an activity** (e.g., Running, 30 mins, 300 calories)
4. **View AI recommendations** generated for your activity
5. **Check RabbitMQ Console** at `http://localhost:15672` (guest/guest)
6. **Check Eureka Dashboard** at `http://localhost:8761`

---

## 📂 Project Structure

fitness-microservice/
├── eureka/ # Service discovery server
├── configserver/ # Centralized configuration
├── gateway/ # API Gateway
├── userservice/ # User management microservice
├── activityservice/ # Activity logging microservice
├── aiservice/ # AI recommendation microservice
├── fitness-app-frontend/ # React frontend
├── docker-compose.yml # Infrastructure setup
└── README.md

text

---

## 🔐 Security Architecture

### OAuth 2.0 PKCE Flow

1. User clicks "Login" → Frontend redirects to Keycloak
2. User authenticates → Keycloak issues authorization code
3. Frontend exchanges code for JWT token (with PKCE verifier)
4. JWT token included in all API requests to Gateway
5. Gateway validates token with Keycloak
6. Requests routed to appropriate microservices

### User Synchronization

- Keycloak manages authentication
- User Service stores extended profile data (height, weight, preferences)
- Automatic sync between Keycloak and User Service on user creation

---

## 🌐 API Endpoints

### Gateway (http://localhost:8080)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users/profile` | Get user profile |
| POST | `/api/activities` | Create new activity |
| GET | `/api/activities/{userId}` | Get user activities |
| GET | `/api/recommendations/{activityId}` | Get AI recommendations |

### Eureka Dashboard
- **URL**: `http://localhost:8761`

### RabbitMQ Management
- **URL**: `http://localhost:15672` (guest/guest)

---

## 🤖 AI Integration Details

### Google Gemini API

The AI Service consumes activity data from RabbitMQ and generates personalized recommendations:

// Pseudo-code for AI integration
public String generateRecommendation(Activity activity) {
String prompt = buildPrompt(activity);
GeminiResponse response = geminiClient.generate(prompt);
return response.getText();
}

text

**Prompt Template:**
Based on the following fitness activity:

Type: {activityType}

Duration: {duration} minutes

Calories: {caloriesBurned}

Generate 3 personalized health recommendations for improvement.

text

---

## 📊 Monitoring & Observability

- **Eureka Dashboard**: Service health and registration status
- **RabbitMQ Console**: Message queue monitoring
- **Application Logs**: Structured logging with SLF4J
- *(Optional)* Spring Boot Actuator endpoints for metrics

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- Inspired by the **EmbarkX** Spring Boot Microservices course
- Built as a learning project to demonstrate modern microservices patterns with AI
- Special thanks to the Spring and React communities

---

## 📧 Contact

**Neal Gautam**
- Email: nealgautam09@gmail.com
- LinkedIn: [linkedin.com/in/nealgautam](https://linkedin.com/in/nealgautam)
- GitHub: [github.com/falconlive09](https://github.com/falconlive09)

---

## 🚀 Future Enhancements

- [ ] Add Docker Compose for one-command deployment
- [ ] Implement distributed tracing with Zipkin/Sleuth
- [ ] Add Kubernetes deployment manifests
- [ ] Integrate Grafana + Prometheus for metrics
- [ ] Add CI/CD pipeline with GitHub Actions
- [ ] Implement caching with Redis
- [ ] Add unit and integration tests
- [ ] Deploy to AWS/Azure cloud platform

---

⭐ **If you found this project helpful, please consider giving it a star!**
