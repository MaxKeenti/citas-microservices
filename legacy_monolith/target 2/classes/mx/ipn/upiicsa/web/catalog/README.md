# Catalog Module

## Description
This module manages the catalogs of services and price lists offered by the application.

## Architecture
This module follows the **Hexagonal Architecture**.

### Domain Layer
Contains the core entities and business rules.
-   **Entities**:
    -   `ServicioJpa`: Represents a service offered (e.g., Haircut).
    -   `ListaPrecioJpa`: Represents a price list configuration.
    -   `EstadoListaPrecioJpa`: Represents the status of a price list.
    -   `ServicioListaPrecioJpa`: Represents the association between a service and a price list (Application of prices).

### Application Layer
Contains the business logic and use cases.
-   **Services**:
    -   `ServicioService`: Manages services.
    -   `ListaPrecioService`: Manages price lists.
    -   `EstadoListaPrecioService`: Manages price list statuses.
    -   `ServicioListaPrecioService`: Manages the assignment of services to price lists.

### Infrastructure Layer
-   **Adapters**:
    -   **In**: Web Controllers for catalog management.
    -   **Out**: Persistence adapters for database operations.
