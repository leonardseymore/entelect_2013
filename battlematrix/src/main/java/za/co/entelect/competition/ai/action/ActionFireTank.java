package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.domain.Directed;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankAction;

public class ActionFireTank extends Action {

  private GameState gameState;
  private Tank tank;

  public ActionFireTank(GameState gameState, Tank tank) {
    this.gameState = gameState;
    this.tank = tank;
    this.expiryTime = 1;
  }

  @Override
  public void doExecute() {
    tank.setNextAction(TankAction.FIRE);
  }

  @Override
  public boolean isComplete() {
    return false;
  }
}
