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

  public ActionMoveTo(String tankId, int x, int y, Stack<PathFinder.Node> path) {
    this.tankId = tankId;
    this.path = path;
    effects.add(new GameModelProp(tankId, GameModelPropKey.IsAtX, x));
    effects.add(new GameModelProp(tankId, GameModelPropKey.IsAtY, y));
  }

  @Override
  public int getCost() {
    return path.size() * 2;
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

  @Override
  public String getName() {
    return "ActionMoveTo";
  }
}