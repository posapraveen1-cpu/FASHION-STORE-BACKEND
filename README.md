# FORMÉ fashion store API

Spring Boot 3 / Java 17 REST API with MySQL persistence, Flyway migrations, BCrypt password hashing, and short-lived signed bearer tokens.

## Run locally

1. Install Java 17+, Maven, and MySQL.
2. Create a MySQL account/database or use the default local settings in `src/main/resources/application.properties`.
3. Start the API with `mvn spring-boot:run` from this directory.
4. Serve `fashion-store-frontend` on `http://localhost:5500`.

Configure `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`, `FRONTEND_ORIGIN`, and `JWT_SECRET` through environment variables. Change the development JWT secret before deployment. Flyway creates tables and seeds a small sample catalog at startup.

To grant an account administrative access, first register it, then promote that account in MySQL, for example:

```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'admin@example.com';
```

## API overview

- `POST /api/auth/register`, `POST /api/auth/login`
- `GET /api/products`, `GET /api/products/{id}`, `GET /api/categories`
- Authenticated: `/api/cart`, `POST /api/orders`, `GET /api/orders/my`
- Admin role: `/api/admin/products`, `/api/admin/orders`

Send the returned JWT as `Authorization: Bearer <token>` for authenticated routes. Admin endpoints are protected by the `ADMIN` role.
