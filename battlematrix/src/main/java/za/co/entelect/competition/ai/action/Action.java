package za.co.entelect.competition.ai.action;

public class Action implements Comparable<Action> {

  private double priority;
  private boolean canInterrupt;
  private long expiryTime;

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

  public long getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(long expiryTime) {
    this.expiryTime = expiryTime;
  }

  public boolean canDoBoth(Action action) {
    return false;
  }

  public boolean isComplete() {
    return true;
  }

  public void execute() {

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
