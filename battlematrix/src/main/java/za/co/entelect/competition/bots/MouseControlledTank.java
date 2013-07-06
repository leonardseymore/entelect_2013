package za.co.entelect.competition.bots;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Keyboard;
import za.co.entelect.competition.Mouse;
import za.co.entelect.competition.bots.movement.MoveToPoint;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Player;
import za.co.entelect.competition.domain.Tank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class MouseControlledTank extends Tank {

  private static final Logger logger = Logger.getLogger(MouseControlledTank.class);

  private Mouse mouse;

  private int[] targetPos = new int[2];

  public MouseControlledTank(int x, int y, GameState gameState, Player owner, Direction direction, Mouse mouse) {
    super(x, y, gameState, owner, direction);
    this.mouse = mouse;
  }

  @Override
  protected TankAction getAction() {
    if (mouse.buttonDown(2)) {
      return TankAction.FIRE;
    }

    if (mouse.buttonDown(1)) {
      targetPos[0] = (int)(mouse.getPosition().getX() / mouse.getZoomFactor());
      targetPos[1] = (int)(mouse.getPosition().getY() / mouse.getZoomFactor());
      logger.debug("New target set " + Arrays.toString(targetPos));
    }

    return MoveToPoint.getAction(this, targetPos);
  }
}
