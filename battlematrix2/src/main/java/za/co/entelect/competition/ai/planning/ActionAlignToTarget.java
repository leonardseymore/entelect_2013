package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.domain.*;

public class ActionAlignToTarget extends Action {

  private GameState gameState;
  private Tank tank;
  private String targetId;

  public ActionAlignToTarget(GameState gameState, Tank tank) {
    this.gameState = gameState;
    this.tank = tank;
    this.targetId = tank.getBlackboard().getTargetId();
    if (targetId == null) {
      return;
    }
    effects.add(new GameModelProp(tank.getId(), GameModelPropKey.AlignedTo, targetId));
  }

  @Override
  public int getCost() {
    Entity target = gameState.getEntity(targetId);
    if (target == null) {
      return Integer.MAX_VALUE;
    }
    return Math.max(Math.abs(target.getX() - tank.getX()), Math.abs(target.getX() - tank.getX()));
  }

  @Override
  protected void doExecute(GameState gameState) {
    Entity target = gameState.getEntity(targetId);
    if (target == null) {
      return;
    }

    int xDiff = Math.abs(target.getX() - tank.getX());
    int yDiff = Math.abs(target.getY() - tank.getY());

    boolean moveInX = true;
    if (xDiff == yDiff && xDiff == 0) {
      return;
    } else if (xDiff > yDiff) {
      moveInX = false;
    }

    if (moveInX) {
      if (target.getX() < tank.getX() && gameState.canMoveInDirection(tank, Direction.LEFT)) {
        tank.setNextAction(TankAction.LEFT);
      } else if (target.getX() > tank.getX() && gameState.canMoveInDirection(tank, Direction.RIGHT)) {
        tank.setNextAction(TankAction.RIGHT);
      }
    } else {
      if (target.getY() < tank.getY() && gameState.canMoveInDirection(tank, Direction.UP)) {
        tank.setNextAction(TankAction.UP);
      } else if (target.getY() > tank.getY() && gameState.canMoveInDirection(tank, Direction.DOWN)) {
        tank.setNextAction(TankAction.DOWN);
      }
    }
  }

  @Override
  public boolean isComplete(GameState gameState) {
    Entity target = gameState.getEntity(targetId);
    return tank.getX() == target.getX() || tank.getY() == target.getY();
  }

  @Override
  public boolean isValid(GameState gameState) {
    return targetId == tank.getBlackboard().getTargetId() && gameState.getEntity(targetId) != null;
  }

  @Override
  public String getName() {
    return "ActionAlignToTarget";
  }
}
