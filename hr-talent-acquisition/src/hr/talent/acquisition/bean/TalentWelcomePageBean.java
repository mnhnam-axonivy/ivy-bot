package hr.talent.acquisition.bean;

import java.io.Serializable;
import java.util.Optional;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ch.ivyteam.ivy.cm.ContentObject;
import ch.ivyteam.ivy.cm.ContentObjectValue;
import ch.ivyteam.ivy.environment.Ivy;

@ManagedBean
@ViewScoped
public class TalentWelcomePageBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final String FIRST_CV_PATH  = "/File/CV/FirstCV";
  private static final String SECOND_CV_PATH = "/File/CV/SecondCV";
  private static final String THIRD_CV_PATH  = "/File/CV/ThirdCV";

  public StreamedContent downloadFirstCv() {
    return downloadCv(FIRST_CV_PATH, "FirstCV.pdf");
  }

  public StreamedContent downloadSecondCv() {
    return downloadCv(SECOND_CV_PATH, "SecondCV.pdf");
  }

  public StreamedContent downloadThirdCv() {
    return downloadCv(THIRD_CV_PATH, "ThirdCV.pdf");
  }

  private StreamedContent downloadCv(String cmsPath, String fileName) {
    Optional<ContentObject> obj = Ivy.cm().findObject(cmsPath);
    if (obj.map(ContentObject::exists).orElse(false)) {
      ContentObjectValue cov = obj.map(ContentObject::values)
                                  .map(v -> v.getFirst())
                                  .orElse(null);
      if (cov != null) {
        return DefaultStreamedContent.builder()
            .name(fileName)
            .contentType("application/pdf")
            .stream(() -> cov.read().inputStream())
            .build();
      }
    }
    Ivy.log().warn("CMS file not found: " + cmsPath);
    return null;
  }
}
