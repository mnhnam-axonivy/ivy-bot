package hr.workflow.enums;

public enum LeaveType {
  VACATION("Vacation"),
  SICK_LEAVE("Sick Leave"),
  PERSONAL("Personal"),
  PARENTAL("Parental"),
  BEREAVEMENT("Bereavement");

  private final String displayName;

  LeaveType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public static LeaveType fromString(String value) {
    if (value == null) return null;
    for (LeaveType type : values()) {
      if (type.displayName.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
        return type;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
