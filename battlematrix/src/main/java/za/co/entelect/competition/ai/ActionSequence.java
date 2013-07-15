package za.co.entelect.competition.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class ActionSequence extends Action {

  private List<Action> actions = new ArrayList<>();
  private int activeIndex = 0;

  @Override
  public boolean isCanInterrupt() {
    return actions.get(activeIndex).isCanInterrupt();
  }

  @Override
  public boolean canDoBoth(Action o) {
    for (Action action : actions) {
      if (!action.canDoBoth(o)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isComplete() {
    return activeIndex >= actions.size();
  }

  @Override
  public void execute() {
    actions.get(activeIndex).execute();
    if (actions.get(activeIndex).isComplete()) {
      activeIndex++;
    }
  }
}
