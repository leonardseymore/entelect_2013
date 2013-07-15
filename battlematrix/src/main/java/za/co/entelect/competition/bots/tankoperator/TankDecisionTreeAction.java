package za.co.entelect.competition.bots.tankoperator;

import za.co.entelect.competition.bots.descision.tree.DecisionTreeAction;
import za.co.entelect.competition.domain.TankAction;

public class TankDecisionTreeAction extends DecisionTreeAction {

  private TankAction action;

  public TankDecisionTreeAction(TankAction action) {
    this.action = action;
  }

  public TankAction getTankAction() {
    return action;
  }
}