package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.domain.GameState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GoalDestroyEnemyBase extends Goal {

  private GameModel requiredState;

  public GoalDestroyEnemyBase(String enemyBaseId) {
    super(Constants.GOAL_DESTROY_ENEMY_BASE);
    Set<GameModelProp> requiredStates = new HashSet<>();
    requiredStates.add(new GameModelProp(enemyBaseId, GameModelPropKey.Destroyed, true));
    requiredState = new GameModel(requiredStates);
  }

  @Override
  public GameModel requiredState() {
    return requiredState;
  }

  @Override
  public String getName() {
    return "GoalDestroyEnemyBase";
  }
}