package za.co.entelect.competition.bots.tanks;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

public class DummyTank extends Tank {

  public DummyTank(TankId id) {
    super(id);
  }

  @Override
  public TankAction doGetAction(GameState gameState) {
    return TankAction.NONE;
  }
}
