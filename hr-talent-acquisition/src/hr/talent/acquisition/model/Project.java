package hr.talent.acquisition.model;

import java.util.List;
import java.util.UUID;

import dev.langchain4j.model.output.structured.Description;

/**
 * Represents a project that requires hiring new team members.
 * Used as input for the hiring workflow to determine staffing needs.
 */
public class Project {

    @Description("Unique identifier for the project")
    private String projectId;

    @Description("Name of the project")
    private String projectName;

    @Description("Detailed description of the project goals and objectives")
    private String projectDescription;

    @Description("Technical requirements and constraints for the project")
    private String technicalRequirements;

    @Description("Expected project timeline or duration (e.g., '6 months', '1 year')")
    private String timeline;

    @Description("Project budget allocated for hiring")
    private String budget;

    @Description("Technology stack or preferred technologies for the project")
    private String preferredTechStack;

    @Description("Expected team size for this project")
    private String expectedTeamSize;

    private List<Position> positions;

    public Project() {
        this.projectId = UUID.randomUUID().toString();
    }

    public Project(String projectName, String projectDescription) {
        this();
        this.projectName = projectName;
        this.projectDescription = projectDescription;
    }

    // Getters and setters

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getTechnicalRequirements() {
        return technicalRequirements;
    }

    public void setTechnicalRequirements(String technicalRequirements) {
        this.technicalRequirements = technicalRequirements;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getPreferredTechStack() {
        return preferredTechStack;
    }

    public void setPreferredTechStack(String preferredTechStack) {
        this.preferredTechStack = preferredTechStack;
    }

    public String getExpectedTeamSize() {
        return expectedTeamSize;
    }

    public void setExpectedTeamSize(String expectedTeamSize) {
        this.expectedTeamSize = expectedTeamSize;
    }

    // Fluent builder methods

    public Project withTechnicalRequirements(String technicalRequirements) {
        this.technicalRequirements = technicalRequirements;
        return this;
    }

    public Project withTimeline(String timeline) {
        this.timeline = timeline;
        return this;
    }

    public Project withBudget(String budget) {
        this.budget = budget;
        return this;
    }

    public Project withPreferredTechStack(String preferredTechStack) {
        this.preferredTechStack = preferredTechStack;
        return this;
    }

    public Project withExpectedTeamSize(String expectedTeamSize) {
        this.expectedTeamSize = expectedTeamSize;
        return this;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", technicalRequirements='" + technicalRequirements + '\'' +
                ", timeline='" + timeline + '\'' +
                ", budget='" + budget + '\'' +
                ", preferredTechStack='" + preferredTechStack + '\'' +
                ", expectedTeamSize='" + expectedTeamSize + '\'' +
                '}';
    }
}
