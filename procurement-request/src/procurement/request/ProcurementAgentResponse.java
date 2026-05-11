package procurement.request;

import java.util.List;

import dev.langchain4j.model.output.structured.Description;
import procurement.request.agent.feedback.AgentFeedback;
import procurement.request.model.MaterialProcurementRequest;

public class ProcurementAgentResponse {

  @Description("The response from the agent")
  private String result;

  @Description("List of feedbacks from the agent, such as success or error details.")
  private List<AgentFeedback> feedbackList;

  @Description("The updated procurement request, populated when the request was modified.")
  private MaterialProcurementRequest request;

  public String getResult() { return result; }
  public void setResult(String result) { this.result = result; }

  public List<AgentFeedback> getFeedbackList() { return feedbackList; }
  public void setFeedbackList(List<AgentFeedback> feedbackList) { this.feedbackList = feedbackList; }

  public MaterialProcurementRequest getRequest() { return request; }
  public void setRequest(MaterialProcurementRequest request) { this.request = request; }
}
