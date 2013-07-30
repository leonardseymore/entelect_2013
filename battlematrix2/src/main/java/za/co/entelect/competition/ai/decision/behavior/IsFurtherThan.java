package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.RayCast;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.*;

public class IsFurtherThan extends Task {

  private int dist;

  public IsFurtherThan(int dist) {
    this.dist = dist;
  }

  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    Entity target = blackboard.getTarget();
    return Util.manhattanDist(tank, target) > dist;
  }

  @Override
  protected String getLabel() {
    return "IsFurtherThan" + dist + "?";
  }
}
