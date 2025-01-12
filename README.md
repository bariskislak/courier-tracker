# Migros Case

## Description

This project is built using **Spring Framework** for backend development, **Jakarta EE imports** for enterprise-grade
web applications, and integrates with additional technologies like **Redis** for caching and **SQL** for persistent data
storage. It is structured to provide a robust, scalable, and efficient system.

## Features

- Backend implemented using **Spring MVC** and **Spring Data JPA**.
- Utilizes **Redis** for caching to boost performance.
- Persists data with a relational **SQL** database.
- Built with **Jakarta EE** standards for enterprise application development.
- Dependency management is handled with Maven.
- Packaged and configured for deployment using Docker.

## Technologies Used

- **Java 21** 
- **Spring Framework Version 3.4.1**
   - Spring MVC
   - Spring Data JPA
- **Jakarta EE**
- **SQL** (e.g., PostGis)
- **Redis**
- **Docker** (Docker Compose for orchestration)
- **Lombok** (to reduce boilerplate code)
- **Swagger** 
---

## Quickstart Guide

### Prerequisites

Ensure the following tools and dependencies are installed on your system:

- Java JDK 21
- Maven
- Docker & Docker Compose

### Build & Run

#### Using Docker Compose

1. Clone the repository:

   ```bash
   git clone https://github.com/bariskislak/courier-tracker.git
   cd courier-tracker
   ```

2. Build the Docker images and start the services:

   ```bash
   docker-compose up --build
   ```

   This will automatically set up the project services, including the backend, database (SQL), and Redis.

3. The application should now be accessible at:

   ```
   http://localhost:8080
   ```

#### Without Docker

1. Make sure your SQL database and Redis server are running locally.
2. Configure your `application.properties` file with the appropriate database and Redis connection details.
3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

---

## Configuration

The following environment variables can be configured for customized settings:

| Variable      | Description                 | Default Value                      |
|---------------|-----------------------------|------------------------------------|
| `DB_URL`      | SQL database connection URL | `jdbc:mysql://localhost:5432/mydb` |
| `DB_USERNAME` | SQL database username       | `sa`                               |
| `DB_PASSWORD` | SQL database password       | `password`                         |
| `REDIS_HOST`  | Redis server hostname       | `localhost`                        |
| `REDIS_PORT`  | Redis server port           | `6379`                             |

These values can be updated in the `.env` file (used by Docker Compose) or directly in the `application.properties`.

---

## Testing the Application

1. Use **Postman** or **cURL** to interact with the RESTful API endpoints provided by the backend.
2. Also you can use http://localhost:8080/swagger-ui/index.html after successfully run the application on your local
3. Automated tests can be run using:

   ```bash
   mvn test
   ```

---

## Dependencies

The project uses the following core dependencies:

### Maven Dependencies

- `org.springframework.boot` (Spring Boot Starter)
- `org.springframework.data` (Spring Data JPA)
- `jakarta.persistence` (Persistence API)
- `mysql-connector` or `postgresql` (or your chosen SQL driver)
- `redis.clients` (Jedis/Lettuce for Redis integration)
- `lombok` (for reducing boilerplate code)

---

## Further Questions?

For any additional queries or support, feel free to reach out:

**Email**: [0bariskislak@gmail.com](mailto:0bariskislak@gmail.com)