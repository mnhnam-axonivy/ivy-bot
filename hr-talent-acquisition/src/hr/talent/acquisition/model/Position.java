package hr.talent.acquisition.model;

import dev.langchain4j.model.output.structured.Description;
import hr.talent.acquisition.enums.DeveloperLevel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a job position/role that needs to be filled.
 * Can be AI-generated or manually created, and requires PM approval before proceeding.
 */
public class Position {

    @Description("Unique identifier for the position")
    private String positionId;

    @Description("Role or position title (e.g., 'Senior Backend Engineer', 'Frontend Developer')")
    private String roleTitle;

    @Description("Detailed description of the role responsibilities and expectations")
    private String roleDescription;

    @Description("Required technical skills for this position")
    private List<String> requiredSkills;

    @Description("Nice-to-have technical skills that are preferred but not mandatory")
    private List<String> preferredSkills;

    @Description("Required seniority level: ENTRY, MID, or SENIOR")
    private DeveloperLevel requiredLevel;

    @Description("Number of people needed for this position")
    private int quantity;

    @Description("Key responsibilities and duties for this role")
    private List<String> keyResponsibilities;

    @Description("Approval status: PENDING, APPROVED, REJECTED")
    private String approvalStatus;

    @Description("PM comments or feedback on this position")
    private String pmComments;

    public Position() {
        this.positionId = UUID.randomUUID().toString();
        this.requiredSkills = new ArrayList<>();
        this.preferredSkills = new ArrayList<>();
        this.keyResponsibilities = new ArrayList<>();
        this.approvalStatus = "PENDING";
        this.quantity = 1;
    }

    public Position(String roleTitle, DeveloperLevel requiredLevel) {
        this();
        this.roleTitle = roleTitle;
        this.requiredLevel = requiredLevel;
    }

    // Getters and setters

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public List<String> getPreferredSkills() {
        return preferredSkills;
    }

    public void setPreferredSkills(List<String> preferredSkills) {
        this.preferredSkills = preferredSkills;
    }

    public DeveloperLevel getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(DeveloperLevel requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getKeyResponsibilities() {
        return keyResponsibilities;
    }

    public void setKeyResponsibilities(List<String> keyResponsibilities) {
        this.keyResponsibilities = keyResponsibilities;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getPmComments() {
        return pmComments;
    }

    public void setPmComments(String pmComments) {
        this.pmComments = pmComments;
    }

    // Fluent builder methods

    public Position withDescription(String roleDescription) {
        this.roleDescription = roleDescription;
        return this;
    }

    public Position withRequiredSkill(String skill) {
        if (this.requiredSkills == null) {
            this.requiredSkills = new ArrayList<>();
        }
        this.requiredSkills.add(skill);
        return this;
    }

    public Position withRequiredSkills(List<String> skills) {
        this.requiredSkills = skills != null ? skills : new ArrayList<>();
        return this;
    }

    public Position withPreferredSkill(String skill) {
        if (this.preferredSkills == null) {
            this.preferredSkills = new ArrayList<>();
        }
        this.preferredSkills.add(skill);
        return this;
    }

    public Position withPreferredSkills(List<String> skills) {
        this.preferredSkills = skills != null ? skills : new ArrayList<>();
        return this;
    }

    public Position withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Position withResponsibility(String responsibility) {
        if (this.keyResponsibilities == null) {
            this.keyResponsibilities = new ArrayList<>();
        }
        this.keyResponsibilities.add(responsibility);
        return this;
    }

    public Position withResponsibilities(List<String> responsibilities) {
        this.keyResponsibilities = responsibilities != null ? responsibilities : new ArrayList<>();
        return this;
    }

    public Position withComments(String comments) {
        this.pmComments = comments;
        return this;
    }

    // Utility methods

    public boolean isApproved() {
        return "APPROVED".equalsIgnoreCase(approvalStatus);
    }

    public boolean isRejected() {
        return "REJECTED".equalsIgnoreCase(approvalStatus);
    }

    public boolean isPending() {
        return "PENDING".equalsIgnoreCase(approvalStatus);
    }

    public void approve() {
        this.approvalStatus = "APPROVED";
    }

    public void reject() {
        this.approvalStatus = "REJECTED";
    }

    public int getTotalSkillCount() {
        return (requiredSkills != null ? requiredSkills.size() : 0) +
               (preferredSkills != null ? preferredSkills.size() : 0);
    }

    @Override
    public String toString() {
        return "Position{" +
                "positionId='" + positionId + '\'' +
                ", roleTitle='" + roleTitle + '\'' +
                ", requiredLevel=" + requiredLevel +
                ", quantity=" + quantity +
                ", requiredSkills=" + (requiredSkills != null ? requiredSkills.size() : 0) +
                ", approvalStatus='" + approvalStatus + '\'' +
                '}';
    }
}
