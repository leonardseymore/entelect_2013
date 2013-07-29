package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankAction;

public class Fire extends Task {
  public boolean run(GameState gameState, Tank tank) {
    tank.setNextAction(TankAction.FIRE);
    return true;
  }

  @Override
  protected String getLabel() {
    return "Fire";
  }
}
