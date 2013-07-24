package za.co.entelect.competition.ai.tankoperator;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.pathfinding.PathFinderGoal;
import za.co.entelect.competition.ai.planning.*;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.swing.Keyboard;
import za.co.entelect.competition.swing.Mouse;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;

public class KeyboardTankOperator implements TankOperator {

  private static final Logger logger = Logger.getLogger(KeyboardTankOperator.class);

  @Override
  public void execute(GameState gameState, Tank tank) {
    Keyboard keyboard = Keyboard.getInstance();
    if (keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {
      tank.setNextAction(TankAction.FIRE);
      return;
    }

    if (keyboard.keyDown(KeyEvent.VK_UP)) {
      tank.setNextAction(TankAction.UP);
      return;
    }

    if (keyboard.keyDown(KeyEvent.VK_RIGHT)) {
      tank.setNextAction(TankAction.RIGHT);
      return;
    }

    if (keyboard.keyDown(KeyEvent.VK_DOWN)) {
      tank.setNextAction(TankAction.DOWN);
      return;
    }

    if (keyboard.keyDown(KeyEvent.VK_LEFT)) {
      tank.setNextAction(TankAction.LEFT);
      return;
    }
  }
}
