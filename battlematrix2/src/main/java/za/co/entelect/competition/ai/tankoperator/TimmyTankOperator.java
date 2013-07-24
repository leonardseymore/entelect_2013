package za.co.entelect.competition.ai.tankoperator;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.pathfinding.PathFinderGoal;
import za.co.entelect.competition.ai.planning.*;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.swing.Mouse;

import java.util.*;

public class TimmyTankOperator implements TankOperator {

  private static final Logger logger = Logger.getLogger(TimmyTankOperator.class);

  @Override
  public void execute(GameState gameState, Tank tank) {
    if (true || tank.getPlan() == null) {
      String enemyBaseId = tank.isYourTank() ? Ids.OPPONENT_BASE : Ids.YOUR_BASE;
      Entity base = gameState.getEntity(enemyBaseId);

      Stack<PathFinder.Node> path = PathFinder.closestPathAStar(gameState, tank, base.getX(), base.getY(), false);
      if (path != null) {
        tank.getBlackboard().setTargetLocation(new Point(base.getX(), base.getY()));
        tank.getBlackboard().setTargetId(enemyBaseId);

        Collection<Action> actions = new ArrayList<>();
        actions.add(new ActionDestroyTarget(gameState, tank));
        actions.add(new ActionLookAtTarget(gameState, tank));
        actions.add(new ActionAlignToTarget(gameState, tank));

        Goal goal = new GoalDestroyEnemyBase(enemyBaseId);
        Plan plan = PathFinderGoal.getPlan(gameState.toGameModel(), goal, actions);
        if (plan != null) {
          tank.setPlan(plan);
        }
      } else {
        Stack<PathFinder.Node> closestPath = PathFinder.closestPathAStar(gameState, tank, base.getX(), base.getY(), true);
        String tankId = tank.getId();
        if (closestPath != null && closestPath.size() > 1) {
          PathFinder.Node target = closestPath.firstElement();

          tank.getBlackboard().setTargetLocation(new Point(target.getX(), target.getY()));
          tank.getBlackboard().setTargetId(null);

          Collection<Action> actions = new ArrayList<>();
          actions.add(new ActionMoveTo(gameState, tank));

          Goal goal = new GoalMoveTo(tankId, target.getX(), target.getY());
          Plan plan = PathFinderGoal.getPlan(gameState.toGameModel(), goal, actions);
          if (plan != null) {
            tank.setPlan(plan);
          }
        } else {
          tank.getBlackboard().setTargetLocation(new Point(base.getX(), base.getY()));
          tank.getBlackboard().setTargetId(enemyBaseId);

          ActionAlignToTarget actionAlignToTarget = new ActionAlignToTarget(gameState, tank);
          ActionLookAtTarget actionLookAtTarget = new ActionLookAtTarget(gameState, tank);
          if (!actionLookAtTarget.isComplete(gameState)) {
            actionLookAtTarget.execute(gameState);
          } else {
            tank.setNextAction(TankAction.FIRE);
          }
        }
      }
    }
  }
}