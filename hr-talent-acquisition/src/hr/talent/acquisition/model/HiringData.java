package hr.talent.acquisition.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.langchain4j.model.output.structured.Description;

/**
 * Main hiring workflow data containing all information for the hiring process.
 */
public class HiringData {

    @Description("Project details for the hiring process")
    private Project project;

    @Description("AI-generated list of positions needed for the project")
    private List<Position> generatedPositions;

    @Description("Positions approved by the project manager for recruitment")
    private List<Position> approvedPositions;

    @Description("Pool of available candidates for screening")
    private List<CandidateProfile> candidatePool;

    @Description("Map of position IDs to matched candidates with scores and rationale")
    private Map<String, List<CandidateMatch>> positionCandidateMap;

    @Description("Final candidate selections mapping position ID to selected candidate ID")
    private Map<String, String> finalSelections;

    @Description("Rejected candidates with rejection reasons")
    private Map<String, String> rejectedCandidates;

    @Description("Current workflow status: ROLE_GENERATION, POSITION_APPROVAL, SCREENING, CANDIDATE_SELECTION, COMPLETED, or CANCELLED")
    private String workflowStatus;

    @Description("Flag indicating whether AI processing is currently in progress")
    private Boolean aiProcessingFlag;

    @Description("Status message or error message for the workflow")
    private String statusMessage;

    public HiringData() {
        this.generatedPositions = new ArrayList<>();
        this.approvedPositions = new ArrayList<>();
        this.candidatePool = new ArrayList<>();
        this.positionCandidateMap = new HashMap<>();
        this.finalSelections = new HashMap<>();
        this.rejectedCandidates = new HashMap<>();
        this.aiProcessingFlag = false;
    }

    // Getters and Setters

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Position> getGeneratedPositions() {
        return generatedPositions;
    }

    public void setGeneratedPositions(List<Position> generatedPositions) {
        this.generatedPositions = generatedPositions;
    }

    public List<Position> getApprovedPositions() {
        return approvedPositions;
    }

    public void setApprovedPositions(List<Position> approvedPositions) {
        this.approvedPositions = approvedPositions;
    }

    public List<CandidateProfile> getCandidatePool() {
        return candidatePool;
    }

    public void setCandidatePool(List<CandidateProfile> candidatePool) {
        this.candidatePool = candidatePool;
    }

    public Map<String, List<CandidateMatch>> getPositionCandidateMap() {
        return positionCandidateMap;
    }

    public void setPositionCandidateMap(Map<String, List<CandidateMatch>> positionCandidateMap) {
        this.positionCandidateMap = positionCandidateMap;
    }

    public Map<String, String> getFinalSelections() {
        return finalSelections;
    }

    public void setFinalSelections(Map<String, String> finalSelections) {
        this.finalSelections = finalSelections;
    }

    public Map<String, String> getRejectedCandidates() {
        return rejectedCandidates;
    }

    public void setRejectedCandidates(Map<String, String> rejectedCandidates) {
        this.rejectedCandidates = rejectedCandidates;
    }

    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    public Boolean getAiProcessingFlag() {
        return aiProcessingFlag;
    }

    public void setAiProcessingFlag(Boolean aiProcessingFlag) {
        this.aiProcessingFlag = aiProcessingFlag;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
