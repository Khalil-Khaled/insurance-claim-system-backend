# 🏗️ Architecture Design — Insurance Claims System

## 🧭 Architecture Style

The system is designed as a **Modular Monolith** using Spring Boot.

This means:
- One deployable application
- Clear module separation by business domain
- Strict internal boundaries between modules

---

## 📦 Base Package Structure

The root package is:

com.insurance.claim_system

Modules:

- user
- contract
- claim
- security
- common
- file (for document upload abstraction)

---

## 🧱 Internal Module Structure

Each business module follows this structure:

```
module/
 ├── controller
 ├── service
 ├── repository
 ├── entity
```

DTOs and mappers are separated (global or per module depending on usage).

---

## 🔄 Layer Responsibilities

### Controller Layer
- Exposes REST endpoints
- Handles request/response only
- No business logic

---

### Service Layer
- Contains business logic
- Coordinates repositories
- Handles validation rules
- Orchestrates workflows

---

### Repository Layer
- Database access using Spring Data JPA
- No business logic

---

### Entity Layer
- Database models
- JPA annotations
- Relationships between tables

---

## 📦 DTO Strategy

DTOs are used to avoid exposing entities directly.

We use:
- Java Records for DTOs (immutability + simplicity)

Example usage:
- Request DTOs
- Response DTOs

Mapping is handled using MapStruct.

---

## 🔁 Mapping Strategy

We use MapStruct for entity ↔ DTO mapping.

Benefits:
- Compile-time safety
- No reflection overhead
- Clean separation of layers

Mapping rules:
- Entities are never returned directly from controllers
- Services always return DTOs or domain models

---

## 🔐 Security Architecture Overview

Authentication is based on:

- Email + password login
- JWT token generation

### Security Flow

1. User logs in
2. Credentials validated
3. JWT generated and returned
4. Client sends JWT in Authorization header
5. Security filter validates token
6. User context is set in Spring Security context

---

## 🔑 JWT Design (Option B)

JWT contains:

- userId
- email
- role
- issuedAt
- expiration
- issuer

JWT is stateless and signed using a secret key.

---

## 🛡️ Authorization Model

Roles:

- ROLE_CLIENT
- ROLE_ADMIN

Access rules:
- CLIENT → own resources only
- ADMIN → global access

---

## 📁 File Upload Architecture (IMPORTANT)

We design a pluggable storage system.

### Goal
Avoid coupling business logic with local filesystem.

---

## 📂 Storage Strategy (Phase 1)

Local filesystem storage:

/uploads/{claimId}/{fileName}

---

## 🧩 Abstraction Layer Design

We introduce a storage interface:

- FileStorageService (interface)

Implementations:
- LocalFileStorageService (current)
- S3FileStorageService (future)
- MinioFileStorageService (future)

---

## 📦 File Module Responsibility

The file module handles:

- Uploading files
- Retrieving files
- Storing metadata
- Linking files to claims

It does NOT contain business rules about claims.

---

## 🧠 Dependency Rules

Strict rules:

- claim module depends on file module (interface only)
- file module does NOT depend on claim module
- security is independent
- common module contains shared utilities only

---

## 🗄️ Database Design Principles

We use relational modeling:

- One-to-many relationships for:
  - User → Contracts
  - Contract → Claims
  - Claim → Documents

Constraints:
- Foreign keys enforced
- Lazy loading by default
- No circular dependencies

---

## 🚫 Forbidden Practices

- No business logic in controllers
- No entity exposure in API responses
- No direct filesystem calls outside file module
- No security logic inside business modules

---

## 🚀 Future Evolution (Optional)

If extended later:

- Microservice extraction (File service or Claim service)
- Keycloak integration for IAM
- Cloud storage (AWS S3 / MinIO)
- Event-driven architecture (Kafka)
- API Gateway layer

---