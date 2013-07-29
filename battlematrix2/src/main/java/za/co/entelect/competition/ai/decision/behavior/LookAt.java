package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.*;

public class LookAt extends Task {
  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    Entity target = blackboard.getTarget();
    int targetX = target.getX();
    int targetY = target.getY();
    int tankX = tank.getX();
    int tankY = tank.getY();
    Direction direction = tank.getDirection();
    if (targetY < tankY && direction != Direction.UP) {
      tank.setNextAction(TankAction.UP);
      return false;
    } else if (targetX > tankX && direction != Direction.RIGHT) {
      tank.setNextAction(TankAction.RIGHT);
      return false;
    } else if (targetY > tankY && direction != Direction.DOWN) {
      tank.setNextAction(TankAction.DOWN);
      return false;
    } else if (targetX < tankX && direction != Direction.LEFT) {
      tank.setNextAction(TankAction.LEFT);
      return false;
    }
    return true;
  }

  @Override
  protected String getLabel() {
    return "LookAt";
  }
}
