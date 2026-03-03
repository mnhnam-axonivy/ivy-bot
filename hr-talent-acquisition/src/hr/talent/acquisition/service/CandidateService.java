package hr.talent.acquisition.service;

import java.util.List;

import hr.talent.acquisition.enums.DeveloperLevel;
import hr.talent.acquisition.model.CandidateProfile;
import hr.talent.acquisition.repository.CandidateRepository;

/**
 * Service for managing candidate profiles.
 * Uses Axon Ivy Business Data repository for persistence.
 */
public class CandidateService {

    private static final CandidateRepository repository = CandidateRepository.getInstance();

    /**
     * Add a new candidate to the repository.
     */
    public static CandidateProfile addCandidate(CandidateProfile candidate) {
        return repository.create(candidate);
    }

    /**
     * Get all candidates.
     */
    public static List<CandidateProfile> getAllCandidates() {
        return repository.findAll();
    }

    /**
     * Find candidate by ID.
     */
    public static CandidateProfile findById(String id) {
        return repository.findById(id);
    }

    /**
     * Find candidate by email.
     */
    public static CandidateProfile findByEmail(String email) {
        return repository.findByEmail(email);
    }

    /**
     * Find candidate by name.
     */
    public static List<CandidateProfile> findByName(String name) {
        return repository.findByName(name);
    }

    /**
     * Find candidates by developer level.
     */
    public static List<CandidateProfile> findByDeveloperLevel(DeveloperLevel level) {
        return repository.findByDeveloperLevel(level);
    }

    /**
     * Find candidates by location.
     */
    public static List<CandidateProfile> findByLocation(String location) {
        return repository.findByLocation(location);
    }

    /**
     * Update an existing candidate.
     */
    public static CandidateProfile updateCandidate(CandidateProfile candidate) {
        return repository.update(candidate);
    }

    /**
     * Remove a candidate by ID.
     */
    public static boolean removeCandidate(String id) {
        return repository.deleteById(id);
    }

    /**
     * Clear all candidates.
     */
    public static void clearAll() {
        repository.deleteAll();
    }

    /**
     * Get candidate count.
     */
    public static long getCount() {
        return repository.count();
    }

    /**
     * Search candidates by multiple criteria.
     */
    public static List<CandidateProfile> search(String name, String email, String location, DeveloperLevel level) {
        return repository.search(name, email, location, level);
    }
}
