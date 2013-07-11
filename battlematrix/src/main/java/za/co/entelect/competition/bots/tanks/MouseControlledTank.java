package za.co.entelect.competition.bots.tanks;

import org.apache.log4j.Logger;
import za.co.entelect.competition.swing.Mouse;
import za.co.entelect.competition.bots.movement.SeekPath;
import za.co.entelect.competition.bots.pathfinding.PathFinder;
import za.co.entelect.competition.domain.*;

import java.util.Stack;

public class MouseControlledTank extends Tank {

  private static final Logger logger = Logger.getLogger(MouseControlledTank.class);

  private Mouse mouse;
  private Stack<PathFinder.Node> path;
  private PathFinder pathFinder;
  private SeekPath seek;

  private int targetX = -1;
  private int targetY = -1;

  public MouseControlledTank(String name, int x, int y, GameState gameState, Player owner, Direction direction) {
    super(name, x, y, gameState, owner, direction);
    this.mouse = Mouse.getInstance();
    this.pathFinder = new PathFinder(this, Obstruction.BORDER | Obstruction.WALL);
    this.seek = new SeekPath(this);
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
      seek.setPath(path);
      logger.debug("Path finding took [" + (System.currentTimeMillis() - start) + "ms]");
    }

    return seek.getAction();
  }
}