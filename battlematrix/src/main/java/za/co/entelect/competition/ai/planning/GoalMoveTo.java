package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.domain.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class GoalMoveTo extends Goal {

  private Iterator<Action> actionIterator;
  private Tank tank;
  private int x;
  private int y;

  public GoalMoveTo(int priority, Tank tank, int x, int y) {
    super(priority);
    this.tank = tank;
    this.x = x;
    this.y = y;
  }

  public boolean isFulfilled(GameState gameState) {
    Entity entity = gameState.getEntityAt(x, y);
    if (entity != null && entity.getType() == Entity.Type.TANK) {
      return ((Tank)entity).getTankId() == tank.getTankId();
    }
    return false;
  }

  public Action nextAction(GameState gameState) {
    if (actionIterator == null) {
      loadActionIterator(gameState);
    }
    if (actionIterator.hasNext()) {
      return actionIterator.next();
    } else {
      loadActionIterator(gameState);
    }
    return null;
  }

  @Override
  public void loadAvailableActions(GameState gameState) {
    loadActionIterator(gameState);
  }

  public void loadActionIterator(GameState gameState) {
    Tank clone = gameState.getTank(tank.getTankId());
    Collection<Action> actions = new ArrayList<>();
    //actions.add(new ActionFireTank(tank));
    actions.add(new ActionMoveTankTo(clone, x, y, true));
    actionIterator = actions.iterator();
  }
}
