# Story 5: GetEmployeeInformation — Callable Sub-Process

## Description
Create a callable sub-process that accepts a username and returns the matching `Employee` and `Position` from the repository. No UI is involved.

## Dependencies
- Story 1 (data classes)
- Story 2 (repositories)
- Story 3 + 4 (data must be seeded before this can return results)

---

## Implementation Details

### Process File

**File:** `hr-workflow/processes/hr/workflow/GetEmployeeInformation.p.json`
**Process Name:** `GetEmployeeInformation`
**Type:** Callable Sub-Process (no UI)

### Process Data Class

**File:** `hr-workflow/dataclasses/hr/workflow/GetEmployeeInformationData.d.json`

| Field | Type | Direction | Description |
|-------|------|-----------|-------------|
| username | String | Input | Ivy session login name to look up |
| employee | hr.workflow.Employee | Output | Found employee, or null |
| position | hr.workflow.Position | Output | Employee's position, or null |

### Process Flow

```
[Call Sub Start: start(String username)] → [Script: Lookup Employee] → [Process End]
```

### Element Definitions

#### Call Sub Start — `start`
| Property | Value |
|----------|-------|
| Type | `CallSubStart` |
| Name | `start` |
| Signature | `start(String username)` |
| Input mapping | `param.username → in.username` |

#### Script — `Lookup Employee`
| Property | Value |
|----------|-------|
| Type | `Script` |
| Name | `Lookup Employee` |

**Script code:**
```java
import hr.workflow.repository.EmployeeRepository;
import hr.workflow.repository.PositionRepository;

in.employee = EmployeeRepository.findByUsername(in.username);
if (in.employee != null) {
  in.position = PositionRepository.findById(in.employee.getPositionId());
}
```

#### Process End
| Property | Value |
|----------|-------|
| Type | `TaskEnd` |
| Output mapping | `result.employee = in.employee`, `result.position = in.position` |

### Calling This Sub-Process from Another Process

When another process (e.g., `BusinessProcess`) calls this sub-process:

```
Input:  param.username = ivy.session.getSessionUserName()
Output: result.employee → in.employee
        result.position → in.position
```

---

## Acceptance Criteria
- [ ] `GetEmployeeInformation.p.json` exists and is valid Axon Ivy process JSON
- [ ] Process has a `CallSubStart` element with signature `start(String username)`
- [ ] Script correctly calls `EmployeeRepository.findByUsername()` and `PositionRepository.findById()`
- [ ] Returns correct `Employee` and `Position` for username `employee`
- [ ] Returns correct `Employee` and `Position` for username `manager`
- [ ] Returns `null` for both when username is not found — no exception thrown
- [ ] Sub-process can be called from `BusinessProcess` using a `SubProcessCall` element
