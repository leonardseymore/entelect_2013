package za.co.entelect.competition.bots.tankoperator;

import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankAction;
import za.co.entelect.competition.domain.TankOperator;
import za.co.entelect.competition.swing.Keyboard;

import java.awt.event.KeyEvent;

public class KeyboardControlledTankOperator implements TankOperator {

  public TankAction getAction(GameState gameState, Tank tank) {
    Keyboard keyboard = Keyboard.getInstance();
    if (keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {
      return TankAction.FIRE;
    }

    if (keyboard.keyDown(KeyEvent.VK_UP)) {
      return TankAction.UP;
    }

    if (keyboard.keyDown(KeyEvent.VK_RIGHT)) {
      return TankAction.RIGHT;
    }

    if (keyboard.keyDown(KeyEvent.VK_DOWN)) {
      return TankAction.DOWN;
    }

    if (keyboard.keyDown(KeyEvent.VK_LEFT)) {
      return TankAction.LEFT;
    }

    return TankAction.NONE;
  }
}
