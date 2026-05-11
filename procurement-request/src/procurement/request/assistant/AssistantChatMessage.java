package procurement.request.assistant;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dev.langchain4j.model.output.structured.Description;
import procurement.request.agent.feedback.AgentFeedback;

public class AssistantChatMessage implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final DateTimeFormatter FMT =
      DateTimeFormatter.ofPattern("HH:mm  dd-MM-yyyy");

  @Description("The role of the message sender: 'user' or 'assistant'.")
  private String role;

  @Description("The text content of the message.")
  private String content;

  @Description("The timestamp when the message was created, formatted as HH:mm dd-MM-yyyy.")
  private String timestamp;

  @Description("Optional list of per-item inventory feedback, present only on assistant messages that include an analysis result.")
  private List<AgentFeedback> feedbackList;

  public AssistantChatMessage() {}

  /** Creates a plain text message with an auto-generated timestamp. */
  public AssistantChatMessage(String role, String content) {
    this.role = role;
    this.content = content;
    this.timestamp = LocalDateTime.now().format(FMT);
  }

  /** Creates an assistant message that carries both a text result and an inventory feedback list. */
  public AssistantChatMessage(String role, String content, List<AgentFeedback> feedbackList) {
    this(role, content);
    this.feedbackList = feedbackList;
  }

  public boolean isUser() {
    return "user".equals(role);
  }

  public boolean isAssistant() {
    return "assistant".equals(role);
  }

  public String getRole()           { return role; }
  public void setRole(String role)  { this.role = role; }

  public String getContent()              { return content; }
  public void setContent(String content)  { this.content = content; }

  public String getTimestamp()                { return timestamp; }
  public void setTimestamp(String timestamp)  { this.timestamp = timestamp; }

  public List<AgentFeedback> getFeedbackList()                      { return feedbackList; }
  public void setFeedbackList(List<AgentFeedback> feedbackList)     { this.feedbackList = feedbackList; }
}

