# <img src="https://github.com/user-attachments/assets/f65969fe-ceba-4876-aa0d-93c67ed91e9b" alt="image" width="32" height="32" /> Task Manager

A full-featured **Spring Boot** REST API application for managing tasks with user authentication, role-based access control, task lifecycle management, and analytics.

---

## 📚 Table of Contents

- [Overview](#overview)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Setup](#setup)
- [Database Configuration](#database-configuration)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [Testing APIs](#testing-apis)
- [Error Handling](#error-handling)
- [Contributing](#contributing)

---

## Overview

Task Manager is a backend application built with **Spring Boot 3** that provides:

- 🔐 **User Registration & Login** via JWT-based authentication
- 📝 **Task Creation & Management** for authenticated users
- ✅ **Admin Approval/Rejection** of submitted tasks
- 📊 **Analytics Dashboard** for admins to monitor task activity
- 🔒 **Role-based Access Control** (USER / ADMIN)

---

## Technologies

| Technology | Version | Purpose |
|---|---|---|
| Java | 17+ | Core language |
| Spring Boot | 3.x | Application framework |
| Spring Security | 6.x | Authentication & authorization |
| Spring Data JPA | 3.x | Database ORM |
| MySQL | 8.x | Relational database |
| MapStruct | Latest | DTO ↔ Entity mapping |
| Maven | 3.x | Build & dependency management |
| JWT (JSON Web Token) | - | Stateless authentication |

---

## Project Structure

```
task-manager-assignment/
├── src/
│   ├── main/
│   │   ├── java/com/example/taskmanager/
│   │   │   ├── config/           # Security & app configuration
│   │   │   ├── controller/       # REST controllers
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── entity/           # JPA Entities
│   │   │   ├── mapper/           # MapStruct mappers
│   │   │   ├── repository/       # Spring Data repositories
│   │   │   ├── security/         # JWT filter & utilities
│   │   │   └── service/          # Business logic
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/...
├── pom.xml
└── README.md
```

---

## Setup

### Prerequisites

Before you begin, make sure the following are installed on your machine:

- ☕ **JDK 17** or later → [Download](https://adoptium.net/)
- 📦 **Maven 3.x** (or use the included Maven Wrapper `./mvnw`)
- 🐬 **MySQL Server 8.x** → [Download](https://dev.mysql.com/downloads/)

### Step-by-Step Installation

**1. Clone the repository**

```bash
git clone https://github.com/akshayv06/task-manager-assignment.git
```

**2. Navigate to the project directory**

```bash
cd task-manager-assignment
```

**3. Configure the database**

See the [Database Configuration](#database-configuration) section below before building.

**4. Build the project**

```bash
./mvnw clean install
```

This command:
- Compiles all source code
- Runs the test suite
- Packages the application into a JAR file
- Runs MapStruct annotation processors to generate mapper implementations

**5. Run the application**

```bash
./mvnw spring-boot:run
```

Or, run the packaged JAR directly:

```bash
java -jar target/*.jar
```

The application will start on **`http://localhost:8080`** by default.

> To change the port, add `server.port=YOUR_PORT` to `application.properties`.

---

## Database Configuration

**1. Create the MySQL database schema**

Connect to your MySQL server and run:

```sql
CREATE DATABASE IF NOT EXISTS task_manager_db;
```

**2. Update `application.properties`**

Open `src/main/resources/application.properties` and configure your connection details:

```properties
# ─── DataSource ────────────────────────────────────────────────
spring.datasource.url=jdbc:mysql://localhost:3306/task_manager_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ─── Hibernate / JPA ───────────────────────────────────────────
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# ─── JWT ───────────────────────────────────────────────────────
jwt.secret=YOUR_JWT_SECRET_KEY
jwt.expiration=86400000
```

> ⚠️ Replace `YOUR_DB_USERNAME`, `YOUR_DB_PASSWORD`, and `YOUR_JWT_SECRET_KEY` with your actual credentials.

> `spring.jpa.hibernate.ddl-auto=update` will automatically create and update database tables based on your JPA entities on startup — no manual schema migrations needed during development.

---

## API Endpoints

### 🔑 Authentication

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/api/auth/register` | Register a new user | ❌ |
| `POST` | `/api/auth/login` | Login and receive JWT token | ❌ |

**Register — Request Body:**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securepassword"
}
```

**Login — Request Body:**
```json
{
  "email": "john@example.com",
  "password": "securepassword"
}
```

**Login — Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "john_doe",
  "role": "USER"
}
```

---

### 📝 Task Management

| Method | Endpoint | Description | Auth Required | Role |
|---|---|---|---|---|
| `POST` | `/api/tasks` | Create a new task | ✅ | USER |
| `GET` | `/api/tasks` | View all tasks | ✅ | USER / ADMIN |
| `GET` | `/api/tasks/{id}` | Get task by ID | ✅ | USER / ADMIN |
| `PUT` | `/api/tasks/{id}/approve` | Approve a task | ✅ | ADMIN |
| `PUT` | `/api/tasks/{id}/reject` | Reject a task | ✅ | ADMIN |

**Create Task — Request Body:**
```json
{
  "title": "Fix login bug",
  "description": "The login button is unresponsive on mobile",
  "priority": "HIGH",
  "dueDate": "2025-08-01"
}
```

**Task — Response:**
```json
{
  "id": 1,
  "title": "Fix login bug",
  "description": "The login button is unresponsive on mobile",
  "priority": "HIGH",
  "status": "PENDING",
  "dueDate": "2025-08-01",
  "createdBy": "john_doe",
  "createdAt": "2025-07-15T10:30:00"
}
```

> **Task Status Flow:** `PENDING` → `APPROVED` or `REJECTED`

---

### 📊 Analytics

| Method | Endpoint | Description | Auth Required | Role |
|---|---|---|---|---|
| `GET` | `/api/analytics` | View task analytics summary | ✅ | ADMIN |

**Analytics — Response:**
```json
{
  "totalTasks": 42,
  "pendingTasks": 10,
  "approvedTasks": 28,
  "rejectedTasks": 4,
  "tasksByUser": {
    "john_doe": 15,
    "jane_smith": 27
  }
}
```

---

## Authentication

This application uses **JWT (JSON Web Token)** for stateless authentication.

### How It Works

1. Register or log in via `/api/auth/register` or `/api/auth/login`
2. Receive a JWT token in the response
3. Include the token in the `Authorization` header for all protected requests:

```
Authorization: Bearer <your-jwt-token>
```

### Token Expiry

Tokens expire after **24 hours** by default (configurable via `jwt.expiration` in `application.properties`). After expiry, you must log in again to get a new token.

---

## Testing APIs

Use **[Postman](https://www.postman.com/)** or **`curl`** to interact with the API.

### Using Postman

1. Import the base URL: `http://localhost:8080`
2. Set the `Authorization` header type to **Bearer Token** and paste your JWT after logging in
3. Set `Content-Type: application/json` for POST/PUT requests

### Using curl

**Register a new user:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","email":"john@example.com","password":"pass123"}'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"pass123"}'
```

**Create a task (authenticated):**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <Your-JWT-Token>" \
  -d '{"title":"My Task","description":"Details here","priority":"MEDIUM"}'
```

**Get all tasks:**
```bash
curl -X GET http://localhost:8080/api/tasks \
  -H "Authorization: Bearer <Your-JWT-Token>"
```

**Approve a task (admin only):**
```bash
curl -X PUT http://localhost:8080/api/tasks/1/approve \
  -H "Authorization: Bearer <Your-Admin-JWT-Token>"
```

---

## Error Handling

The API returns standard HTTP status codes with a consistent error response format:

```json
{
  "timestamp": "2025-07-15T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Access denied: Admin role required",
  "path": "/api/analytics"
}
```

| Status Code | Meaning |
|---|---|
| `200 OK` | Request succeeded |
| `201 Created` | Resource created successfully |
| `400 Bad Request` | Invalid request body or parameters |
| `401 Unauthorized` | Missing or invalid JWT token |
| `403 Forbidden` | Valid token but insufficient permissions |
| `404 Not Found` | Resource not found |
| `500 Internal Server Error` | Unexpected server error |

---

## Contributing

Contributions are welcome! To get started:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m "Add your feature"`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request

Please ensure all new features include appropriate tests and follow the existing code style.

---

## License

This project is open source. See the [LICENSE](LICENSE) file for details.

---

> Built with ❤️ using Spring Boot 3 and Java 17
