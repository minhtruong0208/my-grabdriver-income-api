# My Grab Driver Income API

A backend REST API for tracking driver trips, expenses, and income analytics.

## Features

- Trip management
- Expense management
- Daily income summary
- Monthly income summary
- Pagination support
- Request validation
- Global exception handling
- Unit testing

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- Docker
- JUnit + Mockito

## Project Structure

```text
src/main/java/com/tairitsu/driverincome
├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
├── exception
```

## API Examples

### Create Trip

```http
POST /api/trips
Content-Type: application/json
```

```json
{
  "startTime": "2026-05-14T08:30:00",
  "netIncome": 120000,
  "tip": 20000,
  "distance": 12.5,
  "typeOfTrip": "BIKE"
}
```

### Create Expense

```http
POST /api/expenses
Content-Type: application/json
```

```json
{
  "amount": 50000,
  "typeOfExpense": "FUEL",
  "expenseDate": "2026-05-14T10:00:00",
  "note": "Fuel refill"
}
```

### Daily Income Summary

```http
GET /api/income/daily?date=2026-05-14
```

Response:

```json
{
  "totalTripIncome": 300000,
  "totalExpense": 70000,
  "netIncome": 230000,
  "period": "2026-05-14"
}
```

## Validation Error Example

```json
{
  "code": "VALIDATION_ERROR",
  "message": "Invalid request",
  "errors": {
    "netIncome": "must be greater than 0"
  }
}
```

## Design Notes

This project applies several backend engineering practices, including:

- DTO separation to avoid exposing entities directly
- Layered architecture
- Centralized exception handling
- Pagination support with Spring Data
- Mapper utilities for DTO/entity transformation
- Defensive handling for invalid request data

## Learning Goals

This project was built to practice:

- RESTful API development
- Spring Boot backend architecture
- Validation and exception handling
- Database interaction with JPA
- Writing maintainable backend services
- Unit testing

## Future Improvements

- Swagger/OpenAPI documentation
- Authentication and authorization
- Filtering and search
- Integration testing
- CI/CD pipeline
