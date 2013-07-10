package za.co.entelect.competition.bots.movement;

import za.co.entelect.competition.domain.Tank;

import java.util.Random;
import java.util.Stack;

public class Seek {

  private static Random random = new Random();

  public static Tank.TankAction seekDumb(Tank tank, int x, int y) {
    Tank.TankAction lastAction = tank.getLastAction();
    boolean lastMovedVertically = tank.hasLastActionMoved() && (lastAction == Tank.TankAction.UP || lastAction == Tank.TankAction.DOWN);
    boolean lastMovedHorizontally = tank.hasLastActionMoved() && (lastAction == Tank.TankAction.RIGHT || lastAction == Tank.TankAction.LEFT);

    if (lastMovedHorizontally) {
      Tank.TankAction action = moveHorizontally(tank, x);
      if (action == Tank.TankAction.NONE) {
        return moveVertically(tank, y);
      } else {
        return action;
      }
    } else if (lastMovedVertically) {
      Tank.TankAction action = moveVertically(tank, y);
      if (action == Tank.TankAction.NONE) {
        return moveHorizontally(tank, x);
      } else {
        return action;
      }
    } else {
      if (random.nextBoolean()) {
        return moveHorizontally(tank, x);
      } else {
        return moveVertically(tank, y);
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
