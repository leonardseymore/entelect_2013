package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.Constants;

import java.util.ArrayList;
import java.util.Collection;

public class GoalDestroyEnemyTank extends Goal {

  private GameModel requiredState;

  public GoalDestroyEnemyTank(String enemyTankId) {
    super(Constants.GOAL_DESTROY_ENEMY_BASE);
    Collection<GameModelProp> requiredStates = new ArrayList<>();
    requiredStates.add(new GameModelProp(enemyTankId, GameModelPropKey.Destroyed, true));
    requiredState = new GameModel(requiredStates);
  }

  @Override
  public GameModel requiredState() {
    return requiredState;
  }
}
