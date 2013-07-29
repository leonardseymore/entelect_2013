package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.Entity;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankAction;

import java.util.Stack;

public class MoveToClosest extends Task {
  public Result run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    Entity target = blackboard.getTarget();
    Stack<PathFinder.Node> path = PathFinder.closestPathAStar(gameState, tank, target.getX(), target.getY(), true);
    if (path != null && path.size() > 0) {
      TankAction tankAction = Seek.seekPath(gameState, tank, path);
      if (tankAction != TankAction.NONE) {
        tank.setNextAction(tankAction);
        return Result.InProgress;
      }
    }
    return Result.Complete;
  }

  @Override
  protected String getLabel() {
    return "MoveToClosest";
  }
}
