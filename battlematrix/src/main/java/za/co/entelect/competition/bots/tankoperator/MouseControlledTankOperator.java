package za.co.entelect.competition.bots.tankoperator;

import org.apache.log4j.Logger;
import za.co.entelect.competition.swing.Mouse;
import za.co.entelect.competition.bots.movement.SeekPath;
import za.co.entelect.competition.bots.pathfinding.PathFinder;
import za.co.entelect.competition.domain.*;

import java.util.Stack;

public class MouseControlledTankOperator implements TankOperator, PathAware {

  private static final Logger logger = Logger.getLogger(MouseControlledTankOperator.class);

  private Stack<PathFinder.Node> path;
  private PathFinder pathFinder;
  private SeekPath seek;

  private int targetX = -1;
  private int targetY = -1;

  public Stack<PathFinder.Node> getPath() {
    return path;
  }

  @Override
  public TankAction getAction(GameState gameState, Tank tank) {

    Mouse mouse = Mouse.getInstance();
    if (mouse.buttonDown(2)) {
      return TankAction.FIRE;
    }

    if (mouse.buttonDown(1)) {
      targetX = (int)(mouse.getPosition().getX() / mouse.getZoomFactor());
      targetY = (int)(mouse.getPosition().getY() / mouse.getZoomFactor());
      logger.debug("New target set (" + targetX + "," + targetY + ")");
      long start = System.currentTimeMillis();
      this.pathFinder = new PathFinder(gameState, tank);
      path = pathFinder.closestPathAStar(tank.getX(), tank.getY(), targetX, targetY, true);
      this.seek = new SeekPath(gameState, tank);
      seek.setPath(path);
      logger.debug("Path finding took [" + (System.currentTimeMillis() - start) + "ms]");
    }

    if (seek != null) {
      return seek.getAction();
    }
    return TankAction.NONE;
  }
}
