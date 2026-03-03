package hr.talent.acquisition.enums;

/**
 * Enum representing different seniority levels for software developers.
 */
public enum DeveloperLevel {
    ENTRY("Entry Level", 0, 2),
    MID("Mid Level", 2, 5),
    SENIOR("Senior Level", 5, 99);

    private final String displayName;
    private final int minYearsExperience;
    private final int maxYearsExperience;

    DeveloperLevel(String displayName, int minYears, int maxYears) {
        this.displayName = displayName;
        this.minYearsExperience = minYears;
        this.maxYearsExperience = maxYears;
    }

    public String getDisplayName() { return displayName; }
    public int getMinYearsExperience() { return minYearsExperience; }
    public int getMaxYearsExperience() { return maxYearsExperience; }
}
