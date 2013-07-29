package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

public class Inverse extends Decorator {

  public Inverse(Task child) {
    super(child);
  }

  public boolean run(GameState gameState, Tank tank) {
    return !child.run(gameState, tank);
  }

  @Override
  protected String getLabel() {
    return "~" + child.getLabel();
  }
}
