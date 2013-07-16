package za.co.entelect.competition.ai.tankoperator;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.action.ActionManager;
import za.co.entelect.competition.ai.action.MoveTankToAction;
import za.co.entelect.competition.swing.Mouse;
import za.co.entelect.competition.ai.movement.SeekPath;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.*;

import java.util.Stack;

public class MouseControlledTankOperator implements TankOperator, PathAware {

  private static final Logger logger = Logger.getLogger(MouseControlledTankOperator.class);

  private int targetX = -1;
  private int targetY = -1;
  private MoveTankToAction action;

  public Stack<PathFinder.Node> getPath() {
    if (action != null) {
      return action.getPath();
    }
    return null;
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
      if (action != null) {
        action.cancel();
      }
      action = new MoveTankToAction(gameState, tank, targetX, targetY);
      ActionManager.getInstance().scheduleAction(action);
    }

    if (action != null) {
      return action.getTankAction();
    }
    return TankAction.NONE;
  }
}
