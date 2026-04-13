# 🗄️ Database Model — Insurance Claims System

## 🧭 Overview

This document defines the database-level implementation of the domain model using JPA/Hibernate and PostgreSQL.

It includes:
- table structures
- relationships
- constraints
- fetch strategies
- cascade rules

---

## 👤 User Table

### Table: users

Columns:
- id (PK)
- email (unique, not null)
- password (not null)
- role (not null)
- enabled (not null)

Constraints:
- UNIQUE(email)

---

## 📄 Contract Table

### Table: contracts

Columns:
- id (PK)
- contract_type (not null)
- start_date (not null)
- end_date (not null)
- user_id (FK → users.id)

Constraints:
- FOREIGN KEY (user_id) REFERENCES users(id)

---

## 📊 Claim Table

### Table: claims

Columns:
- id (PK)
- reference (unique, not null)
- description
- status (not null)
- created_at (not null)
- contract_id (FK → contracts.id)

Constraints:
- UNIQUE(reference)
- FOREIGN KEY (contract_id) REFERENCES contracts(id)

---

## 📁 Document Table

### Table: documents

Columns:
- id (PK)
- file_name (not null)
- file_type
- file_size
- storage_path (not null)
- checksum
- uploaded_at (not null)
- uploaded_by
- claim_id (FK → claims.id)

Constraints:
- FOREIGN KEY (claim_id) REFERENCES claims(id)

---

## 🔗 Relationships (JPA Mapping)

### User → Contracts

- OneToMany (User → Contract)
- ManyToOne (Contract → User)

Configuration:
- fetch = LAZY
- cascade = NONE

---

### Contract → Claims

- OneToMany (Contract → Claim)
- ManyToOne (Claim → Contract)

Configuration:
- fetch = LAZY
- cascade = NONE

---

### Claim → Documents

- OneToMany (Claim → Document)
- ManyToOne (Document → Claim)

Configuration:
- fetch = LAZY
- cascade = ALL (documents tied to claim lifecycle)

---

## ⚡ Fetch Strategy

Default strategy:

- LAZY for all relationships

Reason:
- avoid unnecessary joins
- better performance
- prevent N+1 issues in basic scenarios

---

## 🔁 Cascade Rules

- User → Contracts: NONE
- Contract → Claims: NONE
- Claim → Documents: ALL

Reason:
- deleting a claim should delete its documents
- higher-level entities should not cascade deletes by default

---

## 📌 Indexing Strategy

Indexes should be added on:

- users.email (unique index)
- claims.reference (unique index)
- contracts.user_id
- claims.contract_id
- documents.claim_id

---

## 🧠 JPA Best Practices

- Use @Enumerated(EnumType.STRING) for enums
- Avoid EAGER fetching
- Avoid bidirectional relationships unless necessary
- Use DTOs to prevent lazy loading issues in controllers

---

## ⚠️ Data Integrity Rules

- No orphan contracts without user
- No orphan claims without contract
- No orphan documents without claim

---

## 🚀 Future Enhancements

- Add Flyway for database migrations
- Add soft delete (deleted_at column)
- Add auditing (created_by, updated_by)
- Optimize queries with custom JPQL

---