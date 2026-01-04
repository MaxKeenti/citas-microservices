# Citas Monolithic Application

## Overview
This is a **Modular Monolith** application designed for managing appointments, customers, and human resources for a service-based business. It handles everything from user authentication to service catalog management and employee scheduling.

## Architecture
The project is structured as a **Modular Monolith**, meaning all modules reside in a single deployable unit but are logically separated.
Internally, each module follows the **Hexagonal Architecture** (also known as Ports and Adapters) pattern, promoting a clean separation of concerns:

-   **Domain**: The core business logic and entities, independent of frameworks.
-   **Application**: Services and use cases that orchestrate the domain logic.
-   **Infrastructure**: Adapters that interface with external concerns (Database, UI/Web, Third-party services).

## Modules
The application is divided into the following key feature modules:

-   **[Access Control](src/main/java/mx/ipn/upiicsa/web/accesscontrol/README.md)**: Authenticates users and manages roles/permissions.
-   **[Appointment](src/main/java/mx/ipn/upiicsa/web/appointment/README.md)**: Manages the scheduling and lifecycle of appointments.
-   **[Catalog](src/main/java/mx/ipn/upiicsa/web/catalog/README.md)**: Manages services, price lists, and their statuses.
-   **[Human Resources](src/main/java/mx/ipn/upiicsa/web/hresources/README.md)**: Manages employees, branches, schedules, and workdays.

## Technologies
-   **Language**: Java 21
-   **Framework**: Spring Boot 3.3.1
-   **Database**: PostgreSQL 42.7
-   **Template Engine**: Thymeleaf
-   **Utilities**: Lombok, Vavr

## Getting Started

### Prerequisites
-   Java 21 JDK
-   Maven
-   PostgreSQL
-   Docker (Optional, for running database)

### Running the Application
1.  **Clone the repository**.
2.  **Configure Database**: Ensure PostgreSQL is running and credentials in `src/main/resources/application.properties` are correct.
3.  **Build**:
    ```bash
    ./mvnw clean install
    ```
4.  **Run**:
    ```bash
    ./mvnw spring-boot:run
    ```

## Development
To add a new feature, identify the relevant module or create a new one following the package structure:
`mx.ipn.upiicsa.web.<module>.<layer>`
