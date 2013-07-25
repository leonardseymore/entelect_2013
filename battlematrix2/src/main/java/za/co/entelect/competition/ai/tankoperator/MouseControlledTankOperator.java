package za.co.entelect.competition.ai.tankoperator;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.movement.Seek;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.swing.Mouse;

import java.util.Stack;

public class MouseControlledTankOperator implements TankOperator, PathAware {

  private static final Logger logger = Logger.getLogger(MouseControlledTankOperator.class);

  private Stack<PathFinder.Node> path;

  @Override
  public Stack<PathFinder.Node> getPath() {
    return path;
  }

  @Override
  public void execute(GameState gameState, Tank tank) {
    Mouse mouse = Mouse.getInstance();
    if (mouse.buttonDownOnce(1)) {
      int targetX = (int)(mouse.getPosition().getX() / mouse.getZoomFactor());
      int targetY = (int)(mouse.getPosition().getY() / mouse.getZoomFactor());
      logger.debug("New target set (" + targetX + "," + targetY + ")");

      path = PathFinder.closestPathAStar(gameState, tank, targetX, targetY, false);
    }
    if (path != null) {
      TankAction tankAction = Seek.seekPath(gameState, tank, path);
      tank.setNextAction(tankAction);
    }
  }
}
