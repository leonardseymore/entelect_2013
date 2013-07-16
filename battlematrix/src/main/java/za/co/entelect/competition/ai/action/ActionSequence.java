package za.co.entelect.competition.ai.action;

import java.util.ArrayList;
import java.util.List;

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
  protected void doExecute() {
    actions.get(activeIndex).execute();
    if (actions.get(activeIndex).isComplete()) {
      activeIndex++;
    }
  }
}
