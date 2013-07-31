package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.Bullet;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;


public class SetTargetFromBullet extends Task {
  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    Bullet bullet = (Bullet)blackboard.getTarget();
    blackboard.setTarget(bullet.getTank());
    return true;
  }

  @Override
  protected String getLabel() {
    return "SetTargetFromBullet";
  }
}
