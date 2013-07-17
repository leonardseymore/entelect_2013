package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.movement.SeekPath;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.*;

import java.util.Stack;

public class ActionMoveTank extends Action {

  private GameState gameState;
  private Tank tank;
  private int x;
  private int y;
  private Directed.Direction direction;

  public ActionMoveTank(GameState gameState, Tank tank, Directed.Direction direction) {
    this.gameState = gameState;
    this.tank = tank;
    this.direction = direction;
    switch (direction) {
      case UP:
        this.x = tank.getX();
        this.y = tank.getY() - 1;
        break;
      case RIGHT:
        this.x = tank.getX() + 1;
        this.y = tank.getY();
        break;
      case DOWN:
        this.x = tank.getX();
        this.y = tank.getY() + 1;
        break;
      case LEFT:
        this.x = tank.getX() - 1;
        this.y = tank.getY();
        break;
    }
    this.expiryTime = 1;
  }

  @Override
  public void doExecute() {
    switch (direction) {
      case UP:
        tank.setNextAction(TankAction.UP);
        break;
      case RIGHT:
        tank.setNextAction(TankAction.RIGHT);
        break;
      case DOWN:
        tank.setNextAction(TankAction.DOWN);
        break;
      case LEFT:
        tank.setNextAction(TankAction.LEFT);
        break;
    }
  }

  @Override
  public boolean isComplete() {
    return tank.getX() == x && tank.getY() == y;
  }
}
