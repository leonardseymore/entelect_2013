package za.co.entelect.competition;

import za.co.entelect.competition.domain.*;

import java.awt.*;

public class Util {

  public static int manhattanDist(int startX, int startY, int endX, int endY) {
    return Math.abs(startX - endX) + Math.abs(startY - endY);
  }

  public static Color getColor(Entity entity) {
    switch (entity.getGameElement()) {
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

  public static String toPpm(GameState gameState) {
    int w = gameState.getW();
    int h = gameState.getH();
    StringBuilder buffer = new StringBuilder();
    buffer.append("P3\n");
    buffer.append(w + " " + h + "\n");
    buffer.append("255\n");
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        GameElement e = gameState.getElementAt(x, y);
        if (e == null) {
          buffer.append(Constants.COLOR_PPM3_BLANK);
        } else {
          switch (e) {
            case BASE:
              buffer.append(Constants.COLOR_PPM3_BASE);
              break;
            case BULLET:
              buffer.append(Constants.COLOR_PPM3_BULLET);
              break;
            case TANK:
              Tank tank = (Tank)gameState.getEntityAt(x, y);
              buffer.append(tank.isYourTank() ? Constants.COLOR_PPM3_TANK_YOU : Constants.COLOR_PPM3_TANK_OPPONENT);
              break;
            case WALL:
              buffer.append(Constants.COLOR_PPM3_WALL);
              break;
          }
        }
        buffer.append(" ");
      }
      buffer.append("\n");
    }
    return buffer.toString();
  }
}
