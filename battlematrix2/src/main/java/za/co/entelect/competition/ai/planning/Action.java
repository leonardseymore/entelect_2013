package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Action implements Comparable<Action> {

  private boolean executed = false;
  protected double priority;
  protected boolean canInterrupt = true;
  protected int expiryTime;
  protected Collection<GameModelProp> preconditions = new ArrayList<>();
  protected Collection<GameModelProp> effects = new ArrayList<>();

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

  public boolean isComplete(GameState gameState) {
    return executed;
  }

  public abstract boolean isValid(GameState gameState);

  protected abstract void doExecute(GameState gameState);

  public void execute(GameState gameState) {
    expiryTime--;
    doExecute(gameState);
    executed = true;
  }

  public void cancel() {
    expiryTime = 0;
  }

  public abstract int getCost();

  public Collection<GameModelProp> getPreconditions() {
    return preconditions;
  }

  public Collection<GameModelProp> getEffects() {
    return effects;
  }

  @Override
  public int compareTo(Action o) {
    int result = priority > o.priority ? 1 : priority < o.priority ? -1 : 0;
    if (result == 0) {
      return expiryTime < o.expiryTime ? 1 : expiryTime > o.expiryTime ? -1 : 0;
    }
    return result;
  }

  public abstract String getName();
}
