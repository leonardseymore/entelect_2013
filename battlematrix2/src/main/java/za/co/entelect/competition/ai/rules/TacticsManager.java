package za.co.entelect.competition.ai.rules;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.decision.behavior.BehaviorTreeFactory;
import za.co.entelect.competition.ai.decision.behavior.Task;
import za.co.entelect.competition.domain.*;

import java.util.*;

public class TacticsManager {

  private enum Strategy {
    FATALITY, AGGRESSIVE, BALANCED, DEFENSIVE, WEARY
  }

  private GameState gameState;
  private Strategy strategy;

  public TacticsManager(GameState gameState) {
    this.gameState = gameState;
  }

  public void update() {
    Map<String, Tank> yTanks = gameState.getYourTanks();
    Map<String, Tank> oTanks = gameState.getOpponentTanks();

    if (oTanks.size() == 0) {
      strategy = Strategy.FATALITY;
      fatality();
    } else if (yTanks.size() == 0) {
      // cry silently
    } else if (yTanks.size() > oTanks.size()) {
      strategy = Strategy.AGGRESSIVE;
      aggressive();
    } else if (yTanks.size() == oTanks.size()) {
      if (yTanks.size() == 2) {
        strategy = Strategy.BALANCED;
        balanced();
      } else {
        strategy = Strategy.WEARY;
        weary();
      }
    } else if (yTanks.size() < oTanks.size()) {
      strategy = Strategy.DEFENSIVE;
      defensive();
    }
  }

  private List<Tank> getYbaseThreats() {
    Map<String, Tank> oTanks = gameState.getOpponentTanks();
    DirichletDomains dirichletDomains = gameState.getDirichletDomains();
    List<Tank> ybaseThreats = new ArrayList<>();
    for (Tank ot : oTanks.values()) {
      Base base = dirichletDomains.getBase(ot);
      if (base != null && base.isYourBase()) {
        ybaseThreats.add(ot);
      }
    }
    return ybaseThreats;
  }

  private Tank getClosestTank(Entity target) {
    Map<String, Tank> ytanks = gameState.getYourTanks();
    Tank closestTank = null;
    int closestDist = 0;
    for (Tank yt : ytanks.values()) {
      int dist = Util.manhattanDist(yt, target);
      if (closestTank == null || dist < closestDist) {
        closestTank = yt;
        closestDist = dist;
      }
    }
    return closestTank;
  }
  private Tank getOtherTank(Tank tank) {
    Map<String, Tank> ytanks = gameState.getYourTanks();
    if (tank.getId() == Ids.Y1) {
      return ytanks.get(Ids.Y2);
    } else {
      return ytanks.get(Ids.Y1);
    }
  }

  private Tank defendBaseWithClosestTank() {
    Base ybase = gameState.getYourBase();
    Tank closestTank = getClosestTank(ybase);
    defendBase(closestTank);
    return closestTank;
  }

  private void defendBase(Tank yt) {
    Base ybase = gameState.getYourBase();
    yt.getBlackboard().setTarget(ybase);
    Task tree = BehaviorTreeFactory.defendBase();
    tree.run(gameState, yt);
  }

  private Tank attackBaseWithClosestTank() {
    Base obase = gameState.getOpponentBase();
    Tank closestTank = getClosestTank(obase);
    attackBase(closestTank);
    return closestTank;
  }

  private void attackBase(Tank yt) {
    Base obase = gameState.getOpponentBase();
    yt.getBlackboard().setTarget(obase);
    Task tree = BehaviorTreeFactory.attackBase();
    tree.run(gameState, yt);
  }

  private void attackBothTanks() {
    Map<String, Tank> otanks = gameState.getOpponentTanks();
    Tank o1 = otanks.get(Ids.O1);
    Tank closestTank = getClosestTank(o1);
    closestTank.getBlackboard().setTarget(o1);
    Task y1tree = BehaviorTreeFactory.attackTank();
    y1tree.run(gameState, closestTank);

    Tank o2 = otanks.get(Ids.O2);
    Tank otherTank = getOtherTank(closestTank);
    otherTank.getBlackboard().setTarget(o2);
    Task y2tree = BehaviorTreeFactory.attackTank();
    y2tree.run(gameState, otherTank);
  }

  private Tank attackTank(Tank ot) {
    Tank closestTank = getClosestTank(ot);
    closestTank.getBlackboard().setTarget(ot);
    Task y1tree = BehaviorTreeFactory.attackTank();
    y1tree.run(gameState, closestTank);
    return closestTank;
  }

  private void moveToFrontLines(Tank yt) {
    //To change body of created methods use File | Settings | File Templates.
  }

  private void fatality() {
    attackBaseWithClosestTank();
  }

  private void aggressive() {
    Tank ot = gameState.getOpponentTanks().values().iterator().next();
    Tank yt = attackTank(ot);
    attackBase(getOtherTank(yt));
  }

  private void balanced() {
    List<Tank> threats = getYbaseThreats();
    if (threats.size() == 2) {
      attackBothTanks();
    } else if (threats.size() == 1) {
      Tank yt = attackTank(threats.get(0));
      defendBase(getOtherTank(yt));
    } else {
      Tank yt = defendBaseWithClosestTank();
      moveToFrontLines(getOtherTank(yt));
    }
  }

  private void weary() {
    if (getYbaseThreats().size() > 0) {
      defendBaseWithClosestTank();
    } else {
      attackBaseWithClosestTank();
    }
  }

  private void defensive() {
    defendBaseWithClosestTank();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    sb.append("strategy=").append(strategy);
    sb.append('}');
    return sb.toString();
  }
}
