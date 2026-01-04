# Appointment Module

## Description
This module manages the core functionality of scheduling and managing appointments (`Citas`).

## Architecture
This module follows the **Hexagonal Architecture**.

### Domain Layer
Contains the core entities and business rules.
-   **Entities**:
    -   `CitaJpa`: Represents the appointment entity. *(Note: Currently named with 'Jpa' suffix, acting as the domain entity)*.

### Application Layer
Contains the business logic and use cases.
-   **Services**:
    -   `CitaService`: Manages the lifecycle of appointments (Create, Update, Delete, List).

### Infrastructure Layer
-   **Adapters**:
    -   **In**: Web Controllers for appointment management.
    -   **Out**: Persistence adapters for `Citas` database operations.
