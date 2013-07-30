package za.co.entelect.competition.ai.blackboard;

import za.co.entelect.competition.domain.Bullet;
import za.co.entelect.competition.domain.Direction;
import za.co.entelect.competition.domain.Entity;

import java.util.ArrayList;
import java.util.Collection;

public class Blackboard {

  private Entity target;
  private Collection<Bullet> threatBullets = new ArrayList<>();
  private int moveTimes;
  private Direction moveTimesDirection;

  public Blackboard() {
  }

  public Entity getTarget() {
    return target;
  }

  public void setTarget(Entity target) {
    this.target = target;
  }

  public void setThreatBullets(Collection<Bullet> threatBullets) {
    this.threatBullets = threatBullets;
  }

  public Collection<Bullet> getThreatBullets() {
    return threatBullets;
  }

  public int getMoveTimes() {
    return moveTimes;
  }

  public void setMoveTimes(int moveTimes) {
    this.moveTimes = moveTimes;
  }

  public Direction getMoveTimesDirection() {
    return moveTimesDirection;
  }

  public void setMoveTimesDirection(Direction moveTimesDirection) {
    this.moveTimesDirection = moveTimesDirection;
  }
}
