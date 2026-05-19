# BekbolatovNurdauletTaskManagementSystem

Production-ready Spring Boot 3 backend for a Task Management System.

## Stack

- Java 21
- Spring Boot 3
- Spring Web, Data JPA, Validation, Security
- JWT authentication and role-based authorization
- PostgreSQL
- Lombok
- MapStruct
- Swagger/OpenAPI
- Docker and Docker Compose

## Architecture

The project follows layered architecture:

- `controller` - REST endpoints with `ResponseEntity`
- `service` - business logic
- `repository` - Spring Data JPA access
- `dto` - request and response DTO classes
- `entity` - JPA entities and relationships
- `mapper` - MapStruct mappers
- `security` - JWT and authentication
- `exception` - custom exceptions and global handler
- `config` - Swagger, security, logging, demo data

All Java classes use the required `BekbolatovNurdaulet` prefix.

## Run With Docker

```bash
docker compose up --build
```

Application: `http://localhost:8080`

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

Health check: `http://localhost:8080/actuator/health`

## Local Run

Start PostgreSQL and create database:

```sql
CREATE DATABASE task_management;
```

Then run:

```bash
mvn spring-boot:run
```

## Demo Users

Demo data is inserted by `CommandLineRunner`.

Admin:

```text
email: admin@example.com
password: admin123
```

User:

```text
email: user@example.com
password: user123
```

## JWT Auth

1. Register or login:

```http
POST /api/auth/login
```

2. Copy the returned token.
3. Send protected requests with:

```http
Authorization: Bearer <token>
```

Swagger also supports the `Authorize` button for JWT.

## Main Endpoints

Auth:

- `POST /api/auth/register`
- `POST /api/auth/login`

Users:

- `GET /api/users`
- `GET /api/users/{id}`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

Projects:

- `POST /api/projects`
- `GET /api/projects`
- `GET /api/projects/{id}`
- `PUT /api/projects/{id}`
- `DELETE /api/projects/{id}`

Tasks:

- `POST /api/tasks`
- `GET /api/tasks`
- `GET /api/tasks/{id}`
- `PUT /api/tasks/{id}`
- `DELETE /api/tasks/{id}`

Task query examples:

```http
GET /api/tasks?page=0&size=5
GET /api/tasks?sort=deadline,asc
GET /api/tasks?status=IN_PROGRESS
GET /api/tasks?search=backend
GET /api/tasks?page=0&size=5&sort=deadline,asc&status=TODO&search=api
```

Comments:

- `POST /api/comments`
- `GET /api/comments/task/{taskId}`

Attachments:

- `POST /api/attachments/upload?taskId=1`
- `GET /api/attachments/{id}/download`

## File Upload

Use multipart form data:

- field `taskId` as query parameter
- field `file` as uploaded file

Files are stored in the local `uploads/` directory or Docker volume.

## Async Processes

The project includes two async processes using `@Async` and `CompletableFuture`:

- notification after task creation
- report generation after task creation

## Docker Notes

The Dockerfile uses a multistage build:

- Maven + JDK 21 build image
- lightweight JRE 21 runtime image
- non-root user
- Actuator health check
- optimized JVM container memory settings
