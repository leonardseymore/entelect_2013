package za.co.entelect.competition.ai.blackboard;

public class BlackboardFactAttr<T> {
  private T value;
  private float confidence;

  public BlackboardFactAttr(T value, float confidence) {
    this.value = value;
    this.confidence = confidence;
  }
}
