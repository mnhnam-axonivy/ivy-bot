package procurement.request.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import procurement.request.model.MaterialProcurementRequest;

@ManagedBean
@ViewScoped
public class ReviewRequestBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private MaterialProcurementRequest request;
  private String approvalComment;
  private String decision; // "APPROVED" or "REJECTED"

  /**
   * Called from XHTML preRender event.
   */
  public void preRender(MaterialProcurementRequest existingRequest) {
    if (request != null) {
      return; // ViewScoped guard
    }
    this.request = existingRequest != null ? existingRequest : new MaterialProcurementRequest();
  }

  public void approve() {
    this.decision = "APPROVED";
  }

  public boolean validateReject() {
    if (approvalComment == null || approvalComment.trim().isEmpty()) {
      FacesContext.getCurrentInstance().addMessage("form:approval-comment",
          new FacesMessage(FacesMessage.SEVERITY_ERROR,
              "A comment is required when rejecting a request.", null));
      return false;
    }
    return true;
  }

  public void reject() {
    this.decision = "REJECTED";
  }

  // Getters and Setters

  public MaterialProcurementRequest getRequest() { return request; }
  public void setRequest(MaterialProcurementRequest request) { this.request = request; }

  public String getApprovalComment() { return approvalComment; }
  public void setApprovalComment(String approvalComment) { this.approvalComment = approvalComment; }

  public String getDecision() { return decision; }
  public void setDecision(String decision) { this.decision = decision; }
}
