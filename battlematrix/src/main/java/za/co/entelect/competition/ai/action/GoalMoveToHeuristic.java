package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankId;

public class GoalMoveToHeuristic implements GoapIdaHeuristic {

  private TankId tankId;
  private int x;
  private int y;

  public GoalMoveToHeuristic(TankId tankId, int x, int y) {
    this.tankId = tankId;
    this.x = x;
    this.y = y;
  }

  @Override
  public int estimate(GameState gameState) {
    Tank tank = gameState.getTank(tankId);
    if (tank != null) {
      return Util.manhattanDist(tank.getX(), tank.getY(), x, y);
    }
    return Integer.MAX_VALUE;
  }
}
