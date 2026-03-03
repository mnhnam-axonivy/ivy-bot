package hr.talent.acquisition.enums;

/**
 * Enum representing screening recommendations for candidates.
 */
public enum ScreeningRecommendation {
    STRONG_YES("Strong Yes", 90, 100),
    YES("Yes", 75, 89),
    MAYBE("Maybe", 60, 74),
    NO("No", 40, 59),
    STRONG_NO("Strong No", 0, 39);

    private final String displayName;
    private final int minScore;
    private final int maxScore;

    ScreeningRecommendation(String displayName, int minScore, int maxScore) {
        this.displayName = displayName;
        this.minScore = minScore;
        this.maxScore = maxScore;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMinScore() {
        return minScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public static ScreeningRecommendation fromScore(double score) {
        if (score >= 90) return STRONG_YES;
        if (score >= 75) return YES;
        if (score >= 60) return MAYBE;
        if (score >= 40) return NO;
        return STRONG_NO;
    }
}
