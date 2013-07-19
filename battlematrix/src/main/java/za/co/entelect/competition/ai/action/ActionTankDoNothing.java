package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankAction;

public class ActionTankDoNothing extends Action {

  private Tank tank;
  private boolean didNothing = false;

  public ActionTankDoNothing(Tank tank) {
    this.tank = tank;
    this.expiryTime = 1;
  }

  @Override
  public void doExecute(GameState gameState) {
    Tank clone = gameState.getTank(tank.getTankId());
    if (clone != null) {
      clone.setNextAction(TankAction.NONE);
    }
    didNothing = true;
  }

  @Override
  public boolean isComplete() {
    return didNothing;
  }

  public String getDescription() {
    return "Nothing " + tank.getTankId().name();
  }
}
