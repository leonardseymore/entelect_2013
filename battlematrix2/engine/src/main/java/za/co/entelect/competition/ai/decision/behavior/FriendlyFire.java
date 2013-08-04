package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.RayCast;
import za.co.entelect.competition.domain.*;

public class FriendlyFire extends Task {

  private static class RayCaseTestFriendlyFire implements RayCast.RayCastTest {

    private Tank tank;

    private RayCaseTestFriendlyFire(Tank tank) {
      this.tank = tank;
    }

    @Override
    public boolean test(GameElement gameElement, Entity entity) {
      if (entity == null) {
        return false;
      } else {
        if (entity.getGameElement() == GameElement.TANK) {
          if (((Tank)entity).getOwner() == tank.getOwner()) {
            return true;
          }
        }
      }
      return false;
    }
  }

  public boolean run(GameState gameState, Tank tank) {
    Direction direction = tank.getDirection();
    Point turretPos = tank.getTurretPos();
    return RayCast.castRay(gameState, new RayCaseTestFriendlyFire(tank), direction, turretPos.getX(), turretPos.getY(), Constants.FRIENDLY_FIRE_RANGE);
  }

  @Override
  protected String getLabel() {
    return "FriendlyFire?";
  }
}
