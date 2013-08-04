package za.co.entelect.competition.ai.movement;

import za.co.entelect.competition.domain.*;

import java.util.Stack;

public class Seek {

  public static TankAction seekPath(GameState gameState, Tank tank, Stack<Trackable> path) {
    if (path == null || path.isEmpty()) {
      return TankAction.NONE;
    }

    Trackable target = path.peek();
    while ((tank.getX() == target.getX() && tank.getY() == target.getY()) && !path.isEmpty()) {
      target = path.pop();
    }
    return seek(gameState, tank, target);
  }

  public static TankAction seek(GameState gameState, Tank tank, Trackable target) {
    if (tank.getX() == target.getX() && tank.getY() == target.getY()) {
      return TankAction.NONE;
    }

    if (target.getX() > tank.getX()
      && gameState.canTankBeMovedTo(tank, tank.getX() + 1, tank.getY())) {
      return TankAction.RIGHT;
    }

    if (target.getX() < tank.getX()
      && gameState.canTankBeMovedTo(tank, tank.getX() - 1, tank.getY())) {
      return TankAction.LEFT;
    }

    if (target.getY() > tank.getY()
      && gameState.canTankBeMovedTo(tank, tank.getX(), tank.getY() + 1)) {
      return TankAction.DOWN;
    }

    if (target.getY() < tank.getY()
      && gameState.canTankBeMovedTo(tank, tank.getX(), tank.getY() - 1)) {
      return TankAction.UP;
    }

    return TankAction.NONE;
  }
}
