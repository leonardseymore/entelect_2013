package za.co.entelect.competition.bots.movement;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Obstruction;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.Trackable;

public class Seek {

  protected Tank tank;
  protected Trackable target;

  public Seek(Tank tank, Trackable target) {
    this.tank = tank;
    this.target = target;
  }

  public Trackable getTarget() {
    return target;
  }

  public void setTarget(Trackable target) {
    this.target = target;
  }

  private Tank.TankAction seek() {
    if (target.getX() > tank.getX() && tank.canMoveRight()) {
      return Tank.TankAction.RIGHT;
    }

    if (target.getX() < tank.getX() && tank.canMoveLeft()) {
      return Tank.TankAction.LEFT;
    }

    if (target.getY() > tank.getY() && tank.canMoveDown()) {
      return Tank.TankAction.DOWN;
    }

    if (target.getY() < tank.getY() && tank.canMoveUp()) {
      return Tank.TankAction.UP;
    }

    return Tank.TankAction.NONE;
  }

  public Tank.TankAction getAction() {
    if (tank.getX() == target.getX() && tank.getY() == target.getY()) {
      return Tank.TankAction.NONE;
    }
    return seek();
  }
}
