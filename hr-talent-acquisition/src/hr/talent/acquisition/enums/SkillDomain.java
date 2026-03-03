package hr.talent.acquisition.enums;

/**
 * Enum representing different technical skill domains.
 */
public enum SkillDomain {
    BACKEND("Backend Development"),
    FRONTEND("Frontend Development"),
    DATABASE("Database & Data"),
    CLOUD("Cloud Infrastructure"),
    DEVOPS("DevOps & CI/CD"),
    ARCHITECTURE("Software Architecture"),
    AI_ML("AI & Machine Learning"),
    MLOPS("MLOps & Experimentation");

    private final String displayName;

    SkillDomain(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
