# Nook

> **Nook** is a privacy-first, location-based social networking backend built with Kotlin and Spring Boot. Users can toggle their visibility radar on demand, configure a custom appearance delay (e.g., 15 minutes), discover people who have crossed their path, send connection requests, and share a curated profile card with social links and up to 9 photos. The API is secured with Spring Security, BCrypt password hashing, and upcoming JWT and Google OAuth authentication, with database schema versioning managed by Flyway.
>
> *🚧 Work in progress - user registration, connection lifecycle, Flyway migrations, and domain exception handling are implemented; JWT authentication, PostGIS spatial queries, configurable radar delay, media uploads, and Google OAuth are on the roadmap.*

## Tech Stack

<div align="center">

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.21-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.x-brightgreen?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)](https://flywaydb.org/)
[![JWT](https://img.shields.io/badge/JWT_(Planned)-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![PostGIS](https://img.shields.io/badge/PostGIS_(Planned)-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://postgis.net/)
[![Google OAuth](https://img.shields.io/badge/Google_OAuth_(Planned)-4285F4?style=for-the-badge&logo=google&logoColor=white)](https://developers.google.com/identity/protocols/oauth2)

</div>

## Features

- **Implemented:**
  - User registration with bean validation (`@Valid`) and BCrypt password hashing
  - User lookup by UUID or username
  - Two-way connection workflow: send requests, list sent/received pending requests, and accept or decline connections
  - Domain rule enforcement (prevents self-connections, duplicate requests, already-connected states, and invalid status transitions)
  - Database schema migrations managed with Flyway
  - Global exception handling (`@ControllerAdvice`) with standardized error responses

- **Planned / In Progress:**
  - Stateless authentication with JWT and Google OAuth2 login
  - On-demand visibility radar with user-defined appearance delay (privacy buffer before showing up to others)
  - "Crossed paths" discovery feed powered by PostgreSQL + PostGIS spatial queries
  - Expandable public profile card with bio, social links (Instagram, X, GitHub, Spotify), and a curated grid of up to 9 photos

## Architecture

The application is a layered REST API organized by domain packages (`user`, `connection`, `config`, `exception`), separating controllers, services, repositories, and DTOs. Database schema evolution is version-controlled via Flyway migrations against PostgreSQL. Password encoding (`BCryptPasswordEncoder`) is configured centrally in `config`, while a global `@ControllerAdvice` (`GlobalExceptionHandler`) intercepts domain exceptions (`NotFoundException`, `ConflictException`, `AlreadyConnectedException`, `ConnectionRequestAlreadySentException`, `SelfConnectionNotAllowedException`, `AccessDeniedException`, `InvalidConnectionStatusException`) and returns standardized error payloads (`status`, `message`, and `timestamp`).

## API Endpoints

### Authentication (`/auth`) *(Planned)*

| Method | Route | Description |
|--------|-------|-------------|
| `POST` | `/auth/login` | Authenticates a user with credentials and returns a JWT *(Planned)* |
| `GET` | `/auth/callback/google` | Handles Google OAuth callback and returns a JWT *(Planned)* |

### Users (`/users`)

| Method | Route | Description |
|--------|-------|-------------|
| `POST` | `/users` | Register a new user |
| `GET` | `/users/{id}` | Get a user by UUID |
| `GET` | `/users/username/{username}` | Get a user by username |

### Connections (`/connections`)

| Method | Route | Description |
|--------|-------|-------------|
| `POST` | `/connections/{addresseeId}?requesterId={uuid}` | Send a connection request from `requesterId` to `addresseeId` |
| `PATCH` | `/connections/{connectionId}/accept?userId={uuid}` | Accept a pending connection request |
| `PATCH` | `/connections/{connectionId}/decline?userId={uuid}` | Decline a pending connection request |
| `GET` | `/connections/{userId}/received` | List all pending connection requests received by a user |
| `GET` | `/connections/{userId}/send` | List all pending connection requests sent by a user |

## Authentication

Password hashing is handled via Spring Security's `BCryptPasswordEncoder`. Once JWT authentication is wired into the security filter chain, user identification via query parameters (`requesterId`, `userId`) will be replaced by the authenticated principal, and protected endpoints will require the `Authorization` header:

```http
Authorization: Bearer <token>
```

## Getting Started

### Prerequisites

- Java 21
- Maven (or the included `./mvnw` wrapper)
- PostgreSQL (with PostGIS extension enabled for upcoming geolocation features)

### Environment Variables

The application reads its configuration from environment variables:

| Variable | Description |
|----------|-------------|
| `DATABASE_URL` | JDBC URL, e.g. `jdbc:postgresql://localhost:5432/nook` |
| `DATABASE_USERNAME` | Database username |
| `DATABASE_PASSWORD` | Database password |
| `JWT_KEY` | Secret key used to sign JWTs *(Planned)* |

### Configuration

`application.properties`:

```properties
spring.application.name=nook

spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
```

### Run the Application

```bash
./mvnw spring-boot:run
```

### Run Tests

```bash
./mvnw test
```

---

<div align="center">

Built by [rafael-vincensi](https://github.com/rafael-vincensi)

</div>
