package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.domain.GameState;

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
  protected void doExecute(GameState gameState) {
    actions.get(activeIndex).execute(gameState);
    if (actions.get(activeIndex).isComplete()) {
      activeIndex++;
    }
  }
}
