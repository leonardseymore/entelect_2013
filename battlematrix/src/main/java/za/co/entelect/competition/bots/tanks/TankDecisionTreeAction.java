package za.co.entelect.competition.bots.tanks;

import za.co.entelect.competition.bots.descision.tree.DecisionTreeAction;
import za.co.entelect.competition.bots.descision.tree.DecisionTreeNode;
import za.co.entelect.competition.bots.descision.tree.DecisionTreeNodeType;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankAction;
import za.co.entelect.competition.domain.TankId;

public class TankDecisionTreeAction extends DecisionTreeAction {

  private TankAction action;

  public TankDecisionTreeAction(TankAction action) {
    this.action = action;
  }

  public TankAction getTankAction() {
    return action;
  }
}