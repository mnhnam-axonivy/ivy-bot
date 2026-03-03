package hr.talent.acquisition.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Data class for the Position Approval dialog.
 * Manages the state of position approval workflow.
 */
public class PositionApprovalData {

    private Project project;
    private List<Position> positions;
    private List<Position> selectedPositions;
    private List<Position> approvedPositions;
    private Position editingPosition;
    private Boolean showEditDialog;

    public PositionApprovalData() {
        this.selectedPositions = new ArrayList<>();
        this.approvedPositions = new ArrayList<>();
        this.showEditDialog = false;
    }

    // Getters and setters

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public List<Position> getSelectedPositions() {
        return selectedPositions;
    }

    public void setSelectedPositions(List<Position> selectedPositions) {
        this.selectedPositions = selectedPositions;
    }

    public List<Position> getApprovedPositions() {
        return approvedPositions;
    }

    public void setApprovedPositions(List<Position> approvedPositions) {
        this.approvedPositions = approvedPositions;
    }

    public Position getEditingPosition() {
        return editingPosition;
    }

    public void setEditingPosition(Position editingPosition) {
        this.editingPosition = editingPosition;
    }

    public Boolean getShowEditDialog() {
        return showEditDialog;
    }

    public void setShowEditDialog(Boolean showEditDialog) {
        this.showEditDialog = showEditDialog;
    }
}
