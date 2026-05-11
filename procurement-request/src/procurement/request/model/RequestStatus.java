package procurement.request.model;

public enum RequestStatus {

  DRAFT("Draft"),
  SUBMITTED("Submitted"),
  APPROVED("Approved"),
  REJECTED("Rejected");

  private final String displayName;

  RequestStatus(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public boolean isActive() {
    return this == DRAFT || this == SUBMITTED;
  }

  public boolean isTerminal() {
    return this == APPROVED || this == REJECTED;
  }
}
