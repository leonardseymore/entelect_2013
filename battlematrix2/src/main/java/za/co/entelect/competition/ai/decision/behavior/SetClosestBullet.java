package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.*;

import java.util.Collection;


public class SetClosestBullet extends Task {
  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    Collection<Bullet> bullets = blackboard.getThreatBullets();
    Bullet closestBullet = null;
    int closestDist = Integer.MAX_VALUE;
    for (Bullet bullet : bullets) {
      int dist = Util.manhattanDist(tank, bullet);
      if (closestBullet == null || dist < closestDist) {
        closestBullet = bullet;
        closestDist = dist;
      }
    }
    blackboard.setTarget(closestBullet);
    return true;
  }

  @Override
  protected String getLabel() {
    return "SetClosestBullet";
  }
}
