# Spring Boot REST API

A practice REST API built with **Spring Boot 3**, featuring full CRUD operations, JWT-based authentication, role-based access control, and an in-memory H2 database.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.4.5 |
| Language | Java 17 |
| Persistence | Spring Data JPA + H2 |
| Security | Spring Security + JWT (jjwt 0.12.3) |
| Validation | Jakarta Bean Validation |
| Boilerplate | Lombok |
| Testing | JUnit 5 + Spring Security Test |
| Build | Maven |

---

## Project Structure

```
src/main/java/com/practice/api/
├── config/           # SecurityConfig — filter chain, auth provider, password encoder
├── controller/       # REST controllers (PostController, UserEntityController)
│   └── security/     # AuthController
├── dto/              # Request / Response records
├── entity/           # JPA entities (UserEntity, Post, RoleEntity, RoleEnum)
├── exception/        # GlobalExceptionHandler, ResourceNotFoundException
├── initializer/      # DataLoader — seeds roles and a default admin on startup
├── repository/       # Spring Data repositories
├── service/          # Business logic interfaces and implementations
│   └── security/     # UserDetailServiceImpl
└── utils/            # JwtUtils — token generation and verification
```

---

## API Endpoints

### Users — `/api/user`

| Method | Path | Description | Access |
|--------|------|-------------|--------|
| `POST` | `/api/user/save` | Register a new user | Authenticated |
| `GET` | `/api/user` | List all users (paginated, filterable by username) | `ADMIN` only |
| `GET` | `/api/user/{id}` | Get user by ID | Authenticated |
| `PUT` | `/api/user/{id}` | Full update of a user | Authenticated |
| `PATCH` | `/api/user/{id}` | Update user's last name | Authenticated |
| `DELETE` | `/api/user/{id}` | Delete a user | Authenticated |

### Posts — `/api/post`

| Method | Path | Description | Access |
|--------|------|-------------|--------|
| `GET` | `/api/post` | List all posts (paginated, filterable by `userId`) | Authenticated |
| `GET` | `/api/post/{id}` | Get post by ID | Authenticated |
| `POST` | `/api/post` | Create a new post | Authenticated |
| `PUT` | `/api/post/{id}` | Full update of a post | Authenticated |
| `PATCH` | `/api/post/{id}` | Update post content only | Authenticated |
| `DELETE` | `/api/post/{id}` | Delete a post | Authenticated |

---

## Security

- **Stateless sessions** — no HTTP session is stored server-side.
- **HTTP Basic** authentication used for credential exchange.
- **JWT** tokens are generated via `JwtUtils` and can be verified on subsequent requests.
- **Role-based access**: `ROLE_ADMIN` is required for sensitive endpoints (e.g. listing all users).
- Passwords are hashed with **BCrypt**.

### Roles

| Role | Description |
|------|-------------|
| `ADMIN` | Full access, including user listing |
| `USER` | Standard authenticated access |

Roles and a default admin user are seeded automatically on startup by `DataLoader`.

---

## Running the App

```bash
./mvnw spring-boot:run
```

The H2 console is available at `http://localhost:8080/h2-console` (in-memory database, resets on restart).

---

## Running Tests

```bash
./mvnw test
```

Tests cover service layer logic for both `PostServiceImpl` and `UserEntityServiceImpl`.