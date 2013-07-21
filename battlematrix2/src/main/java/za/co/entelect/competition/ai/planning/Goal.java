package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.domain.GameState;

public abstract class Goal {

  protected int priority;

  public Goal(int priority) {
    this.priority = priority;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  /*
  public boolean isFulfilled(GameState gameState) {
    return gameState.isInState(requiredState());
  }
  */

  public abstract GameModel requiredState();

  public abstract String getName();
}
