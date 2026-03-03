package hr.talent.acquisition.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.process.call.SubProcessCallStartEvent;
import ch.ivyteam.ivy.process.call.SubProcessSearchFilter;
import ch.ivyteam.ivy.process.call.SubProcessSearchFilter.SearchScope;
import ch.ivyteam.ivy.security.exec.Sudo;
import hr.talent.acquisition.exception.BpmExceptionHandler;
import hr.talent.acquisition.model.CandidateProfile;
import hr.talent.acquisition.service.CandidateService;

/**
 * Managed bean for Talent Management UI.
 * Handles CV upload, parsing via AI agent, and candidate display.
 */
@ManagedBean
@ViewScoped
public class TalentManagementBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String CREATE_CANDIDATE_SIGNATURE = "createCandidate(java.io.InputStream)";
    
    private List<CandidateProfile> candidates;
    private CandidateProfile selectedCandidate;
    private boolean processing = false;
    private String statusMessage = "";
    private boolean showDetails = false;

    @PostConstruct
    public void init() {
        loadCandidates();
    }

    /**
     * Load/reload candidates from repository.
     */
    public void loadCandidates() {
        this.candidates = CandidateService.getAllCandidates();
        if (this.candidates == null) {
            this.candidates = new ArrayList<>();
        }
    }

    /**
     * Get all candidates.
     */
    public List<CandidateProfile> getCandidates() {
        return candidates;
    }

    /**
     * Handle CV file upload event.
     */
    public void handleCvUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        
        if (file == null || file.getContent() == null || file.getContent().length == 0) {
            addErrorMessage("Please select a valid markdown file.");
            return;
        }
        
        try {
            processing = true;
            statusMessage = "Processing CV...";

            InputStream cvStream = file.getInputStream();

            // Call the CandidateCreator agent to parse CV
            Map<String, Object> params = Map.of("cvFile", cvStream);
            Map<String, Object> result = startSubProcessInSecurityContext(CREATE_CANDIDATE_SIGNATURE, params);
            CandidateProfile candidate = (CandidateProfile) result.get("candidateProfile");

            String error = (String) result.get("error");
            if (StringUtils.isNotBlank(error)) {
                processing = false;
                return;
            }

            if (candidate == null) {
                addErrorMessage("Failed to parse CV. Please check the format.");
                processing = false;
                return;
            }

            // Save to repository
            CandidateService.addCandidate(candidate);
            
            // Reload candidates from repository
            loadCandidates();
            PrimeFaces.current().ajax().update("form:candidatesPanel", "form:messages");

            statusMessage = "";
            addSuccessMessage("CV uploaded and parsed successfully for: " + candidate.getFullName());
            
        } catch (Exception e) {
            Ivy.log().error("Error processing CV upload: {0}", e, e.getMessage());
            BpmExceptionHandler.logFormattedError(e);
            addErrorMessage("Error processing CV: " + e.getMessage());
        } finally {
            processing = false;
        }
    }

    /**
     * Show candidate details.
     */
    public void showCandidateDetails(CandidateProfile candidate) {
        this.selectedCandidate = candidate;
        this.showDetails = true;
    }

    /**
     * Close candidate details.
     */
    public void closeDetails() {
        this.selectedCandidate = null;
        this.showDetails = false;
    }

    /**
     * Remove a candidate.
     */
    public void removeCandidate(CandidateProfile candidate) {
        if (candidate != null && candidate.getId() != null) {
            CandidateService.removeCandidate(candidate.getId());
            loadCandidates();
            addSuccessMessage("Candidate removed: " + candidate.getFullName());
        }
    }

    /**
     * Get the count of skills for a candidate.
     */
    public int getSkillCount(CandidateProfile candidate) {
        return candidate != null && candidate.getTechnicalSkills() != null 
                ? candidate.getTechnicalSkills().size() : 0;
    }

    /**
     * Get years of experience (simplified).
     */
    public String getExperienceYears(CandidateProfile candidate) {
        if (candidate == null || candidate.getWorkHistories() == null) {
            return "N/A";
        }
        int totalMonths = candidate.getWorkHistories().size() * 24; // Simplified
        int years = totalMonths / 12;
        return years + "+ years";
    }

    private void addSuccessMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", message));
    }

    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", message));
    }

    // Getters and setters
    public CandidateProfile getSelectedCandidate() {
        return selectedCandidate;
    }

    public void setSelectedCandidate(CandidateProfile selectedCandidate) {
        this.selectedCandidate = selectedCandidate;
    }

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public boolean isShowDetails() {
        return showDetails;
    }

    public void setShowDetails(boolean showDetails) {
        this.showDetails = showDetails;
    }

    public boolean hasNoCandidates() {
        return candidates == null || candidates.isEmpty();
    }

    /**
     * Returns a data URI (data:image/jpeg;base64,...) for the candidate's face photo,
     * or null if no face is available. Used directly as <h:graphicImage value="..."/>.
     */
    public String getFaceAsDataUri(CandidateProfile candidate) {
        if (candidate == null || candidate.getFace() == null || candidate.getFace().isEmpty()) {
            return null;
        }
        return "data:image/jpeg;base64," + candidate.getFace();
    }

    /**
     * Returns up to 2 uppercase initials from the candidate's full name.
     */
    public String getInitials(CandidateProfile candidate) {
        if (candidate == null || StringUtils.isBlank(candidate.getFullName())) {
            return "?";
        }
        String[] parts = candidate.getFullName().trim().split("\\s+");
        return parts.length >= 2
                ? String.valueOf(parts[0].charAt(0)).toUpperCase() + String.valueOf(parts[1].charAt(0)).toUpperCase()
                : String.valueOf(parts[0].charAt(0)).toUpperCase();
    }


    /**
   * Find the subprocess in security context scope then calls it with the given signature
   * with the given params. Exactly one subprocess with the given signature will be called.
   *
   * @param signature The signature of the subprocess to be triggered.
   * @param params The parameters to pass to the process.
   * @return The response of the process execution.
   */

  public Map<String, Object> startSubProcessInSecurityContext(String signature, Map<String, Object> params) {
    return startSubProcess(signature, params, SearchScope.SECURITY_CONTEXT);
  }

  private Map<String, Object> startSubProcess(String signature, Map<String, Object> params, SearchScope scope) {
    return Sudo.get(() -> {
      var filter = SubProcessSearchFilter.create()
          .setSearchScope(scope)
          .setSignature(signature).toFilter();

      // Find subprocess
      var subProcessStartList = SubProcessCallStartEvent.find(filter);
      if (CollectionUtils.isEmpty(subProcessStartList)) {
        return null;
      }
      var subProcessStart = subProcessStartList.get(0);

      // Add param to the subprocess and execute
      return Optional.ofNullable(params).map(Map::entrySet).isEmpty() ? subProcessStart.call().asMap() : startSubProcessWithParams(subProcessStart, params);
    });
  }

  private Map<String, Object> startSubProcessWithParams(SubProcessCallStartEvent subProcess, Map<String, Object> params) {
    Map<String, Object> result = null;
    List<Map.Entry<String, Object>> entryList = new ArrayList<>(params.entrySet());

    for (Map.Entry<String, Object> entry : entryList) {
      if (entryList.indexOf(entry) != entryList.size() - 1) {
        subProcess.withParam(entry.getKey(), entry.getValue());
      } else {
        result = subProcess.withParam(entry.getKey(), entry.getValue()).call().asMap();
      }
    }

    return result;
  }
}
