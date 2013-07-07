package za.co.entelect.competition.bots;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Player;
import za.co.entelect.competition.domain.Tank;

public class DummyTank extends Tank {

  public DummyTank(int x, int y, GameState gameState, Player owner, Direction direction) {
    super(x, y, gameState, owner, direction);
  }

  @Override
  public TankAction getAction() {
    return TankAction.NONE;
  }
}
