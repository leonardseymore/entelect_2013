package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.domain.Entity;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

public class GoalMoveTo implements Goal {

  private Tank tank;
  private int x;
  private int y;

  public GoalMoveTo(Tank tank, int x, int y) {
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
}
