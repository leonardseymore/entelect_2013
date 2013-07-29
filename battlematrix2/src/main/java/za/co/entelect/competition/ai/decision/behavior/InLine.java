package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.Entity;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

public class InLine extends Task {
  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    Entity target = blackboard.getTarget();
    if (target.getX() == tank.getX() || target.getY() == tank.getY()) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  protected String getLabel() {
    return "InLine?";
  }
}
