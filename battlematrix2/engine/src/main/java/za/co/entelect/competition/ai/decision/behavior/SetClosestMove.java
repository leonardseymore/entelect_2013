package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.tactics.TacticalPathFinder;
import za.co.entelect.competition.domain.*;

import java.util.Stack;

public class SetClosestMove extends Task {
  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    Entity target = blackboard.getTarget();
    Stack<Trackable> path = PathFinder.closestPathAStar(gameState, tank, target.getX(), target.getY(), true);
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
    return "SetClosestMove";
  }
}
