package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.Constants;

import java.util.HashSet;
import java.util.Set;

public class GoalFire extends Goal {

  private GameModel requiredState;

  public GoalFire(String tankId) {
    super(Constants.GOAL_FIRE);
    Set<GameModelProp> requiredStates = new HashSet<>();
    requiredStates.add(new GameModelProp(tankId, GameModelPropKey.Fired, true));
    requiredState = new GameModel(requiredStates);
  }

  @Override
  public GameModel requiredState() {
    return requiredState;
  }

  @Override
  public String getName() {
    return "GoalFire";
  }
}
