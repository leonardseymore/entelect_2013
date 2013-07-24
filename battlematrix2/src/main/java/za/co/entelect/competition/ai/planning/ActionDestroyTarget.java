package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.*;

import java.util.Collection;
import java.util.Stack;

public class ActionDestroyTarget extends Action {

  private GameState gameState;
  private Tank tank;
  private String targetId;
  private boolean fired = false;

  public ActionDestroyTarget(GameState gameState, Tank tank) {
    this.gameState = gameState;
    this.tank = tank;
    this.targetId = tank.getBlackboard().getTargetId();
    if (targetId == null) {
      return;
    }
    preconditions.add(new GameModelProp(tank.getId(), GameModelPropKey.CanFire, true));
    preconditions.add(new GameModelProp(tank.getId(), GameModelPropKey.LookAt, targetId));
    effects.add(new GameModelProp(tank.getId(), GameModelPropKey.Destroyed, true));
  }

  @Override
  public int getCost() {
    Entity target = gameState.getEntity(targetId);
    return Util.manhattanDist(tank.getX(), tank.getY(), target.getX(), target.getY());
  }

  @Override
  protected void doExecute(GameState gameState) {
    tank.setNextAction(TankAction.FIRE);
    fired = true;
  }

  @Override
  public boolean isComplete(GameState gameState) {
    return fired;
  }

  @Override
  public boolean isValid(GameState gameState) {
    return targetId == tank.getBlackboard().getTargetId();
  }

  @Override
  public String getName() {
    return "ActionDestroyTarget";
  }
}
