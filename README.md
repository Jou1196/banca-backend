# 🏦 Banca Backend – Spring Boot

Backend del sistema de banca encargado de la gestión de clientes, cuentas y movimientos. Expone APIs REST consumidas desde Postman o frontend y está preparado para ejecución local y dockerizada con PostgreSQL.

## 🚀 Tecnologías
Java 21 · Spring Boot 3.4 · Spring Web · Spring Data JPA · Lombok · PostgreSQL · Maven 3.9+ · Docker · Docker Compose

## 📁 Estructura
banca-backend/
├── src/main/java/com/bolsa/banca_backend
│   ├── controller
│   ├── service
│   ├── repository
│   ├── dto
│   ├── entity
│   └── config
├── src/main/resources
│   └── application.properties
├── db/init/01_BaseDatos.sql
├── Dockerfile
├── docker-compose.yml
├── deploy.sh
├── pom.xml
└── README.md

## 🗄️ Base de Datos
Motor: PostgreSQL  
Base de datos: banca_db  
Usuario: postgres  
Password: 1234  
Puerto: 5432  


## ▶️ Requisitos
Java 21  
Maven 3.9+  
Docker y Docker Compose  

## ▶️ Ejecución local
mvn clean install  
mvn spring-boot:run  

URL: http://localhost:8080

## 🐳 Ejecución con Docker
docker build -t banca-backend:1.0 .  
docker compose up -d  
docker compose logs -f banca-backend  
docker compose down  

## ❤️ Healthcheck
PostgreSQL incluye healthcheck y el backend espera a que la base esté lista antes de arrancar.

## 🔗 Endpoints
GET    /api/clientes  
POST   /api/clientes  
GET    /api/clientes/{id}  
PUT    /api/clientes/{id}  
DELETE /api/clientes/{id}  

GET /api/cuentas?clienteId={uuid}  

POST /api/movimientos/deposito  
POST /api/movimientos/retiro  
GET  /api/movimientos  

## 🧪 Postman
Variable:
baseUrl = http://localhost:8080  

Ejemplo:
GET {{baseUrl}}/api/clientes  

## 🧠 Manejo de errores
400 parámetros inválidos  
404 recurso no encontrado  
500 error interno  

## 🔐 CORS
Permitido desde http://localhost:4200

## 🚀 Deploy automático
Archivo deploy.sh:

#!/bin/bash
set -e
docker compose down -v || true
docker build -t banca-backend:1.0 .
docker compose up -d
docker ps
docker compose logs -f banca-backend

## ✅ Estado
Backend funcional, Dockerizado, PostgreSQL integrada, base inicializada automáticamente y endpoints probados.

## 👨‍💻 Autor
Joseph Arias
