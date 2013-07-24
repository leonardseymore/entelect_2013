package za.co.entelect.competition.ai.blackboard;

import za.co.entelect.competition.domain.Point;

public class Blackboard {
  private String targetId;
  private Point targetLocation;

  public Point getTargetLocation() {
    return targetLocation;
  }

  public void setTargetLocation(Point targetLocation) {
    this.targetLocation = targetLocation;
  }

  public String getTargetId() {
    return targetId;
  }

  public void setTargetId(String targetId) {
    this.targetId = targetId;
  }
}
