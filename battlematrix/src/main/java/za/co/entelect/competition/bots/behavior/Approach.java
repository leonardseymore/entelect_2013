package za.co.entelect.competition.bots.behavior;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.bots.movement.PathFinder;
import za.co.entelect.competition.bots.movement.Seek;
import za.co.entelect.competition.domain.Entity;
import za.co.entelect.competition.domain.Tank;

import java.util.Stack;

public class Approach {

  private int pathAge;
  private int maxPathAge = 10;
  private Stack<PathFinder.Node> path;

  private Entity target;
  private Tank tank;
  private int targetDist;
  private PathFinder pathFinder;

  public Approach(Tank tank, Entity target, int targetDist) {
    pathFinder = new PathFinder(tank.getGameState());
    this.tank = tank;
    this.target = target;
    this.targetDist = targetDist;
  }

  public Tank.TankAction getAction() {
    int tankCenterX = tank.getX() + (tank.getW() / 2);
    int tankCenterY = tank.getY() + (tank.getH() / 2);
    int targetCenterX = target.getX() + (target.getW() / 2);
    int targetCenterY = target.getY() + (target.getH() / 2);
    int dist = Util.manhattanDist(tankCenterX, tankCenterY, targetCenterX, targetCenterY);
    if (dist > targetDist) {
      if (path == null || path.isEmpty() || pathAge > maxPathAge) {
        path = pathFinder.closestPathAStar(tank.getX(), tank.getY(), target.getX(), target.getY());
        pathAge = 0;
      }
      if (path == null) {
        return Tank.TankAction.NONE;
      } else {
        pathAge++;
        return Seek.seekPath(tank, path);
      }
    }
    return Tank.TankAction.NONE;
  }

  public Stack<PathFinder.Node> getPath() {
    return path;
  }
}
