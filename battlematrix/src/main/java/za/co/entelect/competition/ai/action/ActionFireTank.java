package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankAction;

public class ActionFireTank extends Action {

  private Tank tank;
  private boolean fired = false;

  public ActionFireTank(Tank tank) {
    this.tank = tank;
    this.expiryTime = 1;
  }

  @Override
  public void doExecute(GameState gameState) {
    Tank clone = gameState.getTank(tank.getTankId());
    if (clone != null) {
      clone.setNextAction(TankAction.FIRE);
    }
    fired = true;
  }

  @Override
  public boolean isComplete() {
    return fired;
  }

  public String getDescription() {
    return "Fire " + tank.getTankId().name();
  }
}
