# Story 3: Data Initializer — EmployeeDataInitializer

## Description
Create a Java utility class that seeds fake `Position` and `Employee` data into the Axon Ivy repository on first startup. The operation is idempotent.

## Dependencies
- Story 1 (data classes)
- Story 2 (repositories)

---

## Implementation Details

### EmployeeDataInitializer

**File:** `hr-workflow/src/hr/workflow/util/EmployeeDataInitializer.java`
**Package:** `hr.workflow.util`

**Single public method:** `public static void init()`

**Logic (pseudocode):**
```
init():
  if EmployeeRepository.findAll() is not empty → return (already seeded)

  // Create Positions
  Position posIT = new Position()
    id = UUID.randomUUID().toString()
    title = "Software Engineer"
    department = "IT"
  PositionRepository.save(posIT)

  Position posHR = new Position()
    id = UUID.randomUUID().toString()
    title = "HR Manager"
    department = "HR"
  PositionRepository.save(posHR)

  // Create Employees (matching users.yaml)
  Employee emp1 = new Employee()
    id = UUID.randomUUID().toString()
    username = "employee"
    fullName = "John Employee"
    email = "john.employee@example.com"
    positionId = posIT.getId()
  EmployeeRepository.save(emp1)

  Employee emp2 = new Employee()
    id = UUID.randomUUID().toString()
    username = "manager"
    fullName = "Jane Manager"
    email = "jane.manager@example.com"
    positionId = posHR.getId()
  EmployeeRepository.save(emp2)

  ivy.log.info("EmployeeDataInitializer: seeded 2 positions and 2 employees.")
```

**Seed data summary:**

| username | fullName | email | title | department |
|----------|----------|-------|-------|------------|
| employee | John Employee | john.employee@example.com | Software Engineer | IT |
| manager | Jane Manager | jane.manager@example.com | HR Manager | HR |

**Error handling:**
- Wrap the body in try/catch
- On exception: `ivy.log.error("EmployeeDataInitializer failed: " + e.getMessage(), e)`
- Do not rethrow — startup must not be blocked

---

## Acceptance Criteria
- [ ] `EmployeeDataInitializer.init()` compiles without errors
- [ ] First call creates 2 positions and 2 employees in the repository
- [ ] Second call (idempotency check) creates no additional records
- [ ] Exception during seeding is caught and logged; no exception propagates
- [ ] Log message at INFO level is emitted after successful seeding
