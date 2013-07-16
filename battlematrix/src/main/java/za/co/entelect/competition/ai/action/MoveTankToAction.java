package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankAction;

import java.util.Stack;

public class MoveTankToAction extends Action {

  private int x;
  private int y;
  private Tank tank;
  private PathFinder pathFinder;
  private GameState gameState;
  private TankAction nextTankAction = TankAction.NONE;
  private Stack<PathFinder.Node> path;

  public MoveTankToAction(GameState gameState, Tank tank, int x, int y) {
    this.gameState = gameState;
    this.tank = tank;
    this.x = x;
    this.y = y;
    pathFinder = new PathFinder(gameState, tank);
    path = pathFinder.closestPathAStar(tank.getX(), tank.getY(), x, y, true);
    if (path != null) {
      expiryTime = path.size() * 2;
    }
  }

  public Stack<PathFinder.Node> getPath() {
    return path;
  }

  @Override
  public void doExecute() {
    if (path == null || path.isEmpty()) {
      return;
    }

    PathFinder.Node target = path.peek();
    while ((tank.getX() == target.getX() && tank.getY() == target.getY()) && !path.isEmpty()) {
      target = path.pop();
    }
    Seek seek = new Seek(gameState, tank, target);
    nextTankAction = seek.getAction();
  }

  public TankAction getTankAction() {
    TankAction ret = nextTankAction;
    nextTankAction = TankAction.NONE;
    return ret;
  }

  @Override
  public boolean isComplete() {
    if (path == null || path.isEmpty()) {
      return true;
    }

    return tank.getX() == x && tank.getY() == y;
  }
}
