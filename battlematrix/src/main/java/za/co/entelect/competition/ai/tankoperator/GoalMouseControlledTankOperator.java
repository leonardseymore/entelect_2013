package za.co.entelect.competition.ai.tankoperator;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.action.*;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankOperator;
import za.co.entelect.competition.swing.Mouse;

public class GoalMouseControlledTankOperator implements TankOperator {

  private static final Logger logger = Logger.getLogger(GoalMouseControlledTankOperator.class);

  private int targetX = -1;
  private int targetY = -1;
  private Action action;

  @Override
  public void execute(GameState gameState, Tank tank) {
    Mouse mouse = Mouse.getInstance();
    mouse.poll();
    if (mouse.buttonDown(1)) {
      targetX = (int)(mouse.getPosition().getX() / mouse.getZoomFactor());
      targetY = (int)(mouse.getPosition().getY() / mouse.getZoomFactor());
      logger.debug("New target set (" + targetX + "," + targetY + ")");
      if (action != null) {
        action.cancel();
      }
      Goal goal = new GoalMoveTo(tank, targetX, targetY);
      long start = System.currentTimeMillis();
      action = GoapIda.planAction(gameState, goal, new GoalMoveToHeuristic(tank.getTankId(), targetX, targetY), 5);
      logger.debug("Plan took [" + (System.currentTimeMillis() - start) + "ms]");
      if (action != null) {
        ActionManager.getInstance().scheduleAction(action);
      }
    }
  }
}
