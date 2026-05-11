package procurement.request.assistant;

public record AgentGuidance(String questionPattern, String instruction) {

  public String getQuestionPattern() {
    return questionPattern;
  }

  public String getInstruction() {
    return instruction;
  }

  public String toPromptLine() {
    return "- When the user asks \"" + questionPattern + "\": " + instruction;
  }
}
