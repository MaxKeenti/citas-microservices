# Web Portal Service

The **Web Portal** serves as an aggregator or backend-for-frontend (BFF) layer, and provides server-side rendered pages using Quarkus Qute / Renarde (if applicable).

## Features

- **Aggregation**: Consumes APIs from Access Control, Catalog, Appointment, and HR services to provide unified data.
- **Server-Side Rendering**: Serves HTML pages for the legacy or admin portal interface.
- **Gateway**: Acts as a gateway for certain internal communications.

## Tech Stack

- **Framework**: Quarkus
- **Template Engine**: Qute

## Configuration

The service is configured via `application.properties`.

| Variable | Description | Default |
|----------|-------------|---------|
| `QUARKUS_HTTP_PORT` | HTTP Port for the service | `8080` (mapped to `8080` in compose) |
| `QUARKUS_REST_CLIENT_ACCESS_CONTROL_API_URL` | URL for Access Control Service | `http://localhost:8081` |
| `QUARKUS_REST_CLIENT_CATALOG_API_URL` | URL for Catalog Service | `http://localhost:8082` |
| `QUARKUS_REST_CLIENT_HRESOURCES_API_URL` | URL for HR Service | `http://localhost:8083` |
| `QUARKUS_REST_CLIENT_APPOINTMENT_API_URL` | URL for Appointment Service | `http://localhost:8084` |

## Development

### Prerequisites
- Java 21+
- Maven

### Running in Dev Mode
```bash
./mvnw quarkus:dev
```

### Packaging
```bash
./mvnw package
```
