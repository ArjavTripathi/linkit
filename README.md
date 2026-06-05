# Linkit — Backend

Linkit is a full-stack URL shortener with user authentication, link analytics, and JWT-based session management. This repository contains the Spring Boot backend.

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
- Secure short URL creation with unique short codes
- HTTP 302 redirect handling from short URL to original destination
- Per-link click analytics with date range filtering
- Aggregate click analytics across all links for a user
- Spring Security with role-based route protection (`ROLE_USER`)
- PostgreSQL persistence via Spring Data JPA

---

## Architecture

Standard layered Spring Boot architecture:

- **Controller Layer** — REST endpoints for auth, URL management, and redirects
- **Service Layer** — business logic for URL generation, authentication, and analytics
- **Repository Layer** — JPA repositories for users and URL mappings
- **Security Config** — JWT filter chain with stateless session management

---

## API Reference

### Auth — `/api/auth`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/api/auth/public/register` | None | Register a new user |
| POST | `/api/auth/public/login` | None | Login and receive JWT |

### URL Management — `/api/urls`

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/api/urls/shorten` | `ROLE_USER` | Create a new short URL |
| GET | `/api/urls/myurls` | `ROLE_USER` | Get all short URLs for the authenticated user |
| GET | `/api/urls/analytics/{shorturl}` | `ROLE_USER` | Get click events for a specific URL by date range |
| GET | `/api/urls/totalClicks` | `ROLE_USER` | Get total clicks across all user links by date range |

### Redirect

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| GET | `/{shorturl}` | None | Redirect to original URL (HTTP 302) |

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
