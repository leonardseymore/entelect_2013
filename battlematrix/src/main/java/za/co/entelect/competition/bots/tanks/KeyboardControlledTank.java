package za.co.entelect.competition.bots.tanks;

import org.apache.log4j.Logger;
import za.co.entelect.competition.swing.Keyboard;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Player;
import za.co.entelect.competition.domain.Tank;

import java.awt.event.KeyEvent;

public class KeyboardControlledTank extends Tank {

  private static final Logger logger = Logger.getLogger(KeyboardControlledTank.class);

  private Keyboard keyboard;

  public KeyboardControlledTank(String name, int x, int y, GameState gameState, Player owner, Direction direction) {
    super(name, x, y, gameState, owner, direction);
    this.keyboard = Keyboard.getInstance();
  }

  @Override
  public TankAction doGetAction() {
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
