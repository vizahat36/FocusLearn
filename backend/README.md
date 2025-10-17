# FocusLearn Backend

This is the backend for the FocusLearn project, built with Java Spring Boot.

## Structure
- `src/main/java` - Java source code
- `src/main/resources` - Application resources
- `src/test/java` - Test code

## Getting Started
1. Import this folder as a Maven/Gradle project in your IDE.
2. Build and run the application.

## Requirements
- Java 17+
- Maven or Gradle

## To Run
```
mvn spring-boot:run
```

or

```
gradle bootRun
```

## Running with Postgres vs. Dev mode

- By default the app uses a development-friendly configuration (no Flyway migrations, Hibernate auto-update). This lets the app start even when Postgres is not available.
- To run against Postgres and apply Flyway migrations, start the application with the `postgres` profile:

```powershell
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

Make sure Postgres is running and the connection properties in `src/main/resources/application-postgres.properties` are correct before using the `postgres` profile.

To run against MySQL and apply MySQL-compatible Flyway migrations, start the application with the `mysql` profile:

```powershell
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

Ensure MySQL is running and the connection properties in `src/main/resources/application-mysql.properties` are correct before using the `mysql` profile.
