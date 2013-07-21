package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.domain.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GoalMoveToX extends Goal {

  private GameModel requiredState;

  public GoalMoveToX(String tankId, int x) {
    super(Constants.GOAL_MOVE_TO);
    Set<GameModelProp> requiredStates = new HashSet<>();
    requiredStates.add(new GameModelProp(tankId, GameModelPropKey.IsAtX, x));
    requiredState = new GameModel(requiredStates);
  }

  @Override
  public GameModel requiredState() {
    return requiredState;
  }

  @Override
  public String getName() {
    return "GoalMoveToX";
  }
}
