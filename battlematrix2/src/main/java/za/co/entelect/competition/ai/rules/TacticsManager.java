package za.co.entelect.competition.ai.rules;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.decision.behavior.BehaviorTreeFactory;
import za.co.entelect.competition.ai.decision.behavior.Task;
import za.co.entelect.competition.domain.*;

import java.util.*;

public class TacticsManager {

  private enum Strategy {
    FATALITY, AGGRESSIVE, BALANCED, DEFENSIVE
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
    } else if (yTanks.size() > oTanks.size()) {
      strategy = Strategy.AGGRESSIVE;
      aggressive();
    } else if (yTanks.size() == oTanks.size()) {
      strategy = Strategy.BALANCED;
      balanced();
    } else if (yTanks.size() < oTanks.size()) {
      strategy = Strategy.DEFENSIVE;
      defensive();
    }
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

  private void fatality() {
    Base obase = gameState.getOpponentBase();
    Tank closestTank = getClosestTank(obase);
    closestTank.getBlackboard().setTarget(obase);
    Task tree = BehaviorTreeFactory.attackBase();
    tree.run(gameState, closestTank);
  }

  private void aggressive() {

  }

  private void balanced() {

  }

  private void defensive() {

  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    sb.append("strategy=").append(strategy);
    sb.append('}');
    return sb.toString();
  }
}
