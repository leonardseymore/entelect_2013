package za.co.entelect.competition.bots.tanks;

import org.apache.log4j.Logger;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.swing.Keyboard;

import java.awt.event.KeyEvent;

public class KeyboardControlledTank extends Tank {

  private static final Logger logger = Logger.getLogger(KeyboardControlledTank.class);

  public KeyboardControlledTank(TankId id) {
    super(id);
  }

  @Override
  public TankAction doGetAction(GameState gameState) {
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
