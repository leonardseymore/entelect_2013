package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Task {

  public static final AtomicInteger ID_GEN = new AtomicInteger();
  private int id = ID_GEN.incrementAndGet();
  protected List<Task> children;

  protected Task() {
    this.children = new ArrayList<>();
  }

  public Task a(Task child) {
    children.add(child);
    return this;
  }

  public abstract boolean run(GameState gameState, Tank tank);

  public String toDot() {
    StringBuilder buffer = new StringBuilder();
    buffer.append("digraph Task {\n");
    toDot(this, buffer);
    buffer.append("}");
    return buffer.toString();
  }

  private void toDot(Task task, StringBuilder buffer) {
    String parentId = task.getClass().getSimpleName() + task.id;
    for (Task child : task.children) {
      String childId = child.getClass().getSimpleName() + child.id;
      buffer.append("  ")
        .append(parentId)
        .append(" -> ")
        .append(childId)
        .append("\n")
        .append("  ")
        .append(parentId)
        .append(" [label=\"" + task.getLabel() + "\"]\n")
        .append("  ")
        .append(childId)
        .append(" [label=\"" + child.getLabel() + "\"]\n");
      toDot(child, buffer);
    }
  }

  protected abstract String getLabel();
}
