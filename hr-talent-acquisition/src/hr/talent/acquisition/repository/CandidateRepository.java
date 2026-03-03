package hr.talent.acquisition.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import ch.ivyteam.ivy.business.data.store.search.Filter;
import ch.ivyteam.ivy.environment.Ivy;
import hr.talent.acquisition.enums.DeveloperLevel;
import hr.talent.acquisition.model.CandidateProfile;

/**
 * Repository for persisting CandidateProfile to Axon Ivy Business Data.
 */
public class CandidateRepository {

  private static final String FIELD_ID = "id";
  private static final String FIELD_FULL_NAME = "fullName";
  private static final String FIELD_CONTACT_EMAIL = "contactEmail";
  private static final String FIELD_CURRENT_LOCATION = "currentLocation";
  private static final String FIELD_DEVELOPER_LEVEL = "developerLevel";

  private static CandidateRepository instance;

  public static CandidateRepository getInstance() {
    if (instance == null) {
      instance = new CandidateRepository();
    }
    return instance;
  }

  /**
   * Creates or saves a new candidate profile.
   *
   * @param candidate the candidate to save
   * @return the persisted candidate
   * @throws IllegalArgumentException if candidate is null
   */
  public CandidateProfile create(CandidateProfile candidate) {
    if (candidate == null) {
      throw new IllegalArgumentException("Candidate cannot be null");
    }

    if (StringUtils.isBlank(candidate.getId())) {
      candidate.setId(UUID.randomUUID().toString());
    }

    Ivy.repo().save(candidate);
    return candidate;
  }

  /**
   * Retrieves all candidates.
   *
   * @return list of all candidates
   */
  public List<CandidateProfile> findAll() {
    return Ivy.repo().search(CandidateProfile.class).execute().getAll();
  }

  /**
   * Updates an existing candidate.
   *
   * @param candidate the candidate to update
   * @return the updated candidate
   */
  public CandidateProfile update(CandidateProfile candidate) {
    if (candidate == null) {
      return null;
    }

    CandidateProfile existing = findById(candidate.getId());
    if (existing == null) {
      return create(candidate);
    }

    try {
      existing.setFullName(candidate.getFullName());
      existing.setContactEmail(candidate.getContactEmail());
      existing.setContactPhone(candidate.getContactPhone());
      existing.setCurrentLocation(candidate.getCurrentLocation());
      existing.setLinkedInUrl(candidate.getLinkedInUrl());
      existing.setGithubUrl(candidate.getGithubUrl());
      existing.setProfessionalSummary(candidate.getProfessionalSummary());
      existing.setAiGeneratedSummary(candidate.getAiGeneratedSummary());
      existing.setLevel(candidate.getLevel());
      existing.setTechnicalSkills(candidate.getTechnicalSkills());
      existing.setWorkHistories(candidate.getWorkHistories());
      existing.setEducationRecords(candidate.getEducationRecords());
      existing.setProfessionalCertifications(candidate.getProfessionalCertifications());
      existing.setPersonalProjects(candidate.getPersonalProjects());
      existing.setTechnicalWritings(candidate.getTechnicalWritings());

      Ivy.repo().save(existing);
    } catch (Exception e) {
      Ivy.log().error(e);
    }
    return findById(candidate.getId());
  }

  /**
   * Deletes a candidate.
   *
   * @param candidate the candidate to delete, ignored if not exist
   */
  public void delete(CandidateProfile candidate) {
    if (candidate == null) {
      return;
    }
    CandidateProfile candidateInRepo = findById(candidate.getId());
    if (candidateInRepo != null) {
      Ivy.repo().delete(candidateInRepo);
    }
  }

  /**
   * Deletes a candidate by ID.
   *
   * @param id the candidate ID to delete
   * @return true if deleted, false if not found
   */
  public boolean deleteById(String id) {
    CandidateProfile candidate = findById(id);
    if (candidate != null) {
      Ivy.repo().delete(candidate);
      return true;
    }
    return false;
  }

