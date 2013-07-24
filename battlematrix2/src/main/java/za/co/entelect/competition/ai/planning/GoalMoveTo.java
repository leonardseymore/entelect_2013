package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.domain.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
