package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.RayCast;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.Bullet;
import za.co.entelect.competition.domain.Direction;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

import java.util.ArrayList;
import java.util.Collection;


public class InFireLine extends Task {
  public boolean run(GameState gameState, Tank tank) {
    Collection<Bullet> bullets = new ArrayList<>();
    for (Bullet bullet : gameState.getBullets().values()) {
      Direction direction = bullet.getDirection();
      int x = bullet.getX();
      int y = bullet.getY();

      if (RayCast.castRay(gameState, new RayCast.RayCaseTestTarget(tank), direction, x, y, Constants.FIRE_RANGE)) {
        bullets.add(bullet);
      }
    }

    Blackboard blackboard = tank.getBlackboard();
    blackboard.setThreatBullets(bullets);
    if (bullets.size() > 0) {
      return true;
    }
    return false;
  }

  @Override
  protected String getLabel() {
    return "InFireLine?";
  }
}
