package za.co.entelect.competition.bots;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Mouse;
import za.co.entelect.competition.bots.movement.PathFinder;
import za.co.entelect.competition.bots.movement.Seek;
import za.co.entelect.competition.domain.GameElementVisitor;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Player;
import za.co.entelect.competition.domain.Tank;

import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;
import java.util.Stack;

public class MouseControlledTank extends Tank {

  private static final Logger logger = Logger.getLogger(MouseControlledTank.class);

  private Mouse mouse;
  private Stack<PathFinder.Node> path;

  private int targetX = -1;
  private int targetY = -1;

  public MouseControlledTank(int x, int y, GameState gameState, Player owner, Direction direction, Mouse mouse) {
    super(x, y, gameState, owner, direction);
    this.mouse = mouse;
  }

  public Stack<PathFinder.Node> getPath() {
    return path;
  }

  @Override
  protected TankAction getAction() {
    if (mouse.buttonDown(2)) {
      return TankAction.FIRE;
    }

    if (mouse.buttonDown(1)) {
      targetX = (int)(mouse.getPosition().getX() / mouse.getZoomFactor());
      targetY = (int)(mouse.getPosition().getY() / mouse.getZoomFactor());
      logger.debug("New target set (" + x + "," + y + ")");
      path = PathFinder.closestPathAStar(gameState, x, y, targetX, targetY);
    }

    return Seek.seekPath(this, path);
  }
}
