package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.domain.GameState;

import java.util.Collection;

public abstract class Action implements Comparable<Action> {

  protected double priority;
  protected boolean canInterrupt = true;
  protected int expiryTime;

  public double getPriority() {
    return priority;
  }

  public boolean isCanInterrupt() {
    return canInterrupt;
  }

  public int getExpiryTime() {
    return expiryTime;
  }

  public boolean canDoBoth(Action action) {
    return false;
  }

  public boolean isComplete() {
    return true;
  }

  protected abstract void doExecute(GameState gameState);

  public void execute(GameState gameState) {
    expiryTime--;
    doExecute(gameState);
  }

  public void cancel() {
    expiryTime = 0;
  }

  public abstract int getCost(GameState gameState);

  public abstract Collection<GameModelProp> getPreconditions();
  public abstract Collection<GameModelProp> getEffects();

  @Override
  public int compareTo(Action o) {
    int result = priority > o.priority ? 1 : priority < o.priority ? -1 : 0;
    if (result == 0) {
      return expiryTime < o.expiryTime ? 1 : expiryTime > o.expiryTime ? -1 : 0;
    }
    return result;
  }

  public String getDescription() {
    return  "action_desc";
  }
}
