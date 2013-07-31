package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.*;


public class DodgeBullet extends Task {
  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    Bullet closestBullet = (Bullet)blackboard.getTarget();

    int tankX = tank.getX();
    int tankY = tank.getY();
    int bulletX = closestBullet.getX();
    int bulletY = closestBullet.getY();
    Direction direction = closestBullet.getDirection();
    if (direction == Direction.UP || direction == Direction.DOWN) {
      if (bulletX < tankX) {
        if (bulletX == tankX + 2) {
          if (gameState.canMoveInDirection(tank, Direction.RIGHT)
            && gameState.canTankBeMovedTo(tank, tankX + 2, tankY)) {
            blackboard.setNextTankAction(TankAction.RIGHT);
            return true;
          }
        } else if (bulletX == tankX + 1) {
          if (gameState.canMoveInDirection(tank, Direction.RIGHT)) {
            blackboard.setNextTankAction(TankAction.RIGHT);
            return true;
          }
        }
      } else {
        if (bulletX == tankX - 2) {
          if (gameState.canMoveInDirection(tank, Direction.LEFT)
            && gameState.canTankBeMovedTo(tank, tankX - 2, tankY)) {
            blackboard.setNextTankAction(TankAction.LEFT);
            return true;
          }
        } else if (bulletX == tankX - 1) {
          if (gameState.canMoveInDirection(tank, Direction.LEFT)) {
            blackboard.setNextTankAction(TankAction.LEFT);
            return true;
          }
        }
      }
    } else {
      if (bulletY < tankY) {
        if (bulletY == tankY + 2) {
          if (gameState.canMoveInDirection(tank, Direction.DOWN)
            && gameState.canTankBeMovedTo(tank, tankX, tankY + 2)) {
            blackboard.setNextTankAction(TankAction.DOWN);
            return true;
          }
        } else if (bulletY == tankY + 1) {
          if (gameState.canMoveInDirection(tank, Direction.DOWN)) {
            blackboard.setNextTankAction(TankAction.DOWN);
            return true;
          }
        }
      } else {
        if (bulletY == tankY - 2) {
          if (gameState.canMoveInDirection(tank, Direction.UP)
            && gameState.canTankBeMovedTo(tank, tankX, tankY - 2)) {
            blackboard.setNextTankAction(TankAction.UP);
            return true;
          }
        } else if (bulletY == tankY - 1) {
          if (gameState.canMoveInDirection(tank, Direction.UP)) {
            blackboard.setNextTankAction(TankAction.UP);
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  protected String getLabel() {
    return "DodgeBullet";
  }
}
