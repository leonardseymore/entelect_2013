package za.co.entelect.competition;

import za.co.entelect.competition.domain.Entity;
import za.co.entelect.competition.domain.Tank;

import java.awt.*;

public class Util {

  public static int manhattanDist(int startX, int startY, int endX, int endY) {
    return Math.abs(startX - endX) + Math.abs(startY - endY);
  }

  public static Color getColor(Entity entity) {
    switch (entity.getType()) {
      case BASE:
        return Constants.COLOR_SWING_BASE;
      case BULLET:
        return Constants.COLOR_SWING_BULLET;
      case TANK:
        Tank tank = (Tank)entity;
        Color tankColor = tank.getOwner() == tank.getGameState().getYou() ? Constants.COLOR_SWING_TANK_PLAYER1 : Constants.COLOR_SWING_TANK_PLAYER2;
        return tankColor;
      case WALL:
        return Constants.COLOR_SWING_WALL;
    }
    return Constants.COLOR_SWING_BLANK;
  }
}
