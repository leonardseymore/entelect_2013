package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.movement.SeekPath;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Point;
import za.co.entelect.competition.domain.Tank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class ActionMoveTo extends Action {

  private String tankId;
  private int targetX;
  private int targetY;
  private Collection<GameModelProp> preconditions = new ArrayList<>();
  private Collection<GameModelProp> effects = new ArrayList<>();
  private Stack<PathFinder.Node> path;
  private Seek seek;

  public ActionMoveTo(String tankId, int x, int y, Stack<PathFinder.Node> path) {
    this.tankId = tankId;
    this.targetX = x;
    this.targetY = y;
    this.path = path;
    effects.add(new GameModelProp(tankId, GameModelPropKey.IsAtX, x));
    effects.add(new GameModelProp(tankId, GameModelPropKey.IsAtY, y));
    this.expiryTime = path.size() * 2;
  }

  @Override
  public int getCost() {
    return path.size() * 2;
  }

  @Override
  protected void doExecute(GameState gameState) {
    Tank tank = (Tank) gameState.getEntity(tankId);
    if (seek == null) {
      seek = new SeekPath(gameState, tank, path);
    }
    tank.setNextAction(seek.getAction());
  }

  @Override
  public boolean isComplete(GameState gameState) {
    Tank tank = (Tank) gameState.getEntity(tankId);
    return tank.getX() == targetX && tank.getY() == targetY;
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
