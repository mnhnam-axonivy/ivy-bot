package procurement.request.model;

import ch.ivyteam.ivy.environment.Ivy;

public enum RequestPriority {

  NORMAL,
  HIGH,
  URGENT,
  LOW;

  public String getDisplayName() {
    return Ivy.cms().co("/Labels/Priority/" + name());
  }
}
