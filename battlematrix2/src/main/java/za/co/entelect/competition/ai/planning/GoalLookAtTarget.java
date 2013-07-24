package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.Constants;

import java.util.HashSet;
import java.util.Set;

public class GoalLookAtTarget extends Goal {

  public GoalLookAtTarget(String tankId, String targetId) {
    super(Constants.GOAL_LOOKAT_TARGET);
    requiredState.addProp(new GameModelProp(tankId, GameModelPropKey.LookAt, targetId));
  }

  @Override
  public String getName() {
    return "GoalLookAtTarget";
  }
}
