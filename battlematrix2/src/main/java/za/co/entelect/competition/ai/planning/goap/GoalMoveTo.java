package za.co.entelect.competition.ai.planning.goap;

import za.co.entelect.competition.Constants;

public class GoalMoveTo extends Goal {

  public GoalMoveTo(String tankId, int posX, int posY) {
    super(Constants.GOAL_MOVE_TO);
    requiredState.addProp(new GameModelProp(tankId, GameModelPropKey.IsAtX, posX));
    requiredState.addProp(new GameModelProp(tankId, GameModelPropKey.IsAtY, posY));
  }

  @Override
  public String getName() {
    return "GoalMoveTo";
  }
}
