package za.co.entelect.competition.bots.tanks;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Player;
import za.co.entelect.competition.domain.Tank;

public class DummyTank extends Tank {

  public DummyTank() {
    super(null, 0, 0, null, null, Direction.UP);
  }

  public DummyTank(String name, int x, int y, GameState gameState, Player owner, Direction direction) {
    super(name, x, y, gameState, owner, direction);
  }

  @Override
  public TankAction doGetAction() {
    return TankAction.NONE;
  }
}
