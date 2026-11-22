# E-banking App

A Java e-banking project developed with Test Driven Development. The project
provides account operations and a REST API for creating, listing, retrieving,
updating, and deleting transactions.

## Technologies

- Java 17
- Spring Boot 3.5
- Spring Web and Spring Data JPA
- H2 in-memory database
- JUnit 5, Spring Boot Test, and MockMvc

## Run the application

From this directory, run:

```bash
mvn spring-boot:run
```

The API is available at `http://localhost:8080/api/transactions`. H2 runs
in-memory and is initialized automatically. The H2 console is available at
`http://localhost:8080/h2-console` while the application is running.

## Transaction API

| Method | Endpoint | Purpose |
| --- | --- | --- |
| POST | `/api/transactions` | Create a transaction |
| GET | `/api/transactions` | List all transactions |
| GET | `/api/transactions/{id}` | Get a transaction by ID |
| PUT | `/api/transactions/{id}` | Update a transaction |
| DELETE | `/api/transactions/{id}` | Delete a transaction |

Create and update requests use JSON like:

```json
{
  "accountId": "account-123",
  "type": "DEPOSIT",
  "amount": 125.50,
  "description": "Monthly deposit"
}
```

Amounts must be greater than zero and may have up to two decimal places.
Unknown transaction IDs return HTTP 404; invalid request data returns HTTP 400.

## Run tests

```bash
mvn clean test
```

The MockMvc integration tests load the Spring application, use the real service
and JPA repository, and persist data in H2. They cover transaction creation,
listing, retrieval, updating, deletion, invalid amounts, and missing IDs.
