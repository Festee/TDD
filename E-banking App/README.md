# E-banking App

A small Java banking application developed with Test Driven Development (TDD).
It supports account balances, deposits, withdrawals, and transfers through a
service layer.

## Technologies

- Java 17
- Maven
- JUnit 5
- Mockito

## Run the tests

Install a Java 17 (or newer) JDK and Maven. From the `E-banking App` directory,
run:

```bash
mvn clean test
```

The test suite covers valid and invalid transactions, withdrawal boundaries,
missing transfer accounts, and mocked repository dependencies. The project is
developed in RED–GREEN–REFACTOR steps; see the Git commit history for the TDD
trail.
