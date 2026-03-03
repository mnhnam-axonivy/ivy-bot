package hr.talent.acquisition.bean;

import hr.talent.acquisition.model.Position;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Managed bean for the Position Approval dialog.
 * Handles position selection, editing, and approval workflow.
 */
@ManagedBean
@ViewScoped
public class PositionApprovalBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public PositionApprovalBean() {
    }

    /**
     * Toggles approval status for a position
     * @param position The position to toggle
     */
    public void toggleApproval(Position position) {
        if (position.isApproved()) {
            position.setApprovalStatus("PENDING");
        } else {
            position.approve();
        }
    }

    /**
     * Approves a position
     * @param position The position to approve
     */
    public void approvePosition(Position position) {
        position.approve();
        showSuccessMessage("Position '" + position.getRoleTitle() + "' approved");
    }

    /**
     * Rejects a position
     * @param position The position to reject
     */
    public void rejectPosition(Position position) {
        position.reject();
        showInfoMessage("Position '" + position.getRoleTitle() + "' rejected");
    }

    /**
     * Approves all positions
     * @param positions The list of positions
     */
    public void approveAll(List<Position> positions) {
        if (positions != null) {
            for (Position pos : positions) {
                pos.approve();
            }
            showSuccessMessage("All " + positions.size() + " positions approved");
        }
    }

    /**
     * Validates that at least one position is approved
     * @param positions The list of positions
     * @return true if at least one position is approved
     */
    public boolean validateApprovals(List<Position> positions) {
        if (positions == null || positions.isEmpty()) {
            showErrorMessage("No positions available for approval");
            return false;
        }

        long approvedCount = positions.stream()
            .filter(Position::isApproved)
            .count();

        if (approvedCount == 0) {
            showErrorMessage("Please approve at least one position before proceeding");
            return false;
        }

        return true;
    }

    /**
     * Gets the list of approved positions
     * @param positions All positions
     * @return List of approved positions
     */
    public List<Position> getApprovedPositions(List<Position> positions) {
        List<Position> approved = new ArrayList<>();
        if (positions != null) {
            for (Position pos : positions) {
                if (pos.isApproved()) {
                    approved.add(pos);
                }
            }
        }
        return approved;
    }

    /**
     * Counts approved positions
     * @param positions All positions
     * @return Number of approved positions
     */
    public int countApproved(List<Position> positions) {
        if (positions == null) return 0;
        return (int) positions.stream()
            .filter(Position::isApproved)
            .count();
    }

    /**
     * Counts rejected positions
     * @param positions All positions
     * @return Number of rejected positions
     */
    public int countRejected(List<Position> positions) {
        if (positions == null) return 0;
        return (int) positions.stream()
            .filter(Position::isRejected)
            .count();
    }

    /**
     * Gets the skill count for a position
     * @param position The position
     * @return Total number of required and preferred skills
     */
    public int getSkillCount(Position position) {
        return position.getTotalSkillCount();
    }

    /**
     * Joins a list of strings with comma separator
     * @param list The list to join
     * @return Comma-separated string
     */
    public String joinWithComma(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "-";
        }
        return String.join(", ", list);
    }

    /**
     * Gets the approval status badge color
     * @param position The position
     * @return CSS class for badge color
     */
    public String getStatusBadgeClass(Position position) {
        if (position.isApproved()) {
            return "success";
        } else if (position.isRejected()) {
            return "danger";
        } else {
            return "warning";
        }
    }

    /**
     * Gets the approval status icon
     * @param position The position
     * @return PrimeIcons icon class
     */
    public String getStatusIcon(Position position) {
        if (position.isApproved()) {
            return "pi-check-circle";
        } else if (position.isRejected()) {
            return "pi-times-circle";
        } else {
            return "pi-clock";
        }
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

    private void showErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
    }
}
