package za.co.entelect.competition.ai.tankoperator;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.pathfinding.PathFinderGoal;
import za.co.entelect.competition.ai.planning.*;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.swing.Mouse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.Stack;

public class MouseControlledTankOperator implements TankOperator {

  private static final Logger logger = Logger.getLogger(MouseControlledTankOperator.class);

  @Override
  public void execute(GameState gameState, Tank tank) {
    Mouse mouse = Mouse.getInstance();
    if (mouse.buttonDownOnce(1)) {
      int targetX = (int)(mouse.getPosition().getX() / mouse.getZoomFactor());
      int targetY = (int)(mouse.getPosition().getY() / mouse.getZoomFactor());
      logger.debug("New target set (" + targetX + "," + targetY + ")");

      Collection<Action> actions = new ArrayList<>();
      tank.getBlackboard().setTargetLocation(new Point(targetX, targetY));
      actions.add(new ActionMoveTo(gameState, tank));
      Goal goal = new GoalMoveTo(tank.getId(), targetX, targetY);
      Plan plan = PathFinderGoal.getPlan(gameState.toGameModel(), goal, actions);
      tank.setPlan(plan);
    }
  }
}
