package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.movement.SeekPath;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

import java.util.Stack;

public class ActionMoveTankTo extends Action {

  private int x;
  private int y;
  private Tank tank;
  private PathFinder pathFinder;
  private GameState gameState;
  private Stack<PathFinder.Node> path;
  private Seek seek;

  public ActionMoveTankTo(GameState gameState, Tank tank, int x, int y) {
    this.gameState = gameState;
    this.tank = tank;
    this.x = x;
    this.y = y;
    pathFinder = new PathFinder(gameState, tank);
    path = pathFinder.closestPathAStar(tank.getX(), tank.getY(), x, y, true);
    if (path != null) {
      expiryTime = path.size() * 2;
    }
    seek = new SeekPath(gameState, tank, path);
  }

  public Stack<PathFinder.Node> getPath() {
    return path;
  }

  @Override
  public void doExecute() {
    tank.setNextAction(seek.getAction());
  }

  @Override
  public boolean isComplete() {
    if (path == null || path.isEmpty() || expiryTime <= 0) {
      return true;
    }

    return tank.getX() == x && tank.getY() == y;
  }
}
