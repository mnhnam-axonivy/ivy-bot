package hr.workflow.model;

import dev.langchain4j.model.output.structured.Description;
import java.util.UUID;

@Description("A job position within the organization")
public class Position {

  @Description("Unique identifier of the position")
  private String id;

  @Description("Job title, e.g. Software Engineer, HR Manager")
  private String title;

  @Description("Department the position belongs to, e.g. IT, HR")
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
