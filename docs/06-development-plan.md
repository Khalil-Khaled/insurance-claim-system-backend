# 🚀 Development Plan — Insurance Claims System

## 🧭 Purpose

This document defines the development roadmap of the project.

It is used to:
- track progress
- define implementation phases
- clarify deliverables
- guide development without over-specifying implementation details

---

## 📦 Phase 1 — Foundation & Security

### 🎯 Goal
Establish core security infrastructure and user management.

---

### 🧩 Tasks

- Define User entity (JPA)
- Define Role enum
- Create UserRepository
- Implement password encryption (BCrypt)
- Implement authentication logic (login)
- Implement JWT generation
- Configure Spring Security (basic setup)
- Implement JWT filter
- Implement CustomUserDetailsService

---

### ✅ Deliverables

- User can register
- User can log in
- JWT is generated and returned
- Protected endpoints require valid JWT
- Roles are extracted from token

---

### 📌 Checkpoint

- Can authenticate and access a secured test endpoint

---

## 📦 Phase 2 — Contract Module

### 🎯 Goal
Implement contract management linked to users.

---

### 🧩 Tasks

- Define Contract entity
- Implement ContractRepository
- Implement ContractService
- Create ContractController
- Link contracts to authenticated user

---

### ✅ Deliverables

- User can create contract
- User can retrieve own contracts
- Contracts are persisted in database

---

### 📌 Checkpoint

- Authenticated user sees only their contracts

---

## 📦 Phase 3 — Claim Module

### 🎯 Goal
Implement claims linked to contracts.

---

### 🧩 Tasks

- Define Claim entity
- Implement ClaimRepository
- Implement ClaimService
- Create ClaimController
- Implement claim reference generation
- Enforce ownership rules

---

### ✅ Deliverables

- User can create claim
- User can view own claims
- Admin can view all claims

---

### 📌 Checkpoint

- Claims correctly linked to contracts and users

---

## 📦 Phase 4 — Document Upload Module

### 🎯 Goal
Implement file upload with storage abstraction.

---

### 🧩 Tasks

- Define Document entity
- Create FileStorageService interface
- Implement LocalFileStorageService
- Implement upload logic in service layer
- Link documents to claims
- Implement file metadata extraction

---

### ✅ Deliverables

- User can upload file to claim
- File stored in filesystem
- Metadata stored in database
- Documents retrievable via API

---

### 📌 Checkpoint

- Files physically stored and linked correctly

---

## 📦 Phase 5 — Authorization & Business Rules

### 🎯 Goal
Enforce role-based access and domain rules.

---

### 🧩 Tasks

- Restrict endpoints by role
- Implement claim status update (ADMIN only)
- Validate ownership (CLIENT access control)
- Enforce domain constraints in services

---

### ✅ Deliverables

- CLIENT cannot access others’ data
- ADMIN can manage all claims
- Status transitions enforced

---

### 📌 Checkpoint

- Security rules correctly enforced across modules

---

## 📦 Phase 6 — DTO & Mapping Layer

### 🎯 Goal
Introduce clean API contracts using DTOs.

---

### 🧩 Tasks

- Create request DTOs
- Create response DTOs
- Implement MapStruct mappers
- Refactor controllers to use DTOs

---

### ✅ Deliverables

- No entity exposed in API responses
- Clean and stable API contracts
- Mapping layer fully implemented

---

### 📌 Checkpoint

- Controllers only use DTOs

---

## 📦 Phase 7 — Error Handling

### 🎯 Goal
Implement centralized exception management.

---

### 🧩 Tasks

- Create custom exceptions
- Implement GlobalExceptionHandler (@RestControllerAdvice)
- Handle validation errors
- Standardize error responses

---

### ✅ Deliverables

- Consistent error response format
- Proper HTTP status codes
- No raw exceptions exposed

---

### 📌 Checkpoint

- All errors handled through global handler

---

## 📦 Phase 8 — Frontend Integration (Angular)

### 🎯 Goal
Provide minimal UI for system interaction.

---

### 🧩 Tasks

- Implement login page
- Store JWT in frontend
- Create claims dashboard
- Connect to backend APIs

---

### ✅ Deliverables

- User can log in via UI
- User can view and create claims

---

### 📌 Checkpoint

- End-to-end flow working (UI → API → DB)

---

## 📦 Phase 9 — Finalization

### 🎯 Goal
Polish and prepare project for GitHub showcase.

---

### 🧩 Tasks

- Clean codebase
- Write README.md
- Add setup instructions
- Document API usage
- Review architecture consistency

---

### ✅ Deliverables

- Clean and readable repository
- Fully documented project
- Ready for presentation

---

## 🧠 Development Rules

- No business logic in controllers
- Always go through service layer
- Keep modules independent
- Update documentation when decisions change

---

## 🚀 Success Criteria

The project is considered complete when:

- Authentication works with JWT
- All modules are functional
- Security rules are enforced
- File upload works
- API is consistent and clean
- Documentation is complete