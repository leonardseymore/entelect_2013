package za.co.entelect.competition.bots.descision.behaviortree;

import java.util.Collection;

public class Task {

  protected Collection<Task> children;

  public Task(Collection<Task> children) {
    this.children = children;
  }

  public boolean run() {
    for (Task child : children) {
      if(!child.run()) {
        return false;
      }
    }
    return true;
  }
}
