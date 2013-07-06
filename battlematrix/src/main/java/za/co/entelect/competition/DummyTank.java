package za.co.entelect.competition;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Player;
import za.co.entelect.competition.domain.Tank;

public class DummyTank extends Tank {

  public DummyTank(int x, int y, GameState gameState, Player owner, Direction direction) {
    super(x, y, gameState, owner, direction);
  }

  @Override
  protected TankAction getAction() {
    return TankAction.NONE;
  }
}
