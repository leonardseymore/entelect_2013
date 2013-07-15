package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.movement.SeekPath;
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

  @Override
  public void execute() {
    Stack<PathFinder.Node> path = pathFinder.closestPathAStar(tank.getX(), tank.getY(), x, y, true);
    if (path == null || path.isEmpty()) {
      return;
    }

    PathFinder.Node target = path.peek();
    while ((tank.getX() == target.getX() && tank.getY() == target.getY()) && !path.isEmpty()) {
      target = path.pop();
    }
    Seek seek = new Seek(gameState, tank, target);
  }

  @Override
  public boolean isComplete() {
    return tank.getX() == x && tank.getY() == y;
  }
}
