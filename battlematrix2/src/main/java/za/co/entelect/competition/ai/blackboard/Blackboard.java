package za.co.entelect.competition.ai.blackboard;

import za.co.entelect.competition.domain.Bullet;
import za.co.entelect.competition.domain.Direction;
import za.co.entelect.competition.domain.Entity;
import za.co.entelect.competition.domain.TankAction;

import java.util.ArrayList;
import java.util.Collection;

public class Blackboard {

  private TankAction nextTankAction;
  private Entity target;
  private Collection<Bullet> threatBullets = new ArrayList<>();

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

  public TankAction getNextTankAction() {
    return nextTankAction;
  }

  public void setNextTankAction(TankAction nextTankAction) {
    this.nextTankAction = nextTankAction;
  }
}
