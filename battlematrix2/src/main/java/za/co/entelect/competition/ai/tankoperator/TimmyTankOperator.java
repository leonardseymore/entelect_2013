package za.co.entelect.competition.ai.tankoperator;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.pathfinding.PathFinderGoal;
import za.co.entelect.competition.ai.planning.*;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.swing.Mouse;

import java.util.ArrayList;
import java.util.Collection;

public class TimmyTankOperator implements TankOperator {

  private static final Logger logger = Logger.getLogger(TimmyTankOperator.class);

  @Override
  public void execute(GameState gameState, Tank tank) {
    Plan plan = tank.getPlan();
    if (plan == null) {
      String enemyBaseId = tank.isYourTank() ? Ids.OPPONENT_BASE : Ids.YOUR_BASE;

      Entity base = gameState.getEntity(enemyBaseId);
      tank.getBlackboard().setTargetLocation(new Point(base.getX(), base.getY()));
      tank.getBlackboard().setTargetId(enemyBaseId);

      Collection<Action> actions = new ArrayList<>();
      actions.add(new ActionDestroyTarget(gameState, tank));
      actions.add(new ActionLookAtTarget(gameState, tank));
      actions.add(new ActionAlignToTarget(gameState, tank));

      Goal goal = new GoalDestroyEnemyBase(enemyBaseId);
      plan = PathFinderGoal.getPlan(gameState.toGameModel(), goal, actions);
      if (plan != null) {
        tank.setPlan(plan);
        //logger.debug("Set new plan [" + plan + "] for tank [" + tank.getId() + "]");
      } else {
        logger.debug("No valid plan found for tank [" + tank.getId() + "]");
      }
    }
  }
}
