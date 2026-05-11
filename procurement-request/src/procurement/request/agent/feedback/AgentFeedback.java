package procurement.request.agent.feedback;

import java.io.Serializable;

import dev.langchain4j.model.output.structured.Description;

public class AgentFeedback implements Serializable {

  private static final long serialVersionUID = 1L;

  @Description("A message describing the feedback from the agent, such as success or error details.")
  private String message;

  @Description("Whether this feedback represents a successful outcome.")
  private boolean success;

  @Description("The type of feedback provided by the agent.")
  private FeedbackType type;

  @Description("If the feedback type is OPTION, this field contains the details of the feedback option.")
  private FeedbackOption feedbackOption;

  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }

  public boolean isSuccess() { return success; }
  public void setSuccess(boolean success) { this.success = success; }

  public FeedbackType getType() { return type; }
  public void setType(FeedbackType type) { this.type = type; }

  public FeedbackOption getFeedbackOption() { return feedbackOption; }
  public void setFeedbackOption(FeedbackOption feedbackOption) { this.feedbackOption = feedbackOption; }
}