  /**
   * Finds candidate by ID.
   *
   * @param id candidate identifier
   * @return candidate or null if not found
   */
  public CandidateProfile findById(String id) {
    if (StringUtils.isBlank(id)) {
      return null;
    }
    return Ivy.repo().search(CandidateProfile.class)
        .textField(FIELD_ID)
        .isEqualToIgnoringCase(id)
        .execute()
        .getFirst();
  }

  /**
   * Finds candidate by email.
   *
   * @param email candidate email
   * @return candidate or null if not found
   */
  public CandidateProfile findByEmail(String email) {
    if (StringUtils.isBlank(email)) {
      return null;
    }
    return Ivy.repo().search(CandidateProfile.class)
        .textField(FIELD_CONTACT_EMAIL)
        .isEqualToIgnoringCase(email)
        .execute()
        .getFirst();
  }

  /**
   * Finds candidates by name (partial match).
   *
   * @param name candidate name to search
   * @return list of matching candidates
   */
  public List<CandidateProfile> findByName(String name) {
    if (StringUtils.isBlank(name)) {
      return new ArrayList<>();
    }
    return Ivy.repo().search(CandidateProfile.class)
        .textField(FIELD_FULL_NAME)
        .containsAllWordPatterns(name)
        .execute()
        .getAll();
  }

  /**
   * Finds candidates by developer level.
   *
   * @param level developer level
   * @return list of matching candidates
   */
  public List<CandidateProfile> findByDeveloperLevel(DeveloperLevel level) {
    if (level == null) {
      return new ArrayList<>();
    }
    return Ivy.repo().search(CandidateProfile.class)
        .textField(FIELD_DEVELOPER_LEVEL)
        .isEqualToIgnoringCase(level.name())
        .execute()
        .getAll();
  }

  /**
   * Finds candidates by location.
   *
   * @param location location to search
   * @return list of matching candidates
   */
  public List<CandidateProfile> findByLocation(String location) {
    if (StringUtils.isBlank(location)) {
      return new ArrayList<>();
    }
    return Ivy.repo().search(CandidateProfile.class)
        .textField(FIELD_CURRENT_LOCATION)
        .containsAllWordPatterns(location)
        .execute()
        .getAll();
  }

  /**
   * Searches candidates based on multiple criteria.
   *
   * @param name optional name filter
   * @param email optional email filter
   * @param location optional location filter
   * @param level optional developer level filter
   * @return list of matching candidates
   */
  public List<CandidateProfile> search(String name, String email, String location, DeveloperLevel level) {
    var search = Ivy.repo().search(CandidateProfile.class);
    List<Filter<CandidateProfile>> filters = new ArrayList<>();

    if (StringUtils.isNotBlank(name)) {
      filters.add(search.textField(FIELD_FULL_NAME).containsAllWordPatterns(name));
    }

    if (StringUtils.isNotBlank(email)) {
      filters.add(search.textField(FIELD_CONTACT_EMAIL).containsAllWordPatterns(email));
    }

    if (StringUtils.isNotBlank(location)) {
      filters.add(search.textField(FIELD_CURRENT_LOCATION).containsAllWordPatterns(location));
    }

    if (level != null) {
      filters.add(search.textField(FIELD_DEVELOPER_LEVEL).isEqualToIgnoringCase(level.name()));
    }

    if (filters.isEmpty()) {
      return findAll();
    }

    // Apply filters with AND logic
    for (Filter<CandidateProfile> f : filters) {
      search.filter(f);
    }

    return search.execute().getAll();
  }

  /**
   * Gets the count of all candidates.
   *
   * @return total candidate count
   */
  public long count() {
    return Ivy.repo().search(CandidateProfile.class).execute().count();
  }

  /**
   * Clears all candidates from the repository.
   * Use with caution!
   */
  public void deleteAll() {
    List<CandidateProfile> all = findAll();
    for (CandidateProfile candidate : all) {
      Ivy.repo().delete(candidate);
    }
  }
}
