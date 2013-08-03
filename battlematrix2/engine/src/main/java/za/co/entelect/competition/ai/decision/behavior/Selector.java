package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

public class Selector extends Task {
  public boolean run(GameState gameState, Tank tank) {
    for (Task child : children) {
      if (child.run(gameState, tank)) {
        return true;
      }
    }
    return false;
  }

  @Override
  protected String getLabel() {
    return "?";
  }
}
