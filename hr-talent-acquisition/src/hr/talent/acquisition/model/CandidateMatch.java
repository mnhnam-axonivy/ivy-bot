package hr.talent.acquisition.model;

import dev.langchain4j.model.output.structured.Description;

/**
 * Represents a matching between a candidate and a position with scoring and rationale.
 * Used in the candidate selection phase to help hiring managers make informed decisions.
 */
public class CandidateMatch implements Comparable<CandidateMatch> {

    @Description("The candidate profile being evaluated")
    private CandidateProfile candidate;

    @Description("The position being evaluated for")
    private Position position;

    @Description("Overall match score from 0-100, with 100 being perfect match")
    private double matchScore;

    @Description("Percentage of required skills the candidate possesses (0-100)")
    private double skillMatchPercentage;

    @Description("Whether the candidate's experience level matches the position requirements")
    private boolean levelMatch;

    @Description("AI-generated explanation of why this candidate matches (or doesn't match) the position")
    private String matchingRationale;

    @Description("Detailed screening result if screening was performed")
    private ScreeningResult screeningResult;

    @Description("Selection status: PENDING, SELECTED, REJECTED")
    private String selectionStatus;

    public CandidateMatch() {
        this.selectionStatus = "PENDING";
    }

    public CandidateMatch(CandidateProfile candidate, Position position) {
        this();
        this.candidate = candidate;
        this.position = position;
    }

    // Getters and setters

    public CandidateProfile getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateProfile candidate) {
        this.candidate = candidate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }

    public double getSkillMatchPercentage() {
        return skillMatchPercentage;
    }

    public void setSkillMatchPercentage(double skillMatchPercentage) {
        this.skillMatchPercentage = skillMatchPercentage;
    }

    public boolean isLevelMatch() {
        return levelMatch;
    }

    public void setLevelMatch(boolean levelMatch) {
        this.levelMatch = levelMatch;
    }

    public String getMatchingRationale() {
        return matchingRationale;
    }

    public void setMatchingRationale(String matchingRationale) {
        this.matchingRationale = matchingRationale;
    }

    public ScreeningResult getScreeningResult() {
        return screeningResult;
    }

    public void setScreeningResult(ScreeningResult screeningResult) {
        this.screeningResult = screeningResult;
    }

    public String getSelectionStatus() {
        return selectionStatus;
    }

    public void setSelectionStatus(String selectionStatus) {
        this.selectionStatus = selectionStatus;
    }

    // Fluent builder methods

    public CandidateMatch withMatchScore(double score) {
        this.matchScore = score;
        return this;
    }

    public CandidateMatch withSkillMatchPercentage(double percentage) {
        this.skillMatchPercentage = percentage;
        return this;
    }

    public CandidateMatch withLevelMatch(boolean match) {
        this.levelMatch = match;
        return this;
    }

    public CandidateMatch withRationale(String rationale) {
        this.matchingRationale = rationale;
        return this;
    }

    public CandidateMatch withScreeningResult(ScreeningResult result) {
        this.screeningResult = result;
        return this;
    }

    // Utility methods

    public boolean isPending() {
        return "PENDING".equalsIgnoreCase(selectionStatus);
    }

    public boolean isSelected() {
        return "SELECTED".equalsIgnoreCase(selectionStatus);
    }

    public boolean isRejected() {
        return "REJECTED".equalsIgnoreCase(selectionStatus);
    }

    public void select() {
        this.selectionStatus = "SELECTED";
    }

    public void reject() {
        this.selectionStatus = "REJECTED";
    }

    public String getCandidateName() {
        return candidate != null ? candidate.getFullName() : "Unknown";
    }

    public String getCandidateEmail() {
        return candidate != null ? candidate.getContactEmail() : "";
    }

    public String getPositionTitle() {
        return position != null ? position.getRoleTitle() : "Unknown Position";
    }

    public boolean isStrongMatch() {
        return matchScore >= 75.0;
    }

    public boolean isGoodMatch() {
        return matchScore >= 60.0 && matchScore < 75.0;
    }

    public boolean isWeakMatch() {
        return matchScore < 60.0;
    }

    public String getMatchQuality() {
        if (matchScore >= 80) return "Excellent";
        if (matchScore >= 70) return "Good";
        if (matchScore >= 60) return "Fair";
        return "Poor";
    }

    /**
     * Compare by match score in descending order (higher scores first)
     */
    @Override
    public int compareTo(CandidateMatch other) {
        return Double.compare(other.matchScore, this.matchScore);
    }

    @Override
    public String toString() {
        return "CandidateMatch{" +
                "candidate=" + getCandidateName() +
                ", position=" + getPositionTitle() +
                ", matchScore=" + matchScore +
                ", skillMatch=" + skillMatchPercentage + "%" +
                ", levelMatch=" + levelMatch +
                ", status='" + selectionStatus + '\'' +
                '}';
    }
}
