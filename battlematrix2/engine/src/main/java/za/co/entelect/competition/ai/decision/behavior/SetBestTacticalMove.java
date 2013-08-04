package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.tactics.TacticalPathFinder;
import za.co.entelect.competition.domain.*;

import java.util.Stack;

public class SetBestTacticalMove extends Task {
  public boolean run(GameState gameState, Tank tank) {
    Blackboard blackboard = tank.getBlackboard();
    Entity target = blackboard.getTarget();
    Stack<Trackable> path = TacticalPathFinder.getBestPath(gameState, tank, target.getX(), target.getY());
    if (path != null && path.size() > 0) {
      TankAction tankAction = Seek.seekPath(gameState, tank, path);
      if (tankAction != TankAction.NONE) {
        blackboard.setNextTankAction(tankAction);
        return true;
      } else {
        Walls walls = gameState.getWalls();
        Point inFrontPos = tank.getInFrontPos();
        int x = inFrontPos.getX();
        int y = inFrontPos.getY();
        if (gameState.isInBounds(x, y)) {
          if (walls.hasWall(x, y)) {
            blackboard.setNextTankAction(TankAction.FIRE);
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  protected String getLabel() {
    return "SetBestTacticalMove";
  }
}
