package za.co.entelect.competition.ai.movement;

import za.co.entelect.competition.domain.*;

public class Seek {

  protected Tank tank;
  protected Trackable target;
  protected GameModel gameModel;

  public Seek(GameModel gameModel, Tank tank, Trackable target) {
    this.tank = tank;
    this.target = target;
    this.gameModel = gameModel;
  }

  public Trackable getTarget() {
    return target;
  }

  public void setTarget(Trackable target) {
    this.target = target;
  }

  private TankAction seek() {
    if (target.getX() > tank.getX()
      && gameModel.canTankBeMovedTo(tank, tank.getX() + 1, tank.getY())) {
      return TankAction.RIGHT;
    }

    if (target.getX() < tank.getX()
      && gameModel.canTankBeMovedTo(tank, tank.getX() - 1, tank.getY())) {
      return TankAction.LEFT;
    }

    if (target.getY() > tank.getY()
      && gameModel.canTankBeMovedTo(tank, tank.getX(), tank.getY() + 1)) {
      return TankAction.DOWN;
    }

    if (target.getY() < tank.getY()
      && gameModel.canTankBeMovedTo(tank, tank.getX(), tank.getY() - 1)) {
      return TankAction.UP;
    }

    return TankAction.NONE;
  }

  public TankAction getAction() {
    if (tank.getX() == target.getX() && tank.getY() == target.getY()) {
      return TankAction.NONE;
    }
    return seek();
  }
}
