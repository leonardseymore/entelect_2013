package za.co.entelect.competition.ai.tankoperator;

import za.co.entelect.competition.domain.*;

public class DummyTankOperator implements TankOperator {

  @Override
  public TankAction getAction(GameState gameState, Tank tank) {
    return TankAction.NONE;
  }
}
