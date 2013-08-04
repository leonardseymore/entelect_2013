package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.RayCast;
import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.*;

public class CanFireAt extends Task {
  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    final Entity target = blackboard.getTarget();
    Direction direction = tank.getDirection();

    Point turretPos = tank.getTurretPos();
    return RayCast.castRay(gameState, new RayCast.RayCaseTestTarget(target), direction, turretPos.getX(), turretPos.getY(), Constants.FIRE_RANGE);
  }

  @Override
  protected String getLabel() {
    return "CanFireAt?";
  }
}
