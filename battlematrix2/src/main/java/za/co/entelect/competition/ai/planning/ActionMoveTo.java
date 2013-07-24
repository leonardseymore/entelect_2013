package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.pathfinding.PathFinderGoal;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Point;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankAction;

import java.util.Collection;
import java.util.Stack;

public class ActionMoveTo extends Action {

  private Stack<PathFinder.Node> path;
  private Tank tank;
  private Point target;

  public ActionMoveTo(GameState gameState, Tank tank) {
    this.tank = tank;
    this.target = tank.getBlackboard().getTargetLocation();
    if (target == null) {
      return;
    }
    this.path = PathFinder.closestPathAStar(gameState, tank, target.getX(), target.getY(), false);
    if (path == null) {
      return;
    }
    effects.add(new GameModelProp(tank.getId(), GameModelPropKey.IsAtX, target.getX()));
    effects.add(new GameModelProp(tank.getId(), GameModelPropKey.IsAtY, target.getY()));
  }

  @Override
  public int getCost() {
    if (path == null) {
      return Integer.MAX_VALUE;
    }
    return path.size() * 2;
  }

  @Override
  protected void doExecute(GameState gameState) {
    Point target = tank.getBlackboard().getTargetLocation();
    if (target == null) {
      return;
    }
    TankAction tankAction = Seek.seekPath(gameState, tank, path);
    tank.setNextAction(tankAction);
  }

  @Override
  public boolean isComplete(GameState gameState) {
    return tank.getX() == target.getX() && tank.getY() == target.getY();
  }

  @Override
  public boolean isValid(GameState gameState) {
    if (target == null) {
      return false;
    }
    if (!target.equals(tank.getBlackboard().getTargetLocation())) {
      return false;
    }
    return true;
  }

  @Override
  public String getName() {
    return "ActionMoveTo";
  }
}
