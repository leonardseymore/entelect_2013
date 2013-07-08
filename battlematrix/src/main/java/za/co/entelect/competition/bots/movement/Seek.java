package za.co.entelect.competition.bots.movement;

import za.co.entelect.competition.domain.Tank;

import java.util.Stack;

public class Seek {

  public static Tank.TankAction seekDumb(Tank tank, int x, int y) {
    if (tank.getLastAction() == Tank.TankAction.UP || tank.getLastAction() == Tank.TankAction.DOWN) {
      Tank.TankAction action = moveHorizontally(tank, x);
      if (action == Tank.TankAction.NONE) {
        return moveVertically(tank, y);
      } else {
        return action;
      }
    } else {
      Tank.TankAction action = moveVertically(tank, y);
      if (action == Tank.TankAction.NONE) {
        return moveHorizontally(tank, x);
      } else {
        return action;
      }
    }
  }

  private static Tank.TankAction moveHorizontally(Tank tank, int x) {
    if (x > tank.getX()) {
      return Tank.TankAction.RIGHT;
    }

    if (x < tank.getX()) {
      return Tank.TankAction.LEFT;
    }

    return Tank.TankAction.NONE;
  }

  private static Tank.TankAction moveVertically(Tank tank, int y) {
    if (y > tank.getY()) {
      return Tank.TankAction.DOWN;
    }

    if (y < tank.getY()) {
      return Tank.TankAction.UP;
    }

    return Tank.TankAction.NONE;
  }

  public static Tank.TankAction seekPath(Tank tank, Stack<PathFinder.Node> path) {
    if (path == null || path.isEmpty()) {
      return Tank.TankAction.NONE;
    }

    PathFinder.Node target = path.peek();
    if (tank.getX() == target.getX() && tank.getY() == target.getY()) {
      target = path.pop();
    }
    return seekDumb(tank, target.x, target.y);
  }
}
