# Catalog Service

The **Catalog Service** manages the catalog of available procedures ("Tramites") and their requirements.

## Features

- **Service Catalog**: CRUD operations for services offered by the institution.
- **Requirements Management**: Define documents or prerequisites needed for each service.
- **Metadata**: Stores duration, cost, and description of services.

## Tech Stack

- **Framework**: Quarkus
- **Database**: PostgreSQL
- **Persistence**: Hibernate ORM with Panache

## Configuration

The service is configured via `application.properties`.

| Variable | Description | Default |
|----------|-------------|---------|
| `QUARKUS_DATASOURCE_JDBC_URL` | Database connection URL | `jdbc:postgresql://localhost:5432/catalog_db` |
| `QUARKUS_HTTP_PORT` | HTTP Port for the service | `8080` (mapped to `8082` in compose) |

## API Documentation

When running in development mode, the interactive OpenAPI (Swagger) documentation is available at:
[http://localhost:8082/q/swagger-ui](http://localhost:8082/q/swagger-ui)

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
