package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.RayCast;
import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.*;

import java.util.ArrayList;
import java.util.Collection;

public class IsSafeMove extends Task {
  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    TankAction tankAction = blackboard.getNextTankAction();
    Rectangle rect = tank.getRect();
    switch (tankAction) {
      case UP:
        rect.translate(0, -1);
        break;
      case RIGHT:
        rect.translate(1, 0);
        break;
      case DOWN:
        rect.translate(0, 1);
        break;
      case LEFT:
        rect.translate(-1, 0);
        break;
    }

    for (Bullet bullet : gameState.getBullets().values()) {
      Direction direction = bullet.getDirection();
      int x = bullet.getX();
      int y = bullet.getY();

      if (RayCast.castRay(gameState, rect, direction, x, y, Constants.FIRE_RANGE)) {
        return false;
      }
    }

    Base base = tank.isYourTank() ? gameState.getYourBase() : gameState.getOpponentBase();
    if (rect.contains(base.getX(), base.getY())) {
      return false;
    }

    // TODO: don't move directly infront of an enemy tank, maybe include in pathfinding

    return true;
  }

  @Override
  protected String getLabel() {
    return "IsSafeMove?";
  }
}
