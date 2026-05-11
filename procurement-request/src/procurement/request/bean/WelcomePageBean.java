package procurement.request.bean;

import java.io.Serializable;
import java.util.Optional;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ch.ivyteam.ivy.cm.ContentObject;
import ch.ivyteam.ivy.cm.ContentObjectValue;
import ch.ivyteam.ivy.environment.Ivy;
import procurement.request.mock.GenerateMock;

@ManagedBean
@ViewScoped
public class WelcomePageBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private String step1Status = "pending";
  private boolean generationStarted = false;
  private boolean generationDone = false;

  private static final String MOCK_PDF_CMS_PATH = "/File/ProcurementRequest/MockRequest";

  // ── Step action (invoked by p:remoteCommand) ──────────────────────────────

  public void generateMockData() {
    generationStarted = true;
    step1Status = "running";
    try {
      GenerateMock.create();
      step1Status = "completed";
    } catch (Exception e) {
      step1Status = "failed";
      Ivy.log().error("Mock data generation failed", e);
    }
    generationDone = step1Status.equals("completed");
  }

  // ── CSS helpers for the timeline ──────────────────────────────────────────

  public String getStepClass(String status) {
    return switch (status) {
      case "running"   -> "so-checklist-item running so-tl-item";
      case "completed" -> "so-checklist-item completed so-tl-item";
      case "failed"    -> "so-checklist-item failed so-tl-item";
      default          -> "so-checklist-item pending so-tl-item";
    };
  }

  public String getBubbleClass(String status) {
    return switch (status) {
      case "running"   -> "so-tl-bubble so-tl-bubble-running";
      case "completed" -> "so-tl-bubble so-tl-bubble-completed";
      case "failed"    -> "so-tl-bubble so-tl-bubble-failed";
      default          -> "so-tl-bubble so-tl-bubble-pending";
    };
  }

  public String getStatusIcon(String status) {
    return switch (status) {
      case "running"   -> "ti ti-loader so-spin";
      case "completed" -> "ti ti-circle-check";
      case "failed"    -> "ti ti-circle-x";
      default          -> "ti ti-clock";
    };
  }

  // ── Download mock PDF from CMS ────────────────────────────────────────────

  public StreamedContent downloadMockPdf() {
    Optional<ContentObject> obj = Ivy.cm().findObject(MOCK_PDF_CMS_PATH);
    if (obj.map(ContentObject::exists).orElse(false)) {
      ContentObjectValue cov = obj.map(ContentObject::values)
                                  .map(v -> v.getFirst())
                                  .orElse(null);
      if (cov != null) {
        return DefaultStreamedContent.builder()
            .name("MockProcurementRequest.pdf")
            .contentType("application/pdf")
            .stream(() -> cov.read().inputStream())
            .build();
      }
    }
    Ivy.log().warn("CMS file not found: " + MOCK_PDF_CMS_PATH);
    return null;
  }

  // ── Getters ───────────────────────────────────────────────────────────────

  public String getStep1Status() { return step1Status; }
  public boolean isGenerationStarted() { return generationStarted; }
  public boolean isGenerationDone() { return generationDone; }
}
