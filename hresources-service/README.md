# Human Resources Service

The **Human Resources (HResources) Service** manages employee data, schedules, and work shifts.

## Features

- **Employee Management**: CRUD operations for employees.
- **Schedules**: Define work hours and shifts.
- **Geospatial Data**: Uses PostGIS to manage location-based data (e.g., branch locations).
- **Availability**: Provides data on when employees are available to take appointments.

## Tech Stack

- **Framework**: Quarkus
- **Database**: PostgreSQL with **PostGIS** extension.
- **Persistence**: Hibernate ORM with Panache (and Hibernate Spatial).

## Configuration

The service is configured via `application.properties`.

| Variable | Description | Default |
|----------|-------------|---------|
| `QUARKUS_DATASOURCE_JDBC_URL` | Database connection URL | `jdbc:postgresql://localhost:5432/hresources_db` |
| `QUARKUS_HTTP_PORT` | HTTP Port for the service | `8080` (mapped to `8083` in compose) |

## API Documentation

When running in development mode, the interactive OpenAPI (Swagger) documentation is available at:
[http://localhost:8083/q/swagger-ui](http://localhost:8083/q/swagger-ui)

## Development

### Prerequisites
- Java 21+
- Maven
- PostgreSQL with PostGIS

### Running in Dev Mode
```bash
./mvnw quarkus:dev
```

### Packaging
```bash
./mvnw package
```
