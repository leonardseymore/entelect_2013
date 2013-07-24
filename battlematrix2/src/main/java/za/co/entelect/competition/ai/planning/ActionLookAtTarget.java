package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.domain.*;

public class ActionLookAtTarget extends Action {

  private GameState gameState;
  private Tank tank;
  private String targetId;

  public ActionLookAtTarget(GameState gameState, Tank tank) {
    this.gameState = gameState;
    this.tank = tank;
    this.targetId = tank.getBlackboard().getTargetId();
    if (targetId == null) {
      return;
    }
    preconditions.add(new GameModelProp(tank.getId(), GameModelPropKey.InLine, targetId));
    effects.add(new GameModelProp(tank.getId(), GameModelPropKey.LookAt, targetId));
  }

  @Override
  public int getCost() {
    return 1;
  }

  @Override
  protected void doExecute(GameState gameState) {
    Entity target = gameState.getEntity(targetId);
    if (target == null) {
      return;
    }
    if (target.getY() < tank.getY() && tank.getDirection() != Direction.UP) {
      tank.setNextAction(TankAction.UP);
    } else if (target.getX() > tank.getX() && tank.getDirection() != Direction.RIGHT) {
      tank.setNextAction(TankAction.RIGHT);
    } else if (target.getY() > tank.getY() && tank.getDirection() != Direction.DOWN) {
      tank.setNextAction(TankAction.DOWN);
    } else if (target.getX() < tank.getX() && tank.getDirection() != Direction.LEFT) {
      tank.setNextAction(TankAction.LEFT);
    }
  }

  @Override
  public boolean isValid(GameState gameState) {
    return targetId == tank.getBlackboard().getTargetId() && gameState.getEntity(targetId) != null;
  }

  @Override
  public String getName() {
    return "ActionLookAtTarget";
  }
}
