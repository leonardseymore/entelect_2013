package za.co.entelect.competition.bots.movement;

import za.co.entelect.competition.bots.pathfinding.PathFinder;
import za.co.entelect.competition.domain.Entity;
import za.co.entelect.competition.domain.Tank;

import java.util.Stack;

public class SeekPath extends Seek {

  private Stack<PathFinder.Node> path;

  public SeekPath(Tank tank, Stack<PathFinder.Node> path) {
    super(tank, null);
    this.path = path;
  }

  public SeekPath(Tank tank) {
    super(tank, null);
  }

  public Stack<PathFinder.Node> getPath() {
    return path;
  }

  public void setPath(Stack<PathFinder.Node> path) {
    this.path = path;
  }

  public Tank.TankAction getAction() {
    if (path == null || path.isEmpty()) {
      return Tank.TankAction.NONE;
    }

    target = path.peek();
    while ((tank.getX() == target.getX() && tank.getY() == target.getY()) && !path.isEmpty()) {
      target = path.pop();
    }
    return super.getAction();
  }
}
