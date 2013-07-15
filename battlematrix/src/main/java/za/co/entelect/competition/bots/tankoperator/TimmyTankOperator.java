package za.co.entelect.competition.bots.tankoperator;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.bots.descision.tree.*;
import za.co.entelect.competition.domain.*;

import java.util.Collection;

public class TimmyTankOperator implements TankOperator {

  private DecisionTreeNode createDecisionTree(GameState gameState, Tank tank) {
    Collection<Tank> enemyTanks = gameState.getEnemyTanks(tank);
    Tank closestTank = null;
    int closestDist = Integer.MAX_VALUE;
    for (Tank enemyTank : enemyTanks) {
      int dist = Util.manhattanDist(tank.getX(), tank.getY(), enemyTank.getX(), enemyTank.getY());
      if (dist < closestDist) {
        closestDist = dist;
        closestTank = enemyTank;
      }
    }


    DecisionTreeNode root = new RangeDecision<>(
      new TankDecisionTreeAction(TankAction.LEFT),
      new TankDecisionTreeAction(TankAction.RIGHT),
      closestDist,
      50,
      Integer.MAX_VALUE
    );

    return root;
  }

  @Override
  public TankAction getAction(GameState gameState, Tank tank) {
    DecisionTreeNode decTree = createDecisionTree(gameState, tank);
    DecisionTreeNode action = decTree.makeDecision();
    if (action != null && action.getType() == DecisionTreeNodeType.ACTION) {
      return ((TankDecisionTreeAction)action).getTankAction();
    }
    return TankAction.NONE;
  }
}
