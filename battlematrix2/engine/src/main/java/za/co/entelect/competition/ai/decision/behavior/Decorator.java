package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.*;

public abstract class Decorator extends Task {
  protected Task child;

  public Decorator(Task child) {
    this.child = child;
  }
}
