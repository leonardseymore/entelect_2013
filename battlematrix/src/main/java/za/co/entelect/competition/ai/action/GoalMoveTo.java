package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

public class GoalMoveTo implements Goal {

  private Tank tank;

  public GoalMoveTo(Tank tank) {
    this.tank = tank;
  }

  public boolean isFulfilled(GameState gameState) {
    return gameState.getEntityAt(tank.getX(), tank.getY()) == tank;
  }
}
