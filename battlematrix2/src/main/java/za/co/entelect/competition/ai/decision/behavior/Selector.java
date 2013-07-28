package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

public class Selector extends Task {
  public Result run(GameState gameState, Tank tank) {
    Result result = Result.Fail;
    for (Task child : children) {
      result = child.run(gameState, tank);
      if (result == Result.Complete || result == Result.InProgress) {
        break;
      }
    }
    return result;
  }
}
