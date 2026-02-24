package hr.workflow.repository;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ch.ivyteam.ivy.environment.Ivy;
import hr.workflow.model.Position;

/**
 * Repository for persisting Position to Axon Ivy Business Data.
 */
public class PositionRepository {

  private static final String FIELD_ID = "id";

  private static PositionRepository instance;

  public static PositionRepository getInstance() {
    if (instance == null) {
      instance = new PositionRepository();
    }
    return instance;
  }

  /**
   * Creates a new position.
   */
  public Position create(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("Position cannot be null");
    }
    Ivy.repo().save(position);
    return position;
  }

  /**
   * Retrieves all positions.
   */
  public List<Position> findAll() {
    return Ivy.repo().search(Position.class).execute().getAll();
  }

  /**
   * Finds a position by id.
   */
  public Position findById(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    }
    return Ivy.repo().search(Position.class)
        .textField(FIELD_ID)
        .isEqualToIgnoringCase(id)
        .execute()
        .getFirst();
  }
}
