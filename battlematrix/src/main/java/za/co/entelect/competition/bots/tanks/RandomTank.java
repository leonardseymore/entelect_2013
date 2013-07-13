package za.co.entelect.competition.bots.tanks;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

import java.util.Random;

public class RandomTank extends Tank {

  private Random random = new Random(System.currentTimeMillis());

  public RandomTank(TankId id) {
    super(id);
  }

  @Override
  public TankAction doGetAction(GameState gameState) {
    switch (random.nextInt(100)) {
      case 1:
        return TankAction.UP;
      case 2:
        return TankAction.RIGHT;
      case 3:
        return TankAction.DOWN;
      case 4:
        return TankAction.LEFT;
      case 5:
        return TankAction.FIRE;
    }
    return TankAction.NONE;
  }
}
