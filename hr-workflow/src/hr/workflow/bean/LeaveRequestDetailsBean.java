package hr.workflow.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import hr.workflow.enums.LeaveType;

@ManagedBean
@ViewScoped
public class LeaveRequestDetailsBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<LeaveType> leaveTypes;

  @PostConstruct
  public void init() {
    leaveTypes = Arrays.asList(LeaveType.values());
  }

  public List<LeaveType> getLeaveTypes() {
    return leaveTypes;
  }
}
