package hr.talent.acquisition.enums;

/**
 * Enum representing different types of employers.
 */
public enum EmployerCategory {
    FAANG("FAANG"),
    STARTUP("Startup"),
    MIDSIZE_SAAS("Mid-size SaaS"),
    ENTERPRISE("Enterprise"),
    CONSULTANCY("Consultancy");

    private final String displayName;

    EmployerCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
