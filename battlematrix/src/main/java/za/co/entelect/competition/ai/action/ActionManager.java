package za.co.entelect.competition.ai.action;

import java.util.*;

public class ActionManager {

  private static ActionManager instance;

  public static ActionManager getInstance() {
    if (instance == null) {
      instance = new ActionManager();
    }
    return instance;
  }

  private Queue<Action> pending = new PriorityQueue<>();
  private Queue<Action> active = new PriorityQueue<>();

  public void scheduleAction(Action action) {
    pending.add(action);
  }

  public void execute() {
    Action highestPriorityAction = active.poll();
    Queue<Action> newActiveQueue = new PriorityQueue<>();
    for (Action action : pending) {
      if (highestPriorityAction != null
        && action.getPriority() < highestPriorityAction.getPriority()) {
        break;
      }

      if (action.isCanInterrupt()) {
        newActiveQueue.add(action);
      }
    }
    active = newActiveQueue;

    Collection<Action> actionsToRemoveFromPending = new HashSet<>();
    Collection<Action> actionsToAddToActive = new HashSet<>();
    for (Action action : pending) {
      if (action.getExpiryTime() <= 0) {
        actionsToRemoveFromPending.add(action);
      }

      for (Action activeAction : active) {
        if (!action.canDoBoth(activeAction)) {
          continue;
        }

        actionsToRemoveFromPending.add(action);
        actionsToAddToActive.add(action);
      }
    }
    pending.removeAll(actionsToRemoveFromPending);
    active.addAll(actionsToAddToActive);

    Collection<Action> actionsToRemoveFromActive = new HashSet<>();
    for (Action action : active) {
      if (action.isComplete()) {
        actionsToRemoveFromActive.add(action);
      } else {
        action.execute();
      }
    }
  }
}
