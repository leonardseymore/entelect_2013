package za.co.entelect.competition.ai.tankoperator;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.tactics.TacticalPathFinder;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.swing.Mouse;

import java.util.Stack;

public class MouseControlledTankOperator implements TankOperator, PathAware {

  private static final Logger logger = Logger.getLogger(MouseControlledTankOperator.class);

  private Stack<Trackable> path;

  @Override
  public Stack<Trackable> getPath() {
    return path;
  }

  @Override
  public void execute(GameState gameState, Tank tank) {
    Mouse mouse = Mouse.getInstance();
    if (mouse.buttonDownOnce(1)) {
      int targetX = (int)(mouse.getPosition().getX() / mouse.getZoomFactor());
      int targetY = (int)(mouse.getPosition().getY() / mouse.getZoomFactor());
      logger.debug("New target set (" + targetX + "," + targetY + ")");
      path = TacticalPathFinder.getBestPath(gameState, tank, targetX, targetY);
    }
    if (path != null && path.size() > 0) {
      tank.setNextAction(Seek.seekPath(gameState, tank, path));
    }
  }
}
