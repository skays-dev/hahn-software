# Full Stack Application – Spring Boot + React.js

This is a full-stack web application using **Spring Boot** for the backend, **React.js** for the frontend, and **MySQL** as the database. The app demonstrates basic product management with clean code, error handling, unit testing, and containerization using Docker.

---

## 🚀 Features

### ✅ Backend (Spring Boot)
- RESTful API using Spring Boot 3 (`(GET) /api/products`, `(GET) /api/products/{id}`, `(POST) /api/products`, `(PUT) /api/products/{id}`, `(DELETE) /api/products/{id}`)
- JPA/Hibernate for ORM and DB operations
- `Product` entity with CRUD endpoints
- Input validation
- Global error handling

### ✅ Frontend (React.js)
- Functional components with React Hooks
- React Router for navigation (`/products`, `/products/new`, `/products/edit/:id`)
- Axios for API communication
- Product form (add/edit) and list view
- Delete/Edit options on list
- State managed using Context API

### ✅ Database
- MySQL for data storage
- Initial schema provided via `schema.sql`

### ✅ Git & Clean Code
- Modular code with separation of concerns
- Clean and readable naming conventions
- Meaningful and granular commit messages

### ✅ Bonus
- Dockerfile for backend and frontend
- Unit tests with JUnit

---
## 📦 Installation

```bash
## 📦 Frontend Setup (React.js)


cd frontend
npm install
npm run dev         # Starts the React dev server
npx jest            # Runs frontend unit tests

## 📦 Backend Setup (Spring Boot)

```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run

## 📦 Build & Containerization

### ✅ Step 1
```bash
./docker pull mysql:latest
./docker network create hahn
./docker run --name mysqldb --network hahn  -e MYSQL_ROOT_PASSWORD=root  -e MYSQL_DATABASE=hahn_software  -d mysql:latest

### ✅ Step 2
```bash
cd backend
./docker build -t hahn-software-backend .
./docker run --network hahn --name hahn-back-end-container -p 8080:8080 -d hahn-software-backend

### ✅ Step 3
```bash
cd ../frontend
./docker build -t hahn-software-backend .
./docker run --network hahn --name hahn-front-end-container -p 5173:5173 -d hahn-software-backend
