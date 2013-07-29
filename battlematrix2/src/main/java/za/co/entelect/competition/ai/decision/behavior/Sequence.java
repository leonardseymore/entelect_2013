package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

public class Sequence extends Task {

  public Result run(GameState gameState, Tank tank) {
    for (Task child : children) {
      Result result = child.run(gameState, tank);
      if (result == Result.Fail || result == Result.InProgress) {
        return result;
      }
    }
    return Result.Complete;
  }

  @Override
  protected String getLabel() {
    return "->";
  }
}
