# REQ-PRC-001: Employee Information

## 1. General Information

**Requirement ID:** REQ-PRC-001
**Requirement Name:** Employee Information — Data Model, Repository, Seeding, and Lookup
**Business Domain:** HR
**Priority:** Must
**Status:** Draft

---

## 2. Business Goal

- **Business problem:** Other processes (e.g., leave request, workflow bot) need to look up employee data (name, role, position) by username. No employee master data exists in the system yet.
- **Expected outcome:** Employee and position data is available in the Axon Ivy repository from startup. Any process can query an employee's information by their Ivy login username.
- **Success metrics:** `GetEmployeeInformation` sub-process returns a valid `Employee` and `Position` for any seeded username. Seed runs once on startup and is idempotent.

---

## 3. Process Overview

### 3.1 Process Triggers

Two separate triggers:

| Trigger | Description |
|---------|-------------|
| Engine startup | `EmployeeDataInitializer` seeds fake employee data into the Axon Ivy repository if none exists |
| Explicit call | `GetEmployeeInformation` sub-process is called by other processes to look up employee info by username |

### 3.2 High-Level Flow

**Startup seeding:**
```
Engine Starts → Program Start → Call EmployeeDataInitializer.init()
  → If no employees exist: create Positions + Employees → Persist via repositories
  → If data exists: skip
```

**Employee lookup (called sub-process):**
```
Caller provides username → Find Employee by username → Find Position by positionId → Return Employee + Position
```

### 3.3 State Machine

Not applicable. This feature is a data seeding + read-only lookup. No entity lifecycle states.

### 3.4 Process End States

| Process | End State |
|---------|-----------|
| Startup seeding | Always completes successfully (idempotent) |
| GetEmployeeInformation | Returns Employee + Position (or nulls if not found) |

---

## 4. Actors & Roles

| Role | Involvement |
|------|-------------|
| System (engine) | Triggers startup seeding automatically |
| Any caller process | Can invoke `GetEmployeeInformation` sub-process |
| Employee | Their data is seeded; they are the subject of queries |
| Manager | Their data is seeded; they are the subject of queries |

---

## 5. Detailed Task Definition

### Task: Seed Employee Data (Startup)

**Type:** Script / Service Task (no UI)
**Responsible:** System (engine startup)
**Inputs:** None
**Outputs:** Persisted `Position` and `Employee` records in Axon Ivy repository
**UI Required:** No

**Logic:**
1. Call `EmployeeRepository.findAll()`
2. If result is empty: create fake positions and employees (see Section 6.4)
3. Persist each `Position` via `PositionRepository.save()`
4. Persist each `Employee` via `EmployeeRepository.save()`

**Business Rule:** Seeding is idempotent — runs on every engine startup but only inserts if the repository is empty.

---

### Task: Get Employee Information (Callable Sub-Process)

**Type:** Callable Sub-Process (no UI)
**Responsible:** System (called programmatically)
**Inputs:** `username` (String)
**Outputs:** `employee` (Employee), `position` (Position)
**UI Required:** No

**Logic:**
1. Call `EmployeeRepository.findByUsername(username)`
2. If found: call `PositionRepository.findById(employee.positionId)`
3. Return both objects (either may be null if not found)

---

## 6. Data Model

### 6.1 Enumerations

None for this feature.

### 6.2 Data Classes (Axon Ivy `.d.json`)

#### Position

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| id | String | Yes | Unique identifier (system-generated UUID) |
| title | String | Yes | Job title, e.g. "Software Engineer" |
| department | String | Yes | Department name, e.g. "IT", "HR" |

#### Employee

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| id | String | Yes | Unique identifier (system-generated UUID) |
| username | String | Yes | Ivy session login name (`ivy.session.getSessionUserName()`) |
| fullName | String | Yes | Display name, e.g. "John Employee" |
| email | String | Yes | Work email address |
| positionId | String | Yes | References `Position.id` |

### 6.3 Business Entities (Persistent via `Ivy.repo()`)

| Entity | Repository Class | Storage |
|--------|-----------------|---------|
| Position | `PositionRepository` | Axon Ivy Business Data |
| Employee | `EmployeeRepository` | Axon Ivy Business Data |

### 6.4 Seed Data

Positions to create on first startup:

| id (UUID) | title | department |
|-----------|-------|------------|
| auto | Software Engineer | IT |
| auto | HR Manager | HR |

Employees to create on first startup (matching `users.yaml`):

| username | fullName | email | position |
|----------|----------|-------|----------|
| employee | John Employee | john.employee@example.com | Software Engineer (IT) |
| manager | Jane Manager | jane.manager@example.com | HR Manager (HR) |

---

## 7. Business Rules

- Seeding must be **idempotent**: if any `Employee` records exist, skip seeding entirely.
- `GetEmployeeInformation` returns `null` for `employee` and `position` if username is not found — callers must handle null.
- `positionId` in `Employee` must reference an existing `Position.id`.
- UUIDs are generated via `java.util.UUID.randomUUID().toString()`.

---

## 8. Notifications

None. This is a background data operation.

---

## 9. Integration & External Systems

None. All data is stored and read from the Axon Ivy Business Data repository (`Ivy.repo()`).

---

## 10. Exception & Alternative Flows

| Scenario | Handling |
|----------|----------|
| `findByUsername` returns no result | Return `null` employee and position; callers handle gracefully |
| Startup seeding fails | Log the error; engine startup continues (non-fatal) |

---

## 11. SLA & Time Constraints

Not applicable.

---

## 12. Security & Data Protection

- Employee email and full name are personal data (PII).
- Access is limited to processes running within the hr-workflow application.
- No external exposure.

---

## 13. Audit & Compliance

- Seeding is logged via `ivy.log` at INFO level.
- No audit trail required for read-only lookups.

---

## 14. Reporting & Monitoring

Not applicable for this feature.

---

## 15. UI Requirements

None. No forms or dialogs are part of this feature.

---

## 17. Acceptance Criteria

- [ ] `Position` data class exists with fields: `id`, `title`, `department`
- [ ] `Employee` data class exists with fields: `id`, `username`, `fullName`, `email`, `positionId`
- [ ] `PositionRepository` can save and retrieve `Position` records via `Ivy.repo()`
- [ ] `EmployeeRepository` can save, retrieve by id, retrieve all, and find by username
- [ ] `EmployeeDataInitializer.init()` seeds 2 positions and 2 employees on first run
- [ ] Second call to `init()` does not create duplicates
- [ ] Startup process calls `init()` automatically on engine/app start
- [ ] `GetEmployeeInformation` sub-process returns correct `Employee` and `Position` for username `employee` and `manager`
- [ ] `GetEmployeeInformation` returns null values gracefully for unknown usernames

---

## 19. Open Questions / Risks

- None at this time. Seed data is aligned with `users.yaml`.
