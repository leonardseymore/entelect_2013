package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.*;

import java.util.Stack;

public class SetMove extends Task {
  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    Trackable target = blackboard.getMoveToPos();
    if (target == null) {
      target = blackboard.getTarget();
    }
    Stack<PathFinder.Node> path = PathFinder.closestPathAStar(gameState, tank, target.getX(), target.getY(), false);
    if (path != null && path.size() > 0) {
      TankAction tankAction = Seek.seekPath(gameState, tank, path);
      if (tankAction != TankAction.NONE) {
        blackboard.setNextTankAction(tankAction);
        return true;
      }
    }
    return false;
  }

  @Override
  protected String getLabel() {
    return "SetMove";
  }
}
