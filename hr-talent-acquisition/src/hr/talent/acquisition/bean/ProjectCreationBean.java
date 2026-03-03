package hr.talent.acquisition.bean;

import hr.talent.acquisition.model.Project;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Managed bean for the Project Creation dialog.
 * Handles validation and submission of new project details.
 */
@ManagedBean
@ViewScoped
public class ProjectCreationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public ProjectCreationBean() {
    }

    /**
     * Validates the project form before submission.
     * @param project The project to validate
     * @return true if valid, false otherwise
     */
    public boolean validateProject(Project project) {
        FacesContext context = FacesContext.getCurrentInstance();
        boolean isValid = true;

        if (project.getProjectName() == null || project.getProjectName().trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", "Project name is required"));
            isValid = false;
        }

        if (project.getProjectDescription() == null || project.getProjectDescription().trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", "Project description is required"));
            isValid = false;
        }

        if (project.getTechnicalRequirements() == null || project.getTechnicalRequirements().trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", "Technical requirements are required"));
            isValid = false;
        }

        if (project.getTimeline() == null || project.getTimeline().trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", "Timeline is required"));
            isValid = false;
        }

        if (project.getExpectedTeamSize() == null || project.getExpectedTeamSize().trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", "Expected team size is required"));
            isValid = false;
        }

        return isValid;
    }

    /**
     * Displays a success message
     */
    public void showSuccessMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Success", message));
    }

    /**
     * Displays an error message
     */
    public void showErrorMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
            "Error", message));
    }
}
