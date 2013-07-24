package za.co.entelect.competition.ai.planning.goap;

import za.co.entelect.competition.Constants;

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
