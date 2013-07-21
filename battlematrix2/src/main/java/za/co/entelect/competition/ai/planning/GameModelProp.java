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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GameModelProp)) return false;

    GameModelProp that = (GameModelProp) o;

    if (key != that.key) return false;
    if (subjectId != null ? !subjectId.equals(that.subjectId) : that.subjectId != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = subjectId != null ? subjectId.hashCode() : 0;
    result = 31 * result + (key != null ? key.hashCode() : 0);
    return result;
  }
}
