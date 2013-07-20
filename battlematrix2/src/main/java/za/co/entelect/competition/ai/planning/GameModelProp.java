package za.co.entelect.competition.ai.planning;

public class GameModelProp<T> {
  public String subjectId;
  public GameModelPropKey key;
  public T value;

  public GameModelProp(String subjectId, GameModelPropKey key, T value) {
    this.subjectId = subjectId;
    this.key = key;
    this.value = value;
  }
}
