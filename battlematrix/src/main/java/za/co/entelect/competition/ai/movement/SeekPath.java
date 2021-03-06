package za.co.entelect.competition.ai.movement;

import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankAction;

import java.util.Stack;

public class SeekPath extends Seek {

  private Stack<PathFinder.Node> path;

  public SeekPath(GameState gameState, Tank tank, Stack<PathFinder.Node> path) {
    super(gameState, tank, null);
    this.path = path;
  }

  public SeekPath(GameState gameState, Tank tank) {
    super(gameState, tank, null);
  }

  public Stack<PathFinder.Node> getPath() {
    return path;
  }

  public void setPath(Stack<PathFinder.Node> path) {
    this.path = path;
  }

  public TankAction getAction() {
    if (path == null || path.isEmpty()) {
      return TankAction.NONE;
    }

    target = path.peek();
    while ((tank.getX() == target.getX() && tank.getY() == target.getY()) && !path.isEmpty()) {
      target = path.pop();
    }
    return super.getAction();
  }
}
