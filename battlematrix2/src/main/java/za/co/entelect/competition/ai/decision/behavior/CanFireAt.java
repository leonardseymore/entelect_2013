package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.RayCast;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.*;

public class CanFireAt extends Task {

  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    final Entity target = blackboard.getTarget();
    Direction direction = tank.getDirection();

    int x = tank.turretPos()[0];
    int y = tank.turretPos()[1];
    return RayCast.castRay(gameState, new RayCast.RayCaseTestTarget(target), direction, x, y);
  }

  @Override
  protected String getLabel() {
    return "CanFireAt?";
  }
}
