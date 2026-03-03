package hr.talent.acquisition.model;

/**
 * Data class for the Project Creation dialog.
 * Manages the state of project creation workflow.
 */
public class ProjectCreationData {

    private Project project;
    private String validationMessage;
    private Boolean isValid;

    public ProjectCreationData() {
        this.project = new Project();
        this.isValid = false;
    }

    // Getters and setters

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
