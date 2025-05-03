# Spring Boot API Application

A RESTful API service built with Spring Boot, featuring JWT authentication and Swagger documentation.

## Features

- JWT-based authentication
- Swagger API documentation
- RESTful endpoints
- Database integration
- Exception handling
- Validation
- Logging
- Flyway

## Prerequisites

- Java 17.x.x
- Gradle 8.x
- PostgreSQL 12+

## Installation
git clone the repo

Create a PostgreSQL database

Update application.properties with your credentials

Build the application

Swagger UI: http://localhost:8080/swagger-ui/index.html

OpenAPI JSON: http://localhost:8080/v3/api-docs

Authentication
First obtain a JWT token from the /auth/get-access-token

Click the "Authorize" button in Swagger UI

Enter: Bearer your.jwt.token.here

Add, update, delete, and get institutions 

Users granted permissions can be created at /user

Database Schema
The application uses these main tables:

sql
CREATE TABLE public.institution (
    id BIGSERIAL PRIMARY KEY,
    code BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    status INTEGER NOT NULL
);

CREATE TABLE public.user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status INTEGER NOT NULL
);

Project Structure
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── mdsl/
│   │         └── onboarding/
|   |               └── common/
│   │                  ├── config/       # Configuration classes
|   |                  ├── enums/       # Common Enums
│   │                  ├── response/      # Common API Responses
│   │                  ├── exceptions/    # Exception handling
│   │                  ├── service/      # Common Services
│   │                  ├── utils/       # Common Utils
|   |                  └── BaseEntity.java    # Common Entity Fields
|   |               └── institution/
│   │                  ├── controllers/       # Rest Controllers
|   |                  ├── services/       # Institution Services
│   │                  ├── repositories/      # Institution Database Repositories
│   │                  ├── dtos/    # Data transfer objects
│   │                  ├── models/      # Tables
│   │              └── security/     # Security configuration
│   │                  ├── config/       # Configuration classes
|   |                  ├── controllers/       # Rest Controllers for users and authentication
│   │                  ├── repositories/      # Database Repositories
│   │                  ├── models/       # Tables
│   │                  ├── services/      # Security Functionalities
│   │                  ├── utils/       # Utils
|   |                  ├── dtos/        # Data transfer objects
|   |                  ├── AuthEntryPointJwt.java  
|   |                  └── AuthTokenFilter.java
│   │              └── OnboardingApplication.java
│   └── resources/
│       └── db/
|         └── migration/
│       ├── logback-spring.xml/
│       └── application.properties
└── test/        # Test classes

This README provides:
- Clear setup instructions
- API documentation details
- Database information
- Project structure overview
