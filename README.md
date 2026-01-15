# 🧠 Banca Backend – Spring Boot

Backend del sistema de banca encargado de la gestión de clientes, cuentas, movimientos y generación de reportes en PDF.  
Expone APIs REST documentadas con Swagger/OpenAPI y es consumido por el frontend Angular.

## 🚀 Tecnologías
Java 17 · Spring Boot 3 · Spring Web · Spring Data JPA · Lombok  
OpenAPI / Swagger · iText / PDF · Docker · Maven

## 📁 Estructura
banca-backend/
src/main/java/com/bolsa/banca_backend
├── controller
├── service
├── repository
├── dto
├── entity
└── config

## ▶️ Requisitos
Java 17  
Maven 3.9+  
Docker (opcional)

## ▶️ Ejecución local
mvn clean install  
mvn spring-boot:run

Aplicación:
http://localhost:8080

## 📘 Swagger / OpenAPI
Documentación automática de las APIs:

http://localhost:8080/swagger-ui.html  
o  
http://localhost:8080/swagger-ui/index.html

## 🔗 Endpoints principales

### Customers
GET /customers  
Obtiene el listado de clientes.

### Accounts
GET /accounts?customerId={uuid}  
Obtiene las cuentas de un cliente.

### Movements
GET /movements?accountId={uuid}  
Obtiene los movimientos de una cuenta.

### Reports
GET /reports?customerId={uuid}&from=yyyy-MM-dd&to=yyyy-MM-dd  
Genera el reporte de un cliente en un rango de fechas.

## 📄 Reportes PDF
El backend genera reportes PDF con:
- Datos del cliente
- Cuentas asociadas
- Movimientos por rango de fechas
- Totales y formato legible

El frontend descarga el PDF directamente.

## 🧪 Manejo de errores
Respuestas consistentes con:
- 400: parámetros inválidos
- 404: cliente / datos no encontrados
- 500: error interno

## 🐳 Docker

### Dockerfile
FROM eclipse-temurin:17-jdk-alpine  
WORKDIR /app  
COPY target/*.jar app.jar  
EXPOSE 8080  
ENTRYPOINT ["java","-jar","/app/app.jar"]

### Build
mvn clean package  
docker build -t banca-backend .

### Run
docker run -p 8080:8080 banca-backend

## 🔐 CORS
Configurado para permitir consumo desde:
http://localhost:4200

## ✅ Estado
Backend funcional, APIs documentadas con Swagger, generación de PDFs operativa, integración completa con frontend Angular.

## 👨‍💻 Autor
Banca Backend – Spring Boot  
Sistema de Banca
