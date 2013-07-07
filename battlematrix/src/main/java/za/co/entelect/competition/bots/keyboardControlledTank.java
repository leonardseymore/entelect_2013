package za.co.entelect.competition.bots;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Keyboard;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Player;
import za.co.entelect.competition.domain.Tank;

import java.awt.event.KeyEvent;

public class KeyboardControlledTank extends Tank {

  private static final Logger logger = Logger.getLogger(KeyboardControlledTank.class);

  private Keyboard keyboard;

  public KeyboardControlledTank(String name, int x, int y, GameState gameState, Player owner, Direction direction, Keyboard keyboard) {
    super(name, x, y, gameState, owner, direction);
    this.keyboard = keyboard;
  }

  @Override
  public TankAction getAction() {
    if (keyboard.keyDown(KeyEvent.VK_SPACE)) {
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
