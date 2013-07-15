package za.co.entelect.competition.bots.descision.behaviortree;

import java.util.Collection;

public class Selector extends Task {

  public Selector(Collection<Task> children) {
    super(children);
  }

  public boolean run() {
    for (Task child : children) {
      if(child.run()) {
        return true;
      }
    }
    return false;
  }
}
