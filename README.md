# Linkly — Backend

Linkly is a full-stack URL shortener with user authentication, link analytics, and JWT-based session management. This repository contains the Spring Boot backend.

**Frontend repo:** [Linkly-frontend](https://github.com/ArjavTripathi/Linkly-frontend)

---

## Tech Stack

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat&logo=postgresql&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat&logo=spring-security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=flat&logo=JSON%20web%20tokens)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven&logoColor=white)

---

## Features

- User registration and login with JWT session management
- Secure link creation with unique short codes
- Redirect handling from short URL to original destination
- Per-link analytics (click tracking)
- Spring Security configuration with protected routes
- PostgreSQL persistence via Spring Data JPA

---

## Architecture

Standard layered Spring Boot architecture:

- **Controller Layer** — REST endpoints for auth, link management, and redirects
- **Service Layer** — business logic for URL generation, auth, and analytics
- **Repository Layer** — JPA repositories for users and links
- **Security Config** — JWT filter chain with stateless session management

---

## API Overview

| Endpoint | Method | Description |
|---|---|---|
| `/api/auth/register` | POST | Register a new user |
| `/api/auth/login` | POST | Login and receive JWT |
| `/api/links` | POST | Create a new short link |
| `/api/links` | GET | Get all links for authenticated user |
| `/api/links/{id}/analytics` | GET | Get click analytics for a link |
| `/{shortCode}` | GET | Redirect to original URL |

---

## Running Locally

**Prerequisites:** Java 17+, PostgreSQL, Maven

```bash
git clone https://github.com/ArjavTripathi/linkly
cd linkly
```

Configure your database and JWT secret in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/linkly
spring.datasource.username=your_username
spring.datasource.password=your_password
jwt.secret=your_jwt_secret
```

Then run:

```bash
mvn spring-boot:run
```

Server starts at `http://localhost:8080`

---

## Related

- [Linkly Frontend](https://github.com/ArjavTripathi/Linkly-frontend) — React + Vite frontend
