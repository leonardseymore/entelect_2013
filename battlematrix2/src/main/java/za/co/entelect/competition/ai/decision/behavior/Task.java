package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

import java.util.ArrayList;
import java.util.List;

public abstract class Task {

  protected List<Task> children;

  protected Task() {
    this.children = new ArrayList<>();
  }

  public Task addChild(Task child) {
    children.add(child);
    return this;
  }

  public abstract Result run(GameState gameState, Tank tank);
}
