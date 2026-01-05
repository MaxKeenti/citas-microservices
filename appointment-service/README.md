# Appointment Service

The **Appointment Service** (Appointment Service) helps manage the core business logic of scheduling, modifying, and canceling appointments (Citas).

## Features

- **Schedule Appointments**: Create new appointments for specific services ("Tramites").
- **Availability Checking**: Validates if a slot is free before booking.
- **Rules Enforcement**: Ensures business rules (e.g., no double booking, respect operating hours) are met.
- **Integration**: Communicates with HR Service to check employee availability.

## Tech Stack

- **Framework**: Quarkus
- **Database**: PostgreSQL
- **Persistence**: Hibernate ORM with Panache
- **REST Client**: Integrates with other microservices.

## Configuration

The service is configured via `application.properties`.

| Variable | Description | Default |
|----------|-------------|---------|
| `QUARKUS_DATASOURCE_JDBC_URL` | Database connection URL | `jdbc:postgresql://localhost:5432/appointment_db` |
| `QUARKUS_HTTP_PORT` | HTTP Port for the service | `8080` (mapped to `8084` in compose) |
| `QUARKUS_REST_CLIENT_HRESOURCES_API_URL` | URL for HR Service | `http://localhost:8083` |

## API Documentation

When running in development mode, the interactive OpenAPI (Swagger) documentation is available at:
[http://localhost:8084/q/swagger-ui](http://localhost:8084/q/swagger-ui)

## Development

### Prerequisites
- Java 21+
- Maven
- PostgreSQL

### Running in Dev Mode
```bash
./mvnw quarkus:dev
```

### Packaging
```bash
./mvnw package
```
