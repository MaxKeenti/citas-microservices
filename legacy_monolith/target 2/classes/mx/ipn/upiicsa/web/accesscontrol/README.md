# Access Control Module

## Description
This module checks the identity of the user and manages user accounts, roles, and personal information. It handles authentication and authorization foundations for the application.

## Architecture
This module follows the **Hexagonal Architecture**.

### Domain Layer
Contains the core entities and business rules.
-   **Entities**:
    -   `Usuario`: Represents the system user credentials and roles.
    -   `Persona`: Represents the personal information associated with a user or an employee.

### Application Layer
Contains the business logic and use cases.
-   **Services**:
    -   `LoginService`: Handles user authentication.
    -   `UsuarioService`: Manages user lifecycle (CRUD).
    -   `PersonaService`: Manages personal information.
    -   `RolService`: Manages user roles.
    -   `GeneroService`: Manages gender catalog.

### Infrastructure Layer
Handles external communication and persistence.
-   **Adapters**:
    -   **In**: Web Controllers for handling HTTP requests (Login, User management).
    -   **Out**: Persistence adapters (JPA Repositories) for database interaction.
