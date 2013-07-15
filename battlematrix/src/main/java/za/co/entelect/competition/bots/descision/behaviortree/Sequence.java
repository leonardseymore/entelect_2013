package za.co.entelect.competition.bots.descision.behaviortree;

import java.util.Collection;

public class Sequence extends Task {

  public Sequence(Collection<Task> children) {
    super(children);
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
