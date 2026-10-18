# FairShare

**Split expenses. Track balances. Settle up fairly.**

FairShare is a web application designed to help friends, roommates, and travel groups manage shared expenses. It keeps track of who paid, how expenses are divided, and who owes money to whom.

The goal of FairShare is to make shared finances transparent and simplify the process of settling debts within a group.

> **Project status:** In development

## Features

### Planned Features

- **User Management** — Register and authenticate users.
- **Expense Groups** — Create groups for trips, shared households, events, and other activities.
- **Group Members** — Add members to groups and manage membership.
- **Expense Tracking** — Record expenses, including the amount, description, category, payer, and date.
- **Flexible Expense Splitting** — Divide expenses equally or assign custom shares to participants.
- **Input Validation** — Validate incoming data and provide meaningful error responses.
- **Balance Calculation** — Automatically calculate each member's net balance.
- **Settlement Suggestions** — Minimize the number of transfers needed to settle outstanding debts.
- **Settlement Tracking** — Record completed payments between group members.
- **Expense Overview** — View group expenses, spending summaries, and outstanding balances.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Core programming language |
| Spring Boot 4.1.1 | Application framework |
| Spring Web MVC | REST API development |
| Spring Data JPA | Database access and persistence |
| Hibernate | Object-relational mapping |
| Spring Security | Authentication and authorization |
| Spring Boot Validation | Request validation |
| PostgreSQL | Relational database |
| Flyway | Database schema migrations |
| Maven | Dependency management and build automation |
| JUnit 5 | Automated testing |
| Mockito | Mocking dependencies in tests |
| Testcontainers | Integration testing with PostgreSQL |
| Docker | Application containerization |
| Docker Compose | Local development environment |
| Git and GitHub | Version control and project hosting |

Additional technologies, such as Redis and Apache Kafka, may be introduced in later iterations if they solve specific architectural problems.

## Architecture

FairShare will initially be developed as a **modular monolith** using a layered architecture.

The application will be organized around the following responsibilities:

- **Controller Layer** — Handles HTTP requests and responses.
- **Service Layer** — Implements business logic and expense calculations.
- **Repository Layer** — Manages database operations through Spring Data JPA.
- **Domain Layer** — Represents users, groups, expenses, shares, and settlements.
- **Security Layer** — Handles authentication and access control.
- **Database Layer** — Stores application data in PostgreSQL.

This approach keeps the initial implementation simple while allowing individual modules to evolve independently as the project grows.

## Core Domain Model

The initial domain model will include the following entities:

| Entity | Description |
|---|---|
| `User` | Represents a registered user. |
| `ExpenseGroup` | Represents a group that shares expenses. |
| `GroupMember` | Represents a user's membership in a group. |
| `Expense` | Represents an expense recorded within a group. |
| `ExpenseShare` | Represents an individual member's share of an expense. |
| `Settlement` | Represents a payment recorded to settle a debt. |

The domain model may evolve as additional requirements are identified during development.

## How Expense Splitting Works

FairShare calculates the difference between the amount each member has paid and the amount they are responsible for.

For example, consider a group of three people:

| Member | Amount Paid | Expense Share | Net Balance |
|---|---:|---:|---:|
| Alice | €90.00 | €45.00 | +€45.00 |
| Bob | €30.00 | €45.00 | -€15.00 |
| Charlie | €0.00 | €30.00 | -€30.00 |

A positive balance means that a member should receive money. A negative balance means that a member owes money.

In this example:

- Bob pays Alice €15.00.
- Charlie pays Alice €30.00.

After these transfers, all group members have a net balance of zero.

FairShare will use `BigDecimal` for monetary calculations instead of floating-point types. Expense splitting will also account for rounding and ensure that the sum of individual shares equals the original expense amount.

## Getting Started

The application is currently under development. Setup instructions will be expanded as the initial implementation becomes available.

### Prerequisites

The planned development environment requires:

- Java Development Kit (JDK) 21
- Maven
- PostgreSQL
- Git
- Docker and Docker Compose (for containerized development and testing)

### Installation

Clone the repository:

```bash
git clone <repository-url>
cd fairshare
```

Build the project using Maven:

```bash
./mvnw clean package
```

On Windows, use:

```bash
mvnw.cmd clean package
```

The database configuration and application startup instructions will be documented once the initial setup is complete.

## Database Migrations

FairShare uses Flyway to manage database schema changes.

Each migration is stored as a versioned SQL file. For example:

```text
src/main/resources/db/migration/
├── V1__create_users_table.sql
├── V2__create_expense_groups_table.sql
└── V3__create_expenses_table.sql
```

Flyway applies pending migrations in order, keeping the database schema consistent across development and testing environments.

## Validation

FairShare uses Spring Boot Validation to validate incoming request data.

Examples include:

- Required fields must not be blank.
- Expense amounts must be greater than zero.
- Request data must satisfy defined constraints.

Business rules, such as verifying group membership or ensuring that expense shares add up to the total amount, will be enforced in the service layer.

## Testing

FairShare will use multiple testing levels:

- **Unit Tests** — Verify business rules and balance calculations.
- **Service Tests** — Verify application behavior and interactions between components.
- **Integration Tests** — Verify persistence and database behavior using PostgreSQL and Testcontainers.
- **API Tests** — Verify HTTP endpoints, validation, and access control.

Particular attention will be given to monetary precision, expense splitting, settlement correctness, and access restrictions between groups.

## Project Goals

FairShare is a personal backend development project intended to demonstrate practical experience with:

- Java and Spring Boot application development.
- REST API design.
- Relational database modeling and persistence.
- Business logic implementation and algorithmic problem-solving.
- Input validation and error handling.
- Authentication and authorization.
- Automated testing.
- Database migrations.
- Containerization and application deployment.
- Clean code and maintainable architecture.

## License

The license has not yet been selected. Licensing information will be added before the project is published for reuse.