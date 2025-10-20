# WW-Two-Spring-Boot-Apps-with-Postgres-and-Docker
Build two small Spring Boot apps (Service A and Service B) that run via docker-compose. Service A handles simple auth with Postgres and exposes a client endpoint that calls Service B. Service B processes the input and returns a result. Service A saves a small record about the processed request.

## Versions
- Java 21
- Maven 3.9+
- Docker Desktop (For launch using docker-compose)
## Create .env file with variables:
- POSTGRES_USER=posgres_user
- POSTGRES_PASSWORD=postgres_password
- JWT_SECRET=your_jwt_secret_key
- INTERNAL_SECRET_TOKEN=your_internal_secret_token

### Variant 1: Using Docker
From root folder run:
```bash
docker-compose up -d --build
```

For stopping
```bash
docker-compose down
```
### Variant 2: Launch on local machine
#### Using IntelliJ IDEA
- Open project and run using Spring Boot files. (AuthApiApplication.java and DataApiApplication.java)

