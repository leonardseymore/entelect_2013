package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Point;

import java.util.ArrayList;
import java.util.Collection;

public class ActionDestroyEnemyBaseRam extends Action {

  private String tankId;
  private Collection<GameModelProp> preconditions = new ArrayList<>();
  private Collection<GameModelProp> effects = new ArrayList<>();

  public ActionDestroyEnemyBaseRam(String tankId, String enemyBaseId, int enemyBasePosX, int enemyBasePosY) {
    this.tankId = tankId;
    preconditions.add(new GameModelProp(tankId, GameModelPropKey.IsAtX, enemyBasePosX));
    preconditions.add(new GameModelProp(tankId, GameModelPropKey.IsAtY, enemyBasePosY));
    effects.add(new GameModelProp(enemyBaseId, GameModelPropKey.Destroyed, true));
  }

  @Override
  public int getCost() {
    return 1;
  }

  @Override
  protected void doExecute(GameState gameState) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Collection<GameModelProp> getPreconditions() {
    return preconditions;
  }

  @Override
  public Collection<GameModelProp> getEffects() {
    return effects;
  }

  @Override
  public String getName() {
    return "ActionDestroyEnemyBaseRam";
  }
}