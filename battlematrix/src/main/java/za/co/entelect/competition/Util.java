package za.co.entelect.competition;

import za.co.entelect.competition.domain.Base;
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
        Base base = (Base)entity;
        return base.isYourBase() ? Constants.COLOR_SWING_TANK_YOU : Constants.COLOR_SWING_TANK_OPPONENT;
      case BULLET:
        return Constants.COLOR_SWING_BULLET;
      case TANK:
        Tank tank = (Tank)entity;
        Color tankColor = tank.isYourTank() ? Constants.COLOR_SWING_TANK_YOU : Constants.COLOR_SWING_TANK_OPPONENT;
        return tankColor;
      case WALL:
        return Constants.COLOR_SWING_WALL;
    }
    return Constants.COLOR_SWING_BLANK;
  }
}
