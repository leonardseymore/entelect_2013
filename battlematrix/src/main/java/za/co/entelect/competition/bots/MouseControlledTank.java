package za.co.entelect.competition.bots;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Mouse;
import za.co.entelect.competition.bots.movement.PathFinder;
import za.co.entelect.competition.bots.movement.Seek;
import za.co.entelect.competition.domain.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;
import java.util.Stack;

public class MouseControlledTank extends Tank {

  private static final Logger logger = Logger.getLogger(MouseControlledTank.class);

  private Mouse mouse;
  private Stack<PathFinder.Node> path;
  private PathFinder pathFinder;

  private int targetX = -1;
  private int targetY = -1;

  public MouseControlledTank(String name, int x, int y, GameState gameState, Player owner, Direction direction, Mouse mouse) {
    super(name, x, y, gameState, owner, direction);
    this.mouse = mouse;
    this.pathFinder = new PathFinder(gameState, Obstruction.BORDER | Obstruction.WALL);
  }

  public Stack<PathFinder.Node> getPath() {
    return path;
  }

  @Override
  public TankAction doGetAction() {
    if (mouse.buttonDown(2)) {
      return TankAction.FIRE;
    }

    if (mouse.buttonDown(1)) {
      targetX = (int)(mouse.getPosition().getX() / mouse.getZoomFactor());
      targetY = (int)(mouse.getPosition().getY() / mouse.getZoomFactor());
      logger.debug("New target set (" + x + "," + y + ")");
      long start = System.currentTimeMillis();
      path = pathFinder.closestPathAStar(x, y, targetX, targetY, true);
      logger.debug("Path finding took [" + (System.currentTimeMillis() - start) + "ms]");
    }

    return Seek.seekPath(this, path);
  }
}
