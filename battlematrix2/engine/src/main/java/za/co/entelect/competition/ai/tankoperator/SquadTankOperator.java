package za.co.entelect.competition.ai.tankoperator;

import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankOperator;

public class SquadTankOperator implements TankOperator {

  @Override
  public void execute(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    tank.setNextAction(blackboard.getNextOrder());
  }
}
