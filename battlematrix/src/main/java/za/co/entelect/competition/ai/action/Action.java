package za.co.entelect.competition.ai.action;

public abstract class Action implements Comparable<Action> {

  protected double priority;
  protected boolean canInterrupt = true;
  protected int expiryTime;

  public double getPriority() {
    return priority;
  }

  public void setPriority(double priority) {
    this.priority = priority;
  }

  public boolean isCanInterrupt() {
    return canInterrupt;
  }

  public void setCanInterrupt(boolean canInterrupt) {
    this.canInterrupt = canInterrupt;
  }

  public int getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(int expiryTime) {
    this.expiryTime = expiryTime;
  }

  public boolean canDoBoth(Action action) {
    return false;
  }

  public boolean isComplete() {
    return true;
  }

  protected abstract void doExecute();

  public void execute() {
    expiryTime--;
    doExecute();
  }

  public void cancel() {
    expiryTime = 0;
  }

  @Override
  public int compareTo(Action o) {
    int result = priority > o.priority ? 1 : priority < o.priority ? -1 : 0;
    if (result == 0) {
      return expiryTime < o.expiryTime ? 1 : expiryTime > o.expiryTime ? -1 : 0;
    }
    return result;
  }
}
