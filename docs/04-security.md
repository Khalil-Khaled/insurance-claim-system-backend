# 🔐 Security Design — Insurance Claims System

## 🧭 Overview

This document defines the security architecture of the system.

The system uses Spring Security with JWT-based authentication and role-based authorization.

---

## 🔑 Authentication Strategy

### Type
- Stateless authentication using JWT

### Flow

1. User sends credentials (email + password)
2. Backend validates credentials
3. JWT token is generated
4. Token is returned to client
5. Client sends token in Authorization header for each request

Header format:

Authorization: Bearer <token>

---

## 🧾 JWT Structure

The token contains:

- userId
- email
- role
- issuedAt
- expiration
- issuer

---

## ⏱ Token Configuration

- Token expiration: 24 hours (configurable)
- Signing algorithm: HMAC (HS256)
- Secret key stored securely in application config

---

## 🛡️ Authorization Model

### Roles

- ROLE_CLIENT
- ROLE_ADMIN

---

### Access Rules

CLIENT:
- access own contracts
- access own claims
- upload documents

ADMIN:
- access all resources
- update claim status

---

## 🔄 Security Flow

For each request:

1. JwtFilter intercepts request
2. Extracts JWT from header
3. Validates token
4. Extracts user details
5. Loads user via CustomUserDetailsService
6. Sets authentication in SecurityContext

---

## 🧩 Core Components

### 1. SecurityConfig
- Configures filter chain
- Disables CSRF
- Enables stateless session
- Defines route authorization

---

### 2. JwtFilter
- Intercepts incoming requests
- Extracts and validates token
- Sets authentication context

---

### 3. JwtService
- Generates tokens
- Validates tokens
- Extracts claims

---

### 4. CustomUserDetailsService
- Loads user from database
- Integrates with Spring Security

---

### 5. Password Encoder

- BCryptPasswordEncoder is used
- Passwords are never stored in plain text

---

## 🔒 Endpoint Security Rules

Public endpoints:
- /api/v1/auth/register
- /api/v1/auth/login

Protected endpoints:
- All others require JWT

Role-based restrictions:
- /claims/** → CLIENT or ADMIN
- /claims/{id}/status → ADMIN only

---

## ⚠️ Security Best Practices

- Never expose password in API
- Always validate JWT signature
- Always check token expiration
- Use HTTPS in production
- Store secret key securely (env variables)

---

## 🚀 Future Enhancements

- Refresh token mechanism
- Keycloak integration
- OAuth2 support
- Role-based permissions (RBAC extension)