# Story 4: Startup Process — Seed Employee Data on Engine Start

## Description
Create an Axon Ivy process with a **Program Start** element that automatically calls `EmployeeDataInitializer.init()` when the application starts.

## Dependencies
- Story 3 (EmployeeDataInitializer must exist)

---

## Implementation Details

### Process File

**File:** `hr-workflow/processes/hr/workflow/Startup.p.json`
**Process Name:** `Startup`
**Type:** Background process (no UI, no tasks)

### Process Variables

None — this process has no data class.

### Process Flow

```
[Program Start: onStartup] → [Script: Init Employee Data] → [Process End]
```

### Element Definitions

#### Program Start — `onStartup`
| Property | Value |
|----------|-------|
| Type | `ProgramStart` |
| Name | `onStartup` |
| Signature | `onStartup` |
| Permission | Everybody (no restriction needed) |

#### Script — `Init Employee Data`
| Property | Value |
|----------|-------|
| Type | `Script` |
| Name | `Init Employee Data` |
| Code | `hr.workflow.util.EmployeeDataInitializer.init();` |

#### Process End
| Property | Value |
|----------|-------|
| Type | `TaskEnd` |

---

## Acceptance Criteria
- [ ] `Startup.p.json` exists and is valid Axon Ivy process JSON
- [ ] The process contains a `ProgramStart` element with signature `onStartup`
- [ ] The Script step calls `EmployeeDataInitializer.init()`
- [ ] On engine/application startup, the process executes automatically
- [ ] After startup, `EmployeeRepository.findAll()` returns 2 employees
- [ ] Process does not interrupt application startup on failure (error is logged only)
