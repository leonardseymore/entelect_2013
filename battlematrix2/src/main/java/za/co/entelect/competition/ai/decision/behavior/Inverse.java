package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

public class Inverse extends Decorator {

  public Inverse(Task child) {
    super(child);
  }

  public Result run(GameState gameState, Tank tank) {
    Result result = child.run(gameState, tank);
    return result == Result.Complete ? Result.Fail : Result.Complete;
  }
}
