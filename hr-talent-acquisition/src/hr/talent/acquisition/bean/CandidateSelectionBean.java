package hr.talent.acquisition.bean;

import hr.talent.acquisition.model.CandidateMatch;
import hr.talent.acquisition.model.CandidateProfile;
import hr.talent.acquisition.model.Position;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Managed bean for the Candidate Selection dialog.
 * Handles candidate selection, viewing details, and finalizing selections.
 */
@ManagedBean
@ViewScoped
public class CandidateSelectionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public CandidateSelectionBean() {
    }

    /**
     * Gets the list of matches for a specific position
     * @param positionCandidateMap The complete map
     * @param positionId The position ID
     * @return List of candidate matches for this position
     */
    public List<CandidateMatch> getMatchesForPosition(
            Map<String, List<CandidateMatch>> positionCandidateMap,
            String positionId) {
        if (positionCandidateMap == null || positionId == null) {
            return new ArrayList<>();
        }
        List<CandidateMatch> matches = positionCandidateMap.get(positionId);
        return matches != null ? matches : new ArrayList<>();
    }

    /**
     * Selects a candidate for a position
     * @param match The candidate match
     * @param selections The selections map
     */
    public void selectCandidate(CandidateMatch match,
                                 Map<String, List<String>> selections) {
        if (match == null || selections == null) return;

        match.select();
        String positionId = match.getPosition().getPositionId();
        String candidateId = match.getCandidate().getId();

        selections.putIfAbsent(positionId, new ArrayList<>());
        if (!selections.get(positionId).contains(candidateId)) {
            selections.get(positionId).add(candidateId);
        }

        showInfoMessage("Selected " + match.getCandidateName() +
            " for " + match.getPositionTitle());
    }

    /**
     * Rejects a candidate for a position
     * @param match The candidate match
     * @param rejections The rejections map
     */
    public void rejectCandidate(CandidateMatch match,
                                 Map<String, List<String>> rejections) {
        if (match == null || rejections == null) return;

        match.reject();
        String positionId = match.getPosition().getPositionId();
        String candidateId = match.getCandidate().getId();

        rejections.putIfAbsent(positionId, new ArrayList<>());
        if (!rejections.get(positionId).contains(candidateId)) {
            rejections.get(positionId).add(candidateId);
        }

        showInfoMessage("Rejected " + match.getCandidateName());
    }

    /**
     * Counts selected candidates for a position
     * @param selections The selections map
     * @param positionId The position ID
     * @return Number of selected candidates
     */
    public int countSelected(Map<String, List<String>> selections, String positionId) {
        if (selections == null || positionId == null) return 0;
        List<String> selected = selections.get(positionId);
        return selected != null ? selected.size() : 0;
    }

    /**
     * Validates that correct number of candidates are selected for each position
     * @param positions All positions
     * @param selections The selections map
     * @return true if valid
     */
    public boolean validateSelections(List<Position> positions,
                                       Map<String, List<String>> selections) {
        if (positions == null || positions.isEmpty()) {
            showErrorMessage("No positions available");
            return false;
        }

        boolean isValid = true;
        for (Position pos : positions) {
            int selected = countSelected(selections, pos.getPositionId());
            int required = pos.getQuantity();

            if (selected == 0) {
                showErrorMessage("Please select at least one candidate for: " +
                    pos.getRoleTitle());
                isValid = false;
            } else if (selected > required) {
                showWarningMessage("Too many candidates selected for " +
                    pos.getRoleTitle() + " (need " + required + ", selected " + selected + ")");
                isValid = false;
            }
        }

        return isValid;
    }

    /**
     * Gets the match quality badge color
     * @param matchScore The match score
     * @return CSS severity class
     */
    public String getMatchQualitySeverity(double matchScore) {
        if (matchScore >= 80) return "success";
        if (matchScore >= 70) return "info";
        if (matchScore >= 60) return "warning";
        return "danger";
    }

    /**
     * Gets the match quality label
     * @param matchScore The match score
     * @return Quality label
     */
    public String getMatchQualityLabel(double matchScore) {
        if (matchScore >= 80) return "Excellent";
        if (matchScore >= 70) return "Good";
        if (matchScore >= 60) return "Fair";
        return "Poor";
    }

    /**
     * Formats match score as percentage
     * @param score The score (0-100)
     * @return Formatted string
     */
    public String formatScore(double score) {
        return String.format("%.0f%%", score);
    }

    /**
     * Gets CSS class for selection status
     * @param match The candidate match
     * @return CSS class
     */
    public String getSelectionStatusClass(CandidateMatch match) {
        if (match.isSelected()) return "p-highlight";
        if (match.isRejected()) return "p-disabled";
        return "";
    }

    /**
     * Checks if candidate is selected for a position
     * @param match The candidate match
     * @param selections The selections map
     * @return true if selected
     */
    public boolean isSelected(CandidateMatch match,
                               Map<String, List<String>> selections) {
        if (match == null || selections == null) return false;
        String positionId = match.getPosition().getPositionId();
        String candidateId = match.getCandidate().getId();
        List<String> selected = selections.get(positionId);
        return selected != null && selected.contains(candidateId);
    }

    /**
     * Gets the experience years for a candidate
     * @param candidate The candidate profile
     * @return Years of experience
     */
    public String getExperienceYears(CandidateProfile candidate) {
        if (candidate == null) return "0";
        int months = candidate.calculateTotalExperienceMonths();
        int years = months / 12;
        int remainingMonths = months % 12;
        if (remainingMonths == 0) {
            return years + " years";
        } else {
            return years + "y " + remainingMonths + "m";
        }
    }

    /**
     * Gets the skill count for a candidate
     * @param candidate The candidate profile
     * @return Number of skills
     */
    public int getSkillCount(CandidateProfile candidate) {
        if (candidate == null || candidate.getTechnicalSkills() == null) {
            return 0;
        }
        return candidate.getTechnicalSkills().size();
    }

    /**
     * Gets total number of selections across all positions
     * @param selections The selections map
     * @return Total selections count
     */
    public int getTotalSelections(Map<String, List<String>> selections) {
        if (selections == null) return 0;
        return selections.values().stream()
            .mapToInt(List::size)
            .sum();
    }

    // Utility methods for messages

    private void showSuccessMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", message));
    }

    private void showInfoMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
    }

    private void showWarningMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", message));
    }

    private void showErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
    }
}
