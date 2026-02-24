# Story 2: Entity + Repository — PositionRepository and EmployeeRepository

## Description
Create Java repository classes for `Position` and `Employee` using the Axon Ivy Business Data API (`Ivy.repo()`).

## Dependencies
- Story 1 (data classes must exist so the Java types are generated)

---

## Implementation Details

### Part A: PositionRepository

**File:** `hr-workflow/src/hr/workflow/repository/PositionRepository.java`
**Package:** `hr.workflow.repository`

| Method | Signature | Description |
|--------|-----------|-------------|
| save | `Position save(Position position)` | Persist or update a Position |
| findById | `Position findById(String id)` | Return Position by id, or null |
| findAll | `List<Position> findAll()` | Return all persisted Positions |

**Implementation notes:**
- Use `Ivy.repo().of(Position.class)` as the repository handle
- `save`: `Ivy.repo().of(Position.class).save(position)`
- `findById`: `Ivy.repo().of(Position.class).find(id)`
- `findAll`: `Ivy.repo().of(Position.class).query().find().collect(Collectors.toList())`
- All methods are `public static`

### Part B: EmployeeRepository

**File:** `hr-workflow/src/hr/workflow/repository/EmployeeRepository.java`
**Package:** `hr.workflow.repository`

| Method | Signature | Description |
|--------|-----------|-------------|
| save | `Employee save(Employee employee)` | Persist or update an Employee |
| findById | `Employee findById(String id)` | Return Employee by id, or null |
| findAll | `List<Employee> findAll()` | Return all persisted Employees |
| findByUsername | `Employee findByUsername(String username)` | Return Employee matching username, or null |

**Implementation notes:**
- Use `Ivy.repo().of(Employee.class)` as the repository handle
- `findByUsername`: query with `.query().where().field("username").isEqualTo(username).find().findFirst().orElse(null)`
- All methods are `public static`

---

## Acceptance Criteria
- [ ] `PositionRepository` compiles without errors
- [ ] `EmployeeRepository` compiles without errors
- [ ] `save()` persists a new record and returns the saved object
- [ ] `findAll()` returns an empty list when no records exist
- [ ] `findByUsername("employee")` returns the correct employee after seeding
- [ ] `findByUsername("unknown")` returns null without throwing an exception
