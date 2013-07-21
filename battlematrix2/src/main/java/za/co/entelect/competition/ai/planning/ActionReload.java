package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.GameState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class ActionReload extends Action {

  private String tankId;
  private Collection<GameModelProp> preconditions = new ArrayList<>();
  private Collection<GameModelProp> effects = new ArrayList<>();

  public ActionReload(String tankId) {
    this.tankId = tankId;
    effects.add(new GameModelProp(tankId, GameModelPropKey.CanFire, true));
  }

  @Override
  public int getCost() {
    return 1;
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
    return "ActionReload";
  }
}
