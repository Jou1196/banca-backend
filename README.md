# 🏦 Banca Backend – Arquitectura de Microservicios con Spring Boot

Backend del sistema de banca desarrollado con **Spring Boot** bajo una arquitectura basada en **microservicios**.  
El sistema permite gestionar **clientes, cuentas, movimientos y reportes de estado de cuenta** mediante APIs REST.

Cada microservicio funciona de forma **independiente**, se ejecuta en **contenedores Docker**, comparte una base de datos **PostgreSQL** y se comunica con otros servicios utilizando **Feign Clients**.

El backend es consumido por el **frontend Angular CRM**.

---

# 🏗️ Arquitectura del Sistema

El sistema está dividido en los siguientes microservicios:

| Servicio | Puerto | Descripción |
|--------|--------|-------------|
| customer-service | 8081 | Gestión de clientes |
| account-service | 8082 | Gestión de cuentas |
| movement-service | 8083 | Gestión de movimientos |
| report-service | 8084 | Generación de reportes de estado de cuenta |

Todos los servicios se ejecutan mediante **Docker Compose** dentro de una misma red.

---

# 🔗 Comunicación entre Microservicios

Los microservicios se comunican entre sí mediante **Spring Cloud OpenFeign**.

Ejemplo de flujo para generar un reporte:

```
Frontend Angular
        │
        ▼
report-service
        │
        ├── consulta cuentas → account-service
        │
        ├── consulta cliente → customer-service
        │
        └── consulta movimientos → movement-service
```

Ejemplo de llamada interna:

```
http://movement-service:8083/api/movements/account/{accountId}?from=yyyy-MM-dd&to=yyyy-MM-dd
```

Docker resuelve automáticamente el nombre del servicio dentro de la red interna.

---

# 🚀 Tecnologías

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- OpenFeign
- Lombok
- PostgreSQL
- Maven
- Docker
- Docker Compose
- Swagger (Springdoc OpenAPI)

---

# 📁 Estructura del Proyecto

```
banca-backend
│
├── customer-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   └── dto
│
├── account-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   └── dto
│
├── movement-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   └── dto
│
├── report-service
│   ├── controller
│   ├── service
│   ├── client (Feign)
│   └── dto
│
├── docker-compose.yml
├── Dockerfile
└── README.md
```

Cada microservicio es una aplicación **Spring Boot independiente**.

---

# 🗄️ Base de Datos

Motor: **PostgreSQL**

Configuración:

```
Database: banca_db
User: postgres
Password: 1234
Port: 5432
```

La base de datos se ejecuta dentro de un contenedor Docker.

Todos los microservicios se conectan a la misma base de datos.

---

# ▶️ Requisitos

Para ejecutar el sistema se requiere:

- Java 21
- Maven
- Docker
- Docker Compose

---

# ▶️ Ejecución Local (sin Docker)

Cada microservicio puede ejecutarse de forma independiente.

Ejemplo:

```
mvn clean install
mvn spring-boot:run
```

Puertos utilizados:

```
customer-service  → 8081
account-service   → 8082
movement-service  → 8083
report-service    → 8084
```

---

# 🐳 Ejecución con Docker

Para levantar toda la arquitectura:

```
docker compose up --build -d
```

Ver contenedores en ejecución:

```
docker ps
```

Ver logs:

```
docker compose logs -f
```

Detener los servicios:

```
docker compose down
```

---

# 📘 Documentación de APIs – Swagger

Cada microservicio incluye **Swagger UI** mediante **Springdoc OpenAPI**, lo que permite:

- Visualizar todos los endpoints disponibles
- Ver los parámetros de cada API
- Ejecutar las peticiones directamente desde el navegador
- Analizar las respuestas de cada servicio

Swagger facilita el proceso de **pruebas y documentación de las APIs**.

### URLs de Swagger

```
Customer Service
http://localhost:8081/swagger-ui/index.html

Account Service
http://localhost:8082/swagger-ui/index.html

Movement Service
http://localhost:8083/swagger-ui/index.html

Report Service
http://localhost:8084/swagger-ui/index.html
```

También es posible acceder a la especificación OpenAPI en formato JSON:

```
http://localhost:8081/v3/api-docs
http://localhost:8082/v3/api-docs
http://localhost:8083/v3/api-docs
http://localhost:8084/v3/api-docs
```

Esto permite integraciones con herramientas externas como **Postman, Swagger Editor o herramientas de testing**.

---

# 🔗 Principales Endpoints

## Customer Service

```
GET    /api/customers
GET    /api/customers/{id}
POST   /api/customers
DELETE /api/customers/{id}
```

---

## Account Service

```
GET    /api/accounts
GET    /api/accounts/{id}
GET    /api/accounts/por-cliente/{customerId}
POST   /api/accounts
DELETE /api/accounts/{id}
```

---

## Movement Service

```
GET  /api/movements
GET  /api/movements/account/{accountId}
POST /api/movements/deposit
POST /api/movements/withdraw
```

---

## Report Service

Genera el estado de cuenta de un cliente consultando múltiples microservicios.

```
GET /api/reportes/estado-cuenta?customerId={uuid}&from=yyyy-MM-dd&to=yyyy-MM-dd
```

Proceso:

1. Obtiene las cuentas del cliente desde **account-service**
2. Obtiene la información del cliente desde **customer-service**
3. Consulta los movimientos desde **movement-service**
4. Genera el estado de cuenta consolidado

---

# 🧠 Manejo de Errores

El sistema maneja errores estándar HTTP:

```
400 → Parámetros inválidos
404 → Recurso no encontrado
500 → Error interno del servidor
```

Las validaciones se realizan mediante **Jakarta Validation**.

---

# 🔐 CORS

Los microservicios permiten acceso desde el frontend Angular.

```
http://localhost:4200
```

---

# 🧪 Pruebas

Las APIs pueden probarse con:

- Swagger UI
- Postman
- Curl

Ejemplo:

```
GET http://localhost:8084/api/reportes/estado-cuenta?customerId={uuid}&from=2026-03-01&to=2026-03-05
```

---

# ⚙️ Infraestructura Docker

Docker Compose levanta los siguientes contenedores:

- PostgreSQL
- customer-service
- account-service
- movement-service
- report-service

Todos los servicios comparten una red interna Docker.

---

# ✅ Estado del Proyecto

✔ Arquitectura basada en microservicios  
✔ Comunicación entre servicios con Feign  
✔ PostgreSQL integrado  
✔ Docker Compose para despliegue completo  
✔ APIs documentadas con Swagger  
✔ Integración con frontend Angular CRM  

---

# 👨‍💻 Autor

Joseph Arias  
Sistema de Banca – Backend
