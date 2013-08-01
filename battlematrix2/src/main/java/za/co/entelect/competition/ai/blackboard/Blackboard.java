package za.co.entelect.competition.ai.blackboard;

import za.co.entelect.competition.domain.*;

import java.util.ArrayList;
import java.util.Collection;

public class Blackboard {

  private TankAction nextTankAction;
  private Entity target;
  private Collection<Bullet> threatBullets;
  private Trackable moveToPos;
  private TankAction nextOrder;

  public Blackboard() {
    reset();
  }

  public void reset() {
    nextTankAction = null;
    target = null;
    threatBullets = new ArrayList<>();
    moveToPos = null;
    nextOrder = null;
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

  public TankAction getNextTankAction() {
    return nextTankAction;
  }

  public void setNextTankAction(TankAction nextTankAction) {
    this.nextTankAction = nextTankAction;
  }

  public Trackable getMoveToPos() {
    return moveToPos;
  }

  public void setMoveToPos(Trackable moveToPos) {
    this.moveToPos = moveToPos;
  }

  public void setNextOrder(TankAction nextOrder) {
    this.nextOrder = nextOrder;
  }

  public TankAction getNextOrder() {
    return nextOrder;
  }
}
