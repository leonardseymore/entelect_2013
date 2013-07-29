package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.*;

public class Fire extends Task {
  public Result run(GameState gameState, Tank tank) {
    tank.setNextAction(TankAction.FIRE);
    return Result.Complete;
  }

  @Override
  protected String getLabel() {
    return "Fire";
  }
}
