# Microservices Refactoring Plan - Spring to Quarkus

## Goal Description
Refactor the existing Spring Boot Modular Monolith (`citas-microservices`) into a distributed Microservices Architecture using **Quarkus**. The goal is to decouple the four existing modules into independent deployable services, improving scalability and maintainability.

## User Review Required
> [!IMPORTANT]
> **Project Structure**: We will move the entire existing codebase into a `legacy_monolith` folder. The new microservices will be created at the repository root.
>
> **Database Strategy**: **YES, we will modularize the database.** This is best practice. We will create 4 separate PostgreSQL databases (or schemas) in our `compose.yaml`, one for each service (`access_control_db`, `catalog_db`, `hresources_db`, `appointment_db`). This ensures true decoupling.
>
> **Breaking Change**: The current JPA Foreign Key relationships between modules (e.g., `Cita` -> `Persona`) will be broken. References will be stored as IDs (e.g., `personaId`).

## Proposed Architecture

**Root Directory Structure**:
```text
/
├── legacy_monolith/       # Old Spring Boot Source Code
├── access-control-service/
├── catalog-service/
├── hresources-service/
├── appointment-service/
├── web-portal/           # BFF
├── compose.yaml          # Updated for 4 databases + 5 services
└── README.md
```

## Service Breakdown

### 1. Access Control Service (`access-control-service`)
-   **Database**: `access_control_db`
-   **Tech**: Quarkus, Hibernate ORM, RESTEasy.
-   **Migration**: Move `accesscontrol` package.
-   **API**: `POST /auth/login`, `GET /users/{id}`, `GET /roles`

### 2. Catalog Service (`catalog-service`)
-   **Database**: `catalog_db`
-   **Tech**: Quarkus, Hibernate ORM, RESTEasy.
-   **Migration**: Move `catalog` package.
-   **API**: `GET /services`, `GET /services/{id}`

### 3. HResources Service (`hresources-service`)
-   **Database**: `hresources_db`
-   **Tech**: Quarkus, Hibernate ORM, RESTEasy.
-   **Migration**: Move `hresources` package.
-   **API**: `GET /employees`, `GET /branches`, `GET /schedules`

### 4. Appointment Service (`appointment-service`)
-   **Database**: `appointment_db`
-   **Tech**: Quarkus, Hibernate ORM, RESTEasy, **REST Client**.
-   **Migration**: Move `appointment` package. Refactor Entities to store IDs instead of Objects.
-   **API**: `GET /appointments?date=...`, `POST /appointments`

### 5. Web Portal (BFF) (`web-portal`)
-   **No Database** (Uses Sessions/Redis or Stateless).
-   **Tech**: Quarkus with **Thymeleaf** (via extension or manual configuration).
-   **Strategy**: Reuse existing Thymeleaf templates (`src/main/resources/templates`) to minimize immediate frontend work. This serves as a temporary BFF until a future migration to **Next.js**.
-   **Function**: Handles Server-Side Rendering (SSR). Controllers call the backend microservices via REST to fetch data and render views.

## Implementation Steps

1.  **Archive Legacy Code**: Move current source to `legacy_monolith`.
2.  **Setup Infrastructure**: Update `compose.yaml` to spin up 4 PostgreSQL containers (or 1 container with 4 DBs) and the services.
3.  **Scaffold Microservices**: Generate 5 new Quarkus projects at the root.
4.  **Migrate & Split Data**: Extract SQL scripts (using `pg_dump` or manually splitting `01-create.sql`) for each new database.
5.  **Develop Services**:
    -   Copy specialized entities/logic to respective services.
    -   Refactor shared entities into DTOs or duplicated entities (Shared Kernel pattern is discouraged to avoid coupling, duplication is cheap).
6.  **Integrate Web Portal**: Re-build the UI controllers to aggregate data from the new APIs.

## Verification Plan

### Automated Tests
-   **Code**: `mvn test` in each service.
-   **Infrastructure**: `docker compose up -d` spins up everything healthy.

### Manual Verification
1.  Navigate to `localhost:8080` (Web Portal).
2.  Verify full user flow: Login -> View Services -> Select Employee -> Book Appointment.
3.  Verify data persistence in the correct isolated database.
