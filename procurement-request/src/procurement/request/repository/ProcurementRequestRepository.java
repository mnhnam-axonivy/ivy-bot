package procurement.request.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ch.ivyteam.ivy.environment.Ivy;
import procurement.request.model.MaterialProcurementRequest;
import procurement.request.model.RequestStatus;

public class ProcurementRequestRepository {

  private static ProcurementRequestRepository instance;

  private ProcurementRequestRepository() {
  }

  public static ProcurementRequestRepository getInstance() {
    if (instance == null) {
      instance = new ProcurementRequestRepository();
    }
    return instance;
  }

  /**
   * Creates or updates a MaterialProcurementRequest in the repository.
   */
  public MaterialProcurementRequest save(MaterialProcurementRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request cannot be null");
    }
    request.setLastModifiedDate(new Date());
    Ivy.repo().save(request);
    return request;
  }

  /**
   * Finds a request by its ID. Returns null if not found.
   */
  public MaterialProcurementRequest findById(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    }
    List<MaterialProcurementRequest> results = Ivy.repo().search(MaterialProcurementRequest.class)
        .textField("id").isEqualToIgnoringCase(id)
        .execute().getAll();
    if (results == null || results.isEmpty()) {
      return null;
    }
    return results.get(0);
  }

  /**
   * Returns all persisted procurement requests.
   */
  public List<MaterialProcurementRequest> findAll() {
    return Ivy.repo().search(MaterialProcurementRequest.class).execute().getAll();
  }

  /**
   * Returns all requests with the given status.
   */
  public List<MaterialProcurementRequest> findByStatus(RequestStatus status) {
    if (status == null) {
      return new ArrayList<>();
    }
    return Ivy.repo().search(MaterialProcurementRequest.class)
        .textField("status").isEqualToIgnoringCase(status.name())
        .execute().getAll();
  }

  /**
   * Returns all requests submitted by the given username.
   */
  public List<MaterialProcurementRequest> findByRequester(String username) {
    if (StringUtils.isBlank(username)) {
      return new ArrayList<>();
    }
    return Ivy.repo().search(MaterialProcurementRequest.class)
        .textField("requester").isEqualToIgnoringCase(username)
        .execute().getAll();
  }

  /**
   * Deletes a request by ID.
   */
  public void delete(String id) {
    MaterialProcurementRequest existing = findById(id);
    if (existing != null) {
      Ivy.repo().delete(existing);
    }
  }
}
