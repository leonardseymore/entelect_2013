package za.co.entelect.competition.bots.tanks;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.bots.descision.tree.*;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankAction;
import za.co.entelect.competition.domain.TankId;

public class TimmyTank extends Tank {

  public TimmyTank(TankId id) {
    super(id);
  }

  private DecisionTreeNode createDecisionTree(GameState gameState) {
    Tank t = gameState.getTank(TankId.P1T1);
    DecisionTreeNode root = new RangeDecision<>(
      new TankDecisionTreeAction(TankAction.LEFT),
      new TankDecisionTreeAction(TankAction.RIGHT),
      Util.manhattanDist(x, y, t.getX(), t.getY()),
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
