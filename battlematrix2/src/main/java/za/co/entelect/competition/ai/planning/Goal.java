package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.domain.GameState;

public abstract class Goal implements Comparable<Goal> {

  protected int priority;
  protected GameModel requiredState;

  public Goal(int priority) {
    this.priority = priority;
    this.requiredState = new GameModel();
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public GameModel requiredState() {
    return requiredState;
  }

  public abstract String getName();

  @Override
  public int compareTo(Goal o) {
    return priority > o.priority ? +1 : priority < o.priority ? -1 : 0;
  }
}
