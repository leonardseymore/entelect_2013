package za.co.entelect.competition.ai.tankoperator;

import za.co.entelect.competition.ai.planning.ActionFireTank;
import za.co.entelect.competition.ai.planning.ActionManager;
import za.co.entelect.competition.ai.planning.ActionMoveTank;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.swing.Keyboard;

import java.awt.event.KeyEvent;

public class KeyboardControlledTankOperator implements TankOperator {

  public void execute(GameState gameState, Tank tank) {
    Keyboard keyboard = Keyboard.getInstance();
    if (keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {
      ActionManager.getInstance().scheduleAction(new ActionFireTank(tank));
      return;
    }

    if (keyboard.keyDown(KeyEvent.VK_UP)) {
      ActionManager.getInstance().scheduleAction(new ActionMoveTank(tank, Directed.Direction.UP));
      return;
    }

    if (keyboard.keyDown(KeyEvent.VK_RIGHT)) {
      ActionManager.getInstance().scheduleAction(new ActionMoveTank(tank, Directed.Direction.RIGHT));
      return;
    }

    if (keyboard.keyDown(KeyEvent.VK_DOWN)) {
      ActionManager.getInstance().scheduleAction(new ActionMoveTank(tank, Directed.Direction.DOWN));
      return;
    }

    if (keyboard.keyDown(KeyEvent.VK_LEFT)) {
      ActionManager.getInstance().scheduleAction(new ActionMoveTank(tank, Directed.Direction.LEFT));
      return;
    }
  }
}
