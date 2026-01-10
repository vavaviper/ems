# Employee Management System

The **Employee Management System** is a robust CRUD RESTful API built using Spring Boot, PostgreSQL, Spring Data JPA, and Hibernate. This project demonstrates the effective integration of these technologies to create a scalable and efficient backend solution.

## Technologies Used

- **Spring Boot**: Streamlines the development of Spring applications with a convention-over-configuration approach, enabling rapid development and a focus on business logic rather than setup.
- **PostgreSQL**: A powerful relational database system used for its advanced capabilities, including reliable transactions and concurrent access management.
- **Spring Data JPA**: Facilitates the implementation of JPA-based repositories, simplifying data access and management within the relational database.
- **Hibernate**: Handles object-relational mapping (ORM) to manage data operations such as create, retrieve, update, and delete (CRUD) with efficiency and ease.
- **Postman**: A tool used for testing and interacting with the API. Postman provides a user-friendly interface to make HTTP requests, validate responses, and automate testing workflows. A pre-configured Postman collection is available to test the various API endpoints.

---

## Endpoints

- CRUD employees (base `/api/employees`)

  - `POST /api/employees` — create an employee
  - `GET /api/employees` — list employees
  - `GET /api/employees/{id}` — get employee by id
  - `PUT /api/employees/{id}` — update employee
  - `DELETE /api/employees/{id}` — delete employee

- Business Intelligence
  - `GET /api/v1/employees/stats` — returns department stats (`DepartmentDTO`): `department`, `employeeCount`, `averageSalary`

## Monitoring & API Docs

- Spring Boot Actuator (exposed endpoints: `health`, `info`)
  - `GET /actuator/health` — service health (UP/DOWN)
  - `GET /actuator/info` — application info (empty by default; consider adding build/git info)
- Swagger / OpenAPI UI (springdoc):
  - `http://localhost:8080/swagger-ui/index.html`

> **Security note:** Expose and secure Actuator and Swagger endpoints appropriately in production (e.g., with Spring Security and role-based access).

## Audit Logging

- Audit records are stored in the `audit_log` table (entity `AuditLog`) with columns: `id`, `action`, `employee_id`, `timestamp`, `changed_by`.
- An AOP aspect records audit entries automatically for `EmployeeService` methods that start with `update` or `delete` (uses `@AfterReturning` advice).
- `changed_by` currently defaults to `system` — integrate Spring Security to set the authenticated principal.

## Testing & Smoke checks

- Run tests: `./mvnw test` or `mvn test`
- Smoke test example:
  - POST -> PUT -> DELETE an employee and verify entries in `audit_log`:
    - `PGPASSWORD='Bob123??' psql -U postgres -d ems -c "SELECT id, action, employee_id, changed_by, timestamp FROM audit_log ORDER BY id DESC LIMIT 10;"`

## Next steps & recommendations

- Add an admin endpoint to query audit logs (paginated)
- Add integration tests for the audit aspect (Testcontainers)
- Secure actuator and docs endpoints for production

## Features

- **Comprehensive CRUD Operations**: Full support for create, read, update, and delete operations through a well-defined API.
- **Database Integration**: Utilizes PostgreSQL for reliable data storage and management.
- **Enhanced Data Access**: Leverages Spring Data JPA for streamlined data interactions.
- **Efficient ORM**: Employs Hibernate for seamless object-relational mapping.

## Getting started

**Prerequisites**

- Java 21 (set `JAVA_HOME` accordingly)
- Maven (or use the project Maven wrapper `./mvnw`)
- PostgreSQL running locally (default URL: `jdbc:postgresql://localhost:5432/ems`)

**Run the app**

1. Ensure PostgreSQL is running and create the `ems` DB if needed:
   - `psql -U postgres -c "CREATE DATABASE ems;"`
   - `psql -U postgres -c "ALTER USER postgres WITH PASSWORD 'Bob123??';"` (or update `src/main/resources/application.properties`)
2. Run using Maven wrapper:
   - `chmod +x mvnw && ./mvnw spring-boot:run`
   - or with system Maven: `mvn spring-boot:run`
3. Build the JAR:
   - `./mvnw -DskipTests package`
