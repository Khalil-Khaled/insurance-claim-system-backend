# 🧱 Domain Model — Insurance Claims System

## 🧭 Overview

This document defines the core domain entities, relationships, and rules of the Insurance Claims Management System.

It represents the persistent data model used with JPA/Hibernate and PostgreSQL.

---

## 👤 User Entity

### Purpose
Represents a system user (client or admin).

### Fields
- id (Long)
- email (String, unique)
- password (String, encrypted)
- role (enum: ROLE_CLIENT, ROLE_ADMIN)
- enabled (boolean)

### Relationships
- One User → Many Contracts

---

## 📄 Contract Entity

### Purpose
Represents an insurance contract owned by a user.

### Fields
- id (Long)
- contractType (enum: AUTO, HOME, HEALTH)
- startDate (LocalDate)
- endDate (LocalDate)

### Relationships
- Many Contracts → One User
- One Contract → Many Claims

---

## 📊 Claim Entity

### Purpose
Represents an insurance claim submitted by a user.

### Fields
- id (Long)
- reference (String, unique business identifier)
- description (String)
- status (enum: CREATED, IN_PROGRESS, APPROVED, REJECTED)
- createdAt (LocalDateTime)

### Relationships
- Many Claims → One Contract
- One Claim → Many Documents

---

## 📁 Document Entity (Advanced Model)

### Purpose
Represents a file uploaded for a claim, stored via an abstraction layer.

### Fields
- id (Long)
- fileName (String)
- fileType (String)
- fileSize (Long)
- storagePath (String)
- checksum (String, optional integrity validation)
- uploadedAt (LocalDateTime)
- uploadedBy (String or userId reference)

### Relationships
- Many Documents → One Claim

---

## 🔗 Entity Relationships Summary
```
User
  └── 1 → N Contracts
          └── 1 → N Claims
                  └── 1 → N Documents
```

---

## 📊 Enumerations

### Role Enum
- ROLE_CLIENT
- ROLE_ADMIN

---

### ContractType Enum
- AUTO
- HOME
- HEALTH

---

### ClaimStatus Enum

CREATED → IN_PROGRESS → APPROVED → REJECTED

Rules:
- Only ADMIN can modify status
- CLIENT can only create and view claims

---

## 🧠 Domain Rules

### User Rules
- Email must be unique
- Password must be encrypted
- Role defines access level

---

### Contract Rules
- A contract must belong to exactly one user
- Contract dates must be valid (start < end)

---

### Claim Rules
- Must be linked to a valid contract
- Reference must be unique
- Status changes restricted to ADMIN

---

### Document Rules
- Must belong to a valid claim
- Stored via storage abstraction layer
- File metadata must be persisted in database
- Physical storage is decoupled from database

---

## 📁 File Storage Strategy

### Storage Abstraction Layer

The system uses a pluggable storage system:

Interface:
- FileStorageService

Implementations:
- LocalFileStorageService (current)
- S3FileStorageService (future)
- MinioFileStorageService (future)

---

### Local Storage Structure

/uploads/{claimId}/{fileName}

---

## 🧩 Design Principles

- Entities are persistence-focused (not API models)
- No business logic inside entities
- Relationships are bidirectional only when necessary
- Lazy loading preferred by default
- Avoid circular serialization issues (DTO layer handles output)

---

## 🚫 Forbidden Practices

- No file system access inside controllers or services directly
- No exposing entities in API responses
- No business logic inside entity classes
- No direct role checks inside controllers (handled by security layer)

---

## 🚀 Next Step

Now that the domain model is defined, the next step is:

👉 Aligning the API layer with this domain model

We will:
- validate endpoints vs entities
- adjust DTOs if needed
- refine request/response contracts
- ensure consistency between API and database design