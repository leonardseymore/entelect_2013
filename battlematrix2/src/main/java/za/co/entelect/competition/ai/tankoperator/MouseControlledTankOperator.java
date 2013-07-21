package za.co.entelect.competition.ai.tankoperator;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.planning.ActionManager;
import za.co.entelect.competition.ai.planning.ActionMoveTo;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankOperator;
import za.co.entelect.competition.swing.Mouse;

import java.util.Stack;

public class MouseControlledTankOperator implements TankOperator, PathAware {

  private static final Logger logger = Logger.getLogger(MouseControlledTankOperator.class);

  private int targetX = -1;
  private int targetY = -1;
  private ActionMoveTo action;

  public Stack<PathFinder.Node> getPath() {
    if (action != null) {
    //  return action.getPath();
    }
    return null;
  }

  @Override
  public void execute(GameState gameState, Tank tank) {
    Mouse mouse = Mouse.getInstance();
    if (mouse.buttonDownOnce(1)) {
      targetX = (int)(mouse.getPosition().getX() / mouse.getZoomFactor());
      targetY = (int)(mouse.getPosition().getY() / mouse.getZoomFactor());
      logger.debug("New target set (" + targetX + "," + targetY + ")");
      if (action != null) {
        action.cancel();
      }
      Stack<PathFinder.Node> path = PathFinder.closestPathAStar(gameState, tank, targetX, targetY, true);
      action = new ActionMoveTo(tank.getId() , targetX, targetY, path);
      ActionManager.getInstance().scheduleAction(action);
    }
  }
}
