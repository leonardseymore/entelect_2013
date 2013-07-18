package za.co.entelect.competition.ai.tankoperator;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.action.ActionManager;
import za.co.entelect.competition.ai.action.ActionMoveTankTo;
import za.co.entelect.competition.swing.Mouse;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.*;

import java.util.Stack;

public class MouseControlledTankOperator implements TankOperator, PathAware {

  private static final Logger logger = Logger.getLogger(MouseControlledTankOperator.class);

  private int targetX = -1;
  private int targetY = -1;
  private ActionMoveTankTo action;

  public Stack<PathFinder.Node> getPath() {
    if (action != null) {
      return action.getPath();
    }
    return null;
  }

  @Override
  public void execute(GameState gameState, Tank tank) {
    Mouse mouse = Mouse.getInstance();
    if (mouse.buttonDown(1)) {
      targetX = (int)(mouse.getPosition().getX() / mouse.getZoomFactor());
      targetY = (int)(mouse.getPosition().getY() / mouse.getZoomFactor());
      logger.debug("New target set (" + targetX + "," + targetY + ")");
      if (action != null) {
        action.cancel();
      }
      action = new ActionMoveTankTo(tank, targetX, targetY);
      ActionManager.getInstance().scheduleAction(action);
    }
  }
}
