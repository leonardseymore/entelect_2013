package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.domain.GameState;

import java.util.Collection;

public abstract class Goal {

  protected int priority;

  protected Goal(int priority) {
    this.priority = priority;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public abstract boolean isFulfilled(GameState gameState);
  public abstract Action nextAction(GameState gameState);
  public abstract void loadAvailableActions(GameState gameState);
}
