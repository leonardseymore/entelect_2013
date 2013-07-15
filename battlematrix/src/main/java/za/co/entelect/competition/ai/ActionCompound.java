package za.co.entelect.competition.ai;

import java.util.ArrayList;
import java.util.List;

public class ActionCompound extends Action {

  List<Action> actions = new ArrayList<>();

  @Override
  public boolean isCanInterrupt() {
    for (Action action : actions) {
      if (action.isCanInterrupt()) {
        return true;
      }
    }
    return false;
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
    for (Action action : actions) {
      if (!action.isComplete()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void execute() {
    for (Action action : actions) {
      action.execute();
    }
  }
}
