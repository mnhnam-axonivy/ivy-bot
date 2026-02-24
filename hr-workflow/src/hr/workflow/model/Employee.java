package hr.workflow.model;

import dev.langchain4j.model.output.structured.Description;
import java.util.UUID;

@Description("An employee in the organization")
public class Employee {

  @Description("Unique identifier of the employee")
  private String id;

  @Description("Ivy session login name of the employee")
  private String username;

  @Description("Full display name of the employee")
  private String fullName;

  @Description("Work email address of the employee")
  private String email;

  @Description("The employee's job position including title and department")
  private Position position;

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

  public Position getPosition() { return position; }
  public void setPosition(Position position) { this.position = position; }

  @Override
  public String toString() {
    return "Employee [id=" + id + ", username=" + username + ", fullName=" + fullName + ", position=" + position + "]";
  }
}
