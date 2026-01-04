# Human Resources Module

## Description
This module handles the management of employees, branches, schedules, and workdays. It is responsible for defining *who* can perform services, *where*, and *when*.

## Architecture
This module follows the **Hexagonal Architecture**.

### Domain Layer
Contains the core entities and business rules.
-   **Entities**:
    -   `EmpleadoJpa`: Represents an employee.
    -   `SucursalJpa`: Represents a branch location.
    -   `EstablecimientoJpa`: Represents the establishment (company).
    -   `HorarioJpa`: Defines working hours.
    -   `EmpleadoHorarioJpa`: Associates an employee with a schedule.
    -   `DiaLaboralJpa`: Defines working days.
    -   `DiaDescansoJpa`: Defines days off.
    -   `BloqueCitaJpa`: Managing appointment blocks/slots.

### Application Layer
Contains the business logic and use cases.
-   **Services**:
    -   `EmpleadoService`: Manages employees.
    -   `SucursalService`: Manages branches.
    -   `EstablecimientoService`: Manages establishment details.
    -   `HorarioService`: Manages schedules.
    -   `EmpleadoHorarioService`: Assigns schedules to employees.
    -   `DiaLaboralService`: Manages working days.
    -   `DiaDescansoService`: Manages days off.
    -   `BloqueCitaService`: Manages time blocks for appointments.

### Infrastructure Layer
-   **Adapters**:
    -   **In**: Web Controllers for HR management.
    -   **Out**: Persistence adapters for HR data.
