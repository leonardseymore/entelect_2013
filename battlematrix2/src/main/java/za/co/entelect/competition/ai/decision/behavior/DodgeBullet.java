package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.*;


public class DodgeBullet extends Task {
  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    Bullet closestBullet = (Bullet)blackboard.getTarget();

    int tankX = tank.getX();
    int tankY = tank.getY();
    int bulletX = closestBullet.getX();
    int bulletY = closestBullet.getY();
    Direction direction = closestBullet.getDirection();
    if (direction == Direction.UP || direction == Direction.DOWN) {
      if (tankX <= bulletX) {
        blackboard.setMoveTimes(3 - (bulletX - tankX));
        blackboard.setMoveTimesDirection(Direction.LEFT);
      } else {
        blackboard.setMoveTimes(3 - (tankX - bulletX));
        blackboard.setMoveTimesDirection(Direction.RIGHT);
      }
    } else {
      if (tankY <= bulletY) {
        blackboard.setMoveTimes(3 - (bulletY - tankY));
        blackboard.setMoveTimesDirection(Direction.UP);
      } else {
        blackboard.setMoveTimes(3 - (tankY - bulletY));
        blackboard.setMoveTimesDirection(Direction.DOWN);
      }
    }
    return true;
  }

  @Override
  protected String getLabel() {
    return "DodgeBullet";
  }
}
