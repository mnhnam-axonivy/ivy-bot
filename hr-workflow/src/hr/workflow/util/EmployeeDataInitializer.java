package hr.workflow.util;

import ch.ivyteam.ivy.environment.Ivy;
import hr.workflow.model.Employee;
import hr.workflow.model.Position;
import hr.workflow.repository.EmployeeRepository;

/**
 * Seeds default Position and Employee data into the Axon Ivy repository on first startup.
 * This operation is idempotent — it only runs if no employees exist yet.
 */
public class EmployeeDataInitializer {

  private EmployeeDataInitializer() {}

  public static void init() {
    try {
      if (!EmployeeRepository.getInstance().findAll().isEmpty()) {
        return;
      }

      Position posIT = new Position();
      posIT.setTitle("Software Engineer");
      posIT.setDepartment("IT");

      Position posHR = new Position();
      posHR.setTitle("HR Manager");
      posHR.setDepartment("HR");

      Employee emp1 = new Employee();
      emp1.setUsername("employee");
      emp1.setFullName("John Employee");
      emp1.setEmail("john.employee@example.com");
      emp1.setPosition(posIT);
      EmployeeRepository.getInstance().create(emp1);

      Employee emp2 = new Employee();
      emp2.setUsername("manager");
      emp2.setFullName("Jane Manager");
      emp2.setEmail("jane.manager@example.com");
      emp2.setPosition(posHR);
      EmployeeRepository.getInstance().create(emp2);

      Ivy.log().info("EmployeeDataInitializer: seeded 2 employees.");
    } catch (Exception e) {
      Ivy.log().error("EmployeeDataInitializer failed: " + e.getMessage(), e);
    }
  }
}
