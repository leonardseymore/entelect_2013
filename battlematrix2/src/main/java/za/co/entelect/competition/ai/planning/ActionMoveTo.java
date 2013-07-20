package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Point;
import za.co.entelect.competition.domain.Tank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class ActionMoveTo extends Action {

  private String tankId;
  private Point pos;
  private Collection<GameModelProp> preconditions = new ArrayList<>();
  private Collection<GameModelProp> effects = new ArrayList<>();
  private Stack<PathFinder.Node> path;

  public ActionMoveTo(String tankId, Point pos) {
    this.tankId = tankId;
    this.pos = pos;
    effects.add(new GameModelProp(tankId, GameModelPropKey.IsAt, pos));
  }

  @Override
  public int getCost(GameState gameState) {
    Tank tank = (Tank) gameState.getEntity(tankId);
    if (tank == null) {
      return Integer.MAX_VALUE;
    }
    PathFinder pathFinder = new PathFinder(gameState, tank);
    path = pathFinder.closestPathAStar(tank.getX(), tank.getY(), pos.getX(), pos.getY(), false);
    if (path == null) {
      return Integer.MAX_VALUE;
    }
    return path.size();
  }

  @Override
  protected void doExecute(GameState gameState) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Collection<GameModelProp> getPreconditions() {
    return preconditions;
  }

  @Override
  public Collection<GameModelProp> getEffects() {
    return effects;
  }
}
