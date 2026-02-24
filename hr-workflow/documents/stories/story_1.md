# Story 1: Data Model — Position and Employee Java Classes

## Description
Create the two Java model classes (`Position` and `Employee`) that form the foundation of the employee information feature.

## Dependencies
None.

---

## Implementation Details

### Part A: Position

**File:** `hr-workflow/src/hr/workflow/model/Position.java`
**Package:** `hr.workflow.model`

| Field | Type | Comment |
|-------|------|---------|
| id | String | Unique identifier — auto-generated UUID in constructor |
| title | String | Job title, e.g. "Software Engineer" |
| department | String | Department name, e.g. "IT", "HR" |

```java
package hr.workflow.model;

import java.util.UUID;

public class Position {

  private String id;
  private String title;
  private String department;

  public Position() {
    this.id = UUID.randomUUID().toString();
  }

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public String getDepartment() { return department; }
  public void setDepartment(String department) { this.department = department; }

  @Override
  public String toString() {
    return "Position [id=" + id + ", title=" + title + ", department=" + department + "]";
  }
}
```

---

### Part B: Employee

**File:** `hr-workflow/src/hr/workflow/model/Employee.java`
**Package:** `hr.workflow.model`

| Field | Type | Comment |
|-------|------|---------|
| id | String | Unique identifier — auto-generated UUID in constructor |
| username | String | Ivy session login name (`ivy.session.getSessionUserName()`) |
| fullName | String | Display name |
| email | String | Work email address |
| positionId | String | References `Position.id` |

```java
package hr.workflow.model;

import java.util.UUID;

public class Employee {

  private String id;
  private String username;
  private String fullName;
  private String email;
  private String positionId;

  public Employee() {
    this.id = UUID.randomUUID().toString();
  }

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public String getFullName() { return fullName; }
  public void setFullName(String fullName) { this.fullName = fullName; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPositionId() { return positionId; }
  public void setPositionId(String positionId) { this.positionId = positionId; }

  @Override
  public String toString() {
    return "Employee [id=" + id + ", username=" + username + ", fullName=" + fullName + "]";
  }
}
```

---

## Acceptance Criteria

- [ ] `Position.java` exists in `src/hr/workflow/model/` with fields: `id`, `title`, `department`
- [ ] `Employee.java` exists in `src/hr/workflow/model/` with fields: `id`, `username`, `fullName`, `email`, `positionId`
- [ ] Both constructors auto-generate a UUID for `id`
- [ ] All fields have standard getters and setters
- [ ] Both classes compile without errors
