package za.co.entelect.competition.bots;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Player;
import za.co.entelect.competition.domain.Tank;

import java.util.Random;

public class RandomTank extends Tank {

  private Random random;

  public RandomTank(String name, int x, int y, GameState gameState, Player owner, Direction direction) {
    super(name, x, y, gameState, owner, direction);
    random = new Random(System.currentTimeMillis());
  }

  @Override
  public TankAction doGetAction() {
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
