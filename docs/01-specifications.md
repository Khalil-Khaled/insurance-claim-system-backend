# 📄 Insurance Claims Management System — Specifications

## 🧭 Overview

This project is a backend system for managing insurance-like claims.

It allows users to manage insurance contracts, create claims, and upload supporting documents.

The system is built as a modular monolith using Spring Boot, with JWT-based authentication and role-based authorization.

---

## 👥 Actors

### 🧑 Client (ROLE_CLIENT)

A standard user of the system who can:

- Register and log in
- Manage their insurance contracts
- Create claims linked to contracts
- Upload documents to claims
- View their own claims and their status

---

### 🧑‍💼 Admin (ROLE_ADMIN)

A privileged user responsible for managing the system:

- View all users (future extension)
- View all claims
- Update claim status
- Review uploaded documents

---

## 🧩 Core Functional Requirements

### 1. Authentication & Authorization

- Email and password login
- JWT-based authentication
- Role-based access control (CLIENT / ADMIN)
- Secure endpoints using Spring Security

---

### 2. Contract Management

- Create insurance contracts
- Retrieve contracts for the authenticated user
- Each contract belongs to a single user

---

### 3. Claim Management

- Create a claim linked to a contract
- View own claims (CLIENT)
- View all claims (ADMIN)
- Update claim status (ADMIN only)

---

### 4. Document Upload (Core Feature)

Users can upload supporting documents for claims.

Requirements:
- Multiple files per claim
- Supported formats: PDF, images, documents
- Files stored on local filesystem (initial version)
- Each file is linked to a claim

Storage structure:
/uploads/{claimId}/{fileName}

---

## 🧱 Domain Rules

- A user can have multiple contracts
- A contract belongs to exactly one user
- A contract can have multiple claims
- A claim belongs to exactly one contract
- A claim can have multiple documents

---

## 📊 Claim Status Lifecycle

Claims follow a strict lifecycle:

CREATED → IN_PROGRESS → APPROVED → REJECTED

Rules:
- Only ADMIN can update claim status
- CLIENT can only create and view claims

---

## 🔐 Security Requirements

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control
- Stateless session management

---

## 📁 File Upload Requirements

Storage Strategy (Initial Version):
- Local filesystem storage
- Files stored under:
/uploads/{claimId}/

Metadata stored in database:
- original file name
- stored file path
- file type
- associated claim ID

---

## 🚫 Out of Scope

- Payment processing
- Email notifications
- Cloud storage (AWS S3, etc.)
- Microservices architecture (initial phase)
- Complex workflow engines

---

## 🎯 Project Objectives

This project aims to demonstrate:

- Clean modular monolith architecture
- Spring Boot backend design
- JWT authentication and security
- Role-based authorization
- REST API design best practices
- Relational database modeling
- File upload handling
- Angular frontend integration (basic dashboard)

---

## 📌 Long-Term Evolution (Optional)

If time allows, the system may evolve into:

- Microservice extraction (e.g., Document Service)
- Refresh token authentication
- External IAM integration (Keycloak)
- Cloud storage integration (S3 / MinIO)
- Event-driven architecture (Kafka)