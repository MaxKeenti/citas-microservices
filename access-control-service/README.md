# Access Control Service

The **Access Control Service** is responsible for managing user identities, authentication, and authorization within the Citas Microservices System.

## Features

- **User Management**: Create, update, and delete user accounts.
- **Authentication**: Validates user credentials (login/password).
- **Role-Based Access Control (RBAC)**: Supports roles such as `ADMIN`, `CLIENT`, and `EMPLOYEE`.
- **Session Management**: Handles user sessions (implied via stateless auth or session tokens).

## Tech Stack

- **Framework**: Quarkus
- **Database**: PostgreSQL
- **Persistence**: Hibernate ORM with Panache

## Configuration

The service is configured via `application.properties`. Key environment variables (overridable via Docker or `.env`):

| Variable | Description | Default |
|----------|-------------|---------|
| `QUARKUS_DATASOURCE_JDBC_URL` | Database connection URL | `jdbc:postgresql://localhost:5432/access_control_db` |
| `QUARKUS_DATASOURCE_USERNAME` | Database username | `user` |
| `QUARKUS_DATASOURCE_PASSWORD` | Database password | `password` |
| `QUARKUS_HTTP_PORT` | HTTP Port for the service | `8080` (mapped to `8081` in compose) |

## API Documentation

When running in development mode, the interactive OpenAPI (Swagger) documentation is available at:
[http://localhost:8081/q/swagger-ui](http://localhost:8081/q/swagger-ui)

## Development

### Prerequisites
- Java 21+
- Maven (wrapper included)
- PostgreSQL (or use Dev Services)

### Running in Dev Mode
```bash
./mvnw quarkus:dev
```
Quarkus Dev Services will automatically start a PostgreSQL container if one is not configured.

### Packaging
```bash
./mvnw package
```
To build a docker image:
```bash
docker build -f src/main/docker/Dockerfile.jvm -t access-control-service .
```
