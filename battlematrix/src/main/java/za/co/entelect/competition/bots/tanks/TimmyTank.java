package za.co.entelect.competition.bots.tanks;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.bots.descision.tree.*;
import za.co.entelect.competition.domain.*;

import java.util.Collection;

public class TimmyTank extends Tank {

  public TimmyTank(TankId id) {
    super(id);
  }

  private DecisionTreeNode createDecisionTree(GameState gameState) {
    Collection<Tank> enemyTanks = gameState.getEnemyTanks(this);
    Tank closestTank = null;
    int closestDist = Integer.MAX_VALUE;
    for (Tank enemyTank : enemyTanks) {
      int dist = Util.manhattanDist(x, y, enemyTank.getX(), enemyTank.getY());
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
  public TankAction doGetAction(GameState gameState) {
    DecisionTreeNode decTree = createDecisionTree(gameState);
    DecisionTreeNode action = decTree.makeDecision();
    if (action != null && action.getType() == DecisionTreeNodeType.ACTION) {
      return ((TankDecisionTreeAction)action).getTankAction();
    }
    return TankAction.NONE;
  }
}
