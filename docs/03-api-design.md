# 🌐 API Design — Insurance Claims System

## 🧭 Overview

This document defines the REST API contract for the Insurance Claims Management System.

All endpoints are versioned under:

/api/v1

The API follows REST principles and uses JSON for all requests and responses.

---

## 🔐 Authentication APIs

### 📌 Register User

POST /api/v1/auth/register

Request:
```
{
  "email": "string",
  "password": "string"
}
```

Response:
```
{
  "message": "User registered successfully"
}
```

---

### 📌 Login

POST /api/v1/auth/login

Request:
```
{
  "email": "string",
  "password": "string"
}
```

Response:
```
{
  "token": "jwt-token",
  "role": "ROLE_CLIENT"
}
```

Possible errors:

- 401 Unauthorized  
  Invalid credentials

- 400 Bad Request  
  Missing or invalid input

---

## 👤 User APIs (Admin only for now)

### 📌 Get All Users

GET /api/v1/users

Authorization: ADMIN only

Response:
```
[
  {
    "id": 1,
    "email": "user@example.com",
    "role": "ROLE_CLIENT"
  }
]
```

---

## 📄 Contract APIs

### 📌 Create Contract

POST /api/v1/contracts

Authorization: CLIENT / ADMIN

Request:
```
{
  "contractType": "AUTO | HOME | HEALTH",
  "startDate": "YYYY-MM-DD",
  "endDate": "YYYY-MM-DD"
}
```

Response:
```
{
  "id": 1,
  "contractType": "AUTO",
  "startDate": "YYYY-MM-DD",
  "endDate": "YYYY-MM-DD",
  "userId": 1
}
```

Possible errors:

- 400 Bad Request  
  Invalid dates or input

- 401 Unauthorized  
  Missing or invalid token

---

### 📌 Get My Contracts

GET /api/v1/contracts/me

Authorization: CLIENT

Response:
```
[
  {
    "id": 1,
    "contractType": "AUTO",
    "startDate": "YYYY-MM-DD",
    "endDate": "YYYY-MM-DD"
  }
]
```

---

## 📊 Claim APIs

### 📌 Create Claim

POST /api/v1/claims

Authorization: CLIENT

Request:
```
{
  "contractId": 1,
  "description": "string"
}
```

Response:
```
{
  "id": 1,
  "reference": "CLM-2026-001",
  "description": "string",
  "status": "CREATED"
}
```

Possible errors:

- 404 Not Found  
  Contract does not exist

- 403 Forbidden  
  Contract does not belong to user

- 400 Bad Request  
  Invalid input

---

### 📌 Get My Claims

GET /api/v1/claims/me

Authorization: CLIENT

Response:
```
[
  {
    "id": 1,
    "reference": "CLM-2026-001",
    "description": "string",
    "status": "CREATED"
  }
]
```

---

### 📌 Get Claim by ID

GET /api/v1/claims/{id}

Authorization: CLIENT (own claims) / ADMIN (all)

Response:
```
{
  "id": 1,
  "reference": "CLM-2026-001",
  "description": "string",
  "status": "CREATED"
}
```

Possible errors:

- 404 Not Found  
  Claim not found

- 403 Forbidden  
  Access denied

---

### 📌 Update Claim Status

PUT /api/v1/claims/{id}/status

Authorization: ADMIN only

Request:
```
{
  "status": "IN_PROGRESS | APPROVED | REJECTED"
}
```

Response:
```
{
  "id": 1,
  "status": "IN_PROGRESS"
}
```

Possible errors:

- 403 Forbidden  
  Not an ADMIN

- 404 Not Found  
  Claim not found

- 400 Bad Request  
  Invalid status transition

---

## 📁 File Upload APIs

### 📌 Upload Document to Claim

POST /api/v1/claims/{claimId}/documents

Authorization: CLIENT / ADMIN

Content-Type: multipart/form-data

Request:
- file: binary file

Response:
```
{
  "fileId": 1,
  "fileName": "accident.pdf",
  "fileUrl": "/uploads/{claimId}/accident.pdf"
}
```

Possible errors:

- 404 Not Found  
  Claim not found

- 400 Bad Request  
  Invalid file (size/type)

- 413 Payload Too Large  
  File exceeds allowed size

---

### 📌 Get Claim Documents

GET /api/v1/claims/{claimId}/documents

Authorization: CLIENT (own claims) / ADMIN

Response:
```
[
  {
    "fileId": 1,
    "fileName": "accident.pdf",
    "fileUrl": "/uploads/{claimId}/accident.pdf"
  }
]
```

---

## 🔐 Security Rules Summary

- All endpoints except auth require JWT
- ROLE_CLIENT:
  - can access only own contracts/claims/documents
- ROLE_ADMIN:
  - can access everything
  - can update claim status

---

## 📦 API Design Principles

- RESTful conventions
- Stateless authentication (JWT)
- Clear separation between request and response DTOs
- No entity exposure in API responses
- Consistent error handling (defined in common module later)

---

## 🚫 Out of Scope (API Layer)

- No pagination (for now)
- No filtering/sorting (for now)
- No HATEOAS
- No GraphQL

---

## 🚀 Next Step

Next phase:

👉 Database Design (ER model + JPA mapping strategy)

This will define:
- entity relationships
- cascade rules
- fetch strategies
- indexing decisions