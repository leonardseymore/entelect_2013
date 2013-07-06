package za.co.entelect.competition.bots.movement;

import za.co.entelect.competition.domain.Tank;

public class MoveToPoint {

  public static Tank.TankAction getAction(Tank tank, int []targetPos) {
    if (targetPos[0] > tank.getX()) {
      return Tank.TankAction.RIGHT;
    }

    if (targetPos[0] < tank.getX()) {
      return Tank.TankAction.LEFT;
    }

    if (targetPos[1] > tank.getY()) {
      return Tank.TankAction.DOWN;
    }

    if (targetPos[1] < tank.getY()) {
      return Tank.TankAction.UP;
    }

    return Tank.TankAction.NONE;
  }
}
