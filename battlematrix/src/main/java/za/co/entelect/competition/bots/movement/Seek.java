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
    GameState gameState = tank.getGameState();
    if (target.getX() > tank.getX()
      && gameState.isInbounds(tank.getX() + tank.getW() - 1, tank.getY())) {
      int obstruction = gameState.getTacticalMap()[tank.getX() + tank.getW()][tank.getY()].getObstruction();
      if (obstruction != Obstruction.BORDER && obstruction != Obstruction.WALL) {
        return Tank.TankAction.RIGHT;
      }
    }

    if (target.getX() < tank.getX()
      && gameState.isInbounds(tank.getX() - 1, tank.getY())) {
      int obstruction = gameState.getTacticalMap()[tank.getX() - 1][tank.getY()].getObstruction();
      if (obstruction != Obstruction.BORDER && obstruction != Obstruction.WALL) {
        return Tank.TankAction.LEFT;
      }
    }

    if (target.getY() > tank.getY()
      && gameState.isInbounds(tank.getX(), tank.getY() + tank.getH())) {
      int obstruction = gameState.getTacticalMap()[tank.getX()][tank.getY() + tank.getH()].getObstruction();
      if (obstruction != Obstruction.BORDER && obstruction != Obstruction.WALL) {
        return Tank.TankAction.DOWN;
      }
    }

    if (target.getY() < tank.getY()
      && gameState.isInbounds(tank.getX(), tank.getY() - 1)) {
      int obstruction = gameState.getTacticalMap()[tank.getX()][tank.getY() - 1].getObstruction();
      if (obstruction != Obstruction.BORDER && obstruction != Obstruction.WALL) {
        return Tank.TankAction.UP;
      }
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
