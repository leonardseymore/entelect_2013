package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.domain.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GoalMoveTo extends Goal {

  private GameModel requiredState;

  public GoalMoveTo(String tankId, int posX, int posY) {
    super(Constants.GOAL_MOVE_TO);
    Set<GameModelProp> requiredStates = new HashSet<>();
    requiredStates.add(new GameModelProp(tankId, GameModelPropKey.IsAtX, posX));
    requiredStates.add(new GameModelProp(tankId, GameModelPropKey.IsAtY, posY));
    requiredState = new GameModel(requiredStates);
  }

  @Override
  public GameModel requiredState() {
    return requiredState;
  }

  @Override
  public String getName() {
    return "GoalMoveTo";
  }
}
