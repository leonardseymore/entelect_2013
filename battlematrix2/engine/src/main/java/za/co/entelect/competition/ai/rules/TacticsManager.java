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
  private Player player;

  public TacticsManager(GameState gameState, Player player) {
    this.gameState = gameState;
    this.player = player;
  }

  public void update() {
    Map<String, Tank> yTanks = gameState.getPlayerTanks(player);
    Map<String, Tank> oTanks = gameState.getEnemyTanks(player);

    if (yTanks.size() == 0) {
      // cry silently
    } else if (oTanks.size() == 0) {
      strategy = Strategy.FATALITY;
      fatality();
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
    Map<String, Tank> oTanks = gameState.getEnemyTanks(player);
    DirichletDomains dirichletDomains = gameState.getDirichletDomains();
    List<Tank> ybaseThreats = new ArrayList<>();
    for (Tank ot : oTanks.values()) {
      Base base = dirichletDomains.getBase(ot);
      if (base != null && base.getOwner() == player) {
        ybaseThreats.add(ot);
      }
    }
    return ybaseThreats;
  }

  private Tank getClosestTank(Entity target) {
    Map<String, Tank> ytanks = gameState.getPlayerTanks(player);
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

  private Tank getClosestEnemyTank(Entity target) {
    Map<String, Tank> otanks = gameState.getEnemyTanks(player);
    Tank closestTank = null;
    int closestDist = 0;
    for (Tank ot : otanks.values()) {
      int dist = Util.manhattanDist(ot, target);
      if (closestTank == null || dist < closestDist) {
        closestTank = ot;
        closestDist = dist;
      }
    }
    return closestTank;
  }

  private Tank getOtherTank(Tank tank) {
    Map<String, Tank> ytanks = gameState.getPlayerTanks(player);
    if (player == Player.YOU) {
      if (tank.getId() == Ids.Y1) {
        return ytanks.get(Ids.Y2);
      } else {
        return ytanks.get(Ids.Y1);
      }
    } else {
      if (tank.getId() == Ids.O1) {
        return ytanks.get(Ids.O2);
      } else {
        return ytanks.get(Ids.O1);
      }
    }
  }

  private Tank getOtherEnemyTank(Tank tank) {
    Map<String, Tank> otanks = gameState.getEnemyTanks(player);
    if (player == Player.OPPONENT) {
      if (tank.getId() == Ids.Y1) {
        return otanks.get(Ids.Y2);
      } else {
        return otanks.get(Ids.Y1);
      }
    } else {
      if (tank.getId() == Ids.O1) {
        return otanks.get(Ids.O2);
      } else {
        return otanks.get(Ids.O1);
      }
    }
  }

  private Tank defendBaseWithClosestTank() {
    Base ybase = gameState.getPlayerBase(player);
    Tank closestTank = getClosestTank(ybase);
    defendBase(closestTank);
    return closestTank;
  }

  private void defendBase(Tank yt) {
    Base ybase = gameState.getPlayerBase(player);
    yt.getBlackboard().setTarget(ybase);
    Task tree = BehaviorTreeFactory.DEFEND_BASE;
    tree.run(gameState, yt);
  }

  private Tank attackBaseWithClosestTank() {
    Base obase = gameState.getEnemyBase(player);
    Tank closestTank = getClosestTank(obase);
    attackBase(closestTank);
    return closestTank;
  }

  private void attackBase(Tank yt) {
    Base obase = gameState.getEnemyBase(player);
    yt.getBlackboard().setTarget(obase);
    Task tree = BehaviorTreeFactory.ATTACK_BASE;
    tree.run(gameState, yt);
  }

  private void attackBothTanks() {
    Map<String, Tank> ytanks = gameState.getPlayerTanks(player);
    Iterator<Tank> tankIterator = ytanks.values().iterator();
    Tank y1 = tankIterator.next();
    Tank y2 = tankIterator.next();
    Tank o1 = getClosestEnemyTank(y1);

    // TODO: try to stick with your target to avoid ... the wiggles
    Tank o2 = getClosestEnemyTank(y2);
    if (o1.getId().equals(o2.getId())) {
      if (Util.manhattanDist(y1, o1) > Util.manhattanDist(y2, o2)) {
        attackTank(y2, o1);
        attackTank(y1, o2);
      } else {
        attackTank(y1, o1);
        attackTank(y2, o2);
      }
    } else {
      attackTank(y1, o1);
      attackTank(y2, o2);
    }
  }

  private Tank attackTank(Tank ot) {
    Tank closestTank = getClosestTank(ot);
    attackTank(closestTank, ot);
    return closestTank;
  }

  private void attackTank(Tank yt, Tank ot) {
    yt.getBlackboard().setTarget(ot);
    if (yt.getOwner() == Player.YOU) {
      Task y1tree = BehaviorTreeFactory.ATTACK_TANK_CLOSEST_MOVE;
      y1tree.run(gameState, yt);
    } else {
      Task y1tree = BehaviorTreeFactory.ATTACK_TANK_CLOSEST_MOVE;
      y1tree.run(gameState, yt);
    }
  }

  private void fatality() {
    attackBaseWithClosestTank();
  }

  private void aggressive() {
    Tank ot = gameState.getEnemyTanks(player).values().iterator().next();
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
      attackBothTanks();
    }
  }

  private void weary() {
    Tank ot = gameState.getEnemyTanks(player).values().iterator().next();
    attackTank(ot);
  }

  private void defensive() {
    defendBaseWithClosestTank();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    sb.append("strategy=").append(strategy);

    for (Tank tank : gameState.getTanks().values()) {
      sb.append(", ");
      Entity target = tank.getBlackboard().getTarget();
      sb.append(tank.getId())
        .append("->")
        .append(target == null ? null : target.getId());
    }

    sb.append('}');
    return sb.toString();
  }
}
