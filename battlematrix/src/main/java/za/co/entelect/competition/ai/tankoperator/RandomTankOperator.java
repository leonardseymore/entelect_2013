package za.co.entelect.competition.ai.tankoperator;

import za.co.entelect.competition.domain.*;

import java.util.Random;

public class RandomTankOperator implements TankOperator {

  private Random random = new Random(System.currentTimeMillis());

  @Override
  public TankAction getAction(GameState gameState, Tank tank) {
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
