package za.co.entelect.competition.ai.planning.htn;

import za.co.entelect.competition.ai.planning.goap.GameModel;
import za.co.entelect.competition.ai.planning.goap.GameModelProp;

import java.util.ArrayList;
import java.util.List;

public class Htn {

  public static List<Planner> plan(GameModel requiredState, Planner planner) {
    List<Planner> plan = new ArrayList<>();
    plan(requiredState, planner, plan);
    return plan;
  }

  public static void plan(GameModel requiredState, Planner planner, List<Planner> plan) {
    if (requiredState.isSatisfiedBy(planner.state)) {
      plan.add(planner);
      return;
    }

    Planner child = planner.nextChild();
    while (child != null) {
      for (GameModelProp gameModelProp : child.getEffects()) {
        child.getState().addProp(gameModelProp);
        plan(requiredState, child, plan);
      }
      child = planner.nextChild();
    }
  }
}
