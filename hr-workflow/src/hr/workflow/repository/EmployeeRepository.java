package hr.workflow.repository;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ch.ivyteam.ivy.environment.Ivy;
import hr.workflow.model.Employee;

/**
 * Repository for persisting Employee to Axon Ivy Business Data.
 */
public class EmployeeRepository {

  private static final String FIELD_ID = "id";
  private static final String FIELD_USERNAME = "username";

  private static EmployeeRepository instance;

  public static EmployeeRepository getInstance() {
    if (instance == null) {
      instance = new EmployeeRepository();
    }
    return instance;
  }

  /**
   * Creates a new employee.
   */
  public Employee create(Employee employee) {
    if (employee == null) {
      throw new IllegalArgumentException("Employee cannot be null");
    }
    Ivy.repo().save(employee);
    return employee;
  }

  /**
   * Retrieves all employees.
   */
  public List<Employee> findAll() {
    return Ivy.repo().search(Employee.class).execute().getAll();
  }

  /**
   * Finds an employee by id.
   */
  public Employee findById(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    }
    return Ivy.repo().search(Employee.class)
        .textField(FIELD_ID)
        .isEqualToIgnoringCase(id)
        .execute()
        .getFirst();
  }

  /**
   * Finds an employee by Ivy session username.
   */
  public Employee findByUsername(String username) {
    if (StringUtils.isBlank(username)) {
      return null;
    }
    return Ivy.repo().search(Employee.class)
        .textField(FIELD_USERNAME)
        .isEqualToIgnoringCase(username)
        .execute()
        .getFirst();
  }
}
