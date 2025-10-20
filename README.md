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
##### Create two values.yml files in resources folders of both projects:
For data-api module:
```yaml
internal:
  secret:
    token: your_token
```
For auth-api module:
```yaml
jwt:
  secret: your_jwt_secret_key

database:
  url: your_database_url
  username: your_database_username
  password: your_database_password

internal:
  token: your_token
```
Build both modules using Maven:
In root folder run:
```bash
mvn clean install
```
To run each module:
```bash
mvn spring-boot:run -pl auth-api
```
```bash
mvn spring-boot:run -pl data-api
```
- Open project and run using Spring Boot files. (AuthApiApplication.java and DataApiApplication.java)

