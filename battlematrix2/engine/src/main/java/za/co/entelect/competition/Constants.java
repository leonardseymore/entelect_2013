package za.co.entelect.competition;

import java.awt.*;

public class Constants {

  public static final int TANK_SIZE = 5;
  public static final int TANK_HALF_SIZE = 2;

  public static final long DEFAULT_TICK_INTERVAL = 3000;

  public static final String APP_TITLE = "<<b.a.t.t.l.e m.a.t.r.i.x>>";

  public static final Color COLOR_SWING_BLANK = Color.black;
  public static final Color COLOR_SWING_BOARD = Color.darkGray;
  public static final Color COLOR_SWING_BULLET = Color.white;
  public static final Color COLOR_SWING_TANK_YOU = Color.green;
  public static final Color COLOR_SWING_TANK_OPPONENT = Color.orange;
  public static final Color COLOR_SWING_WALL = Color.lightGray;

  public static final String COLOR_PPM3_BLANK = "0 0 0";
  public static final String COLOR_PPM3_BASE = "255 255 0";
  public static final String COLOR_PPM3_BULLET = "255 255 255";
  public static final String COLOR_PPM3_TANK_YOU = "0 0 255";
  public static final String COLOR_PPM3_TANK_OPPONENT = COLOR_SWING_TANK_OPPONENT.getRed() + " " + COLOR_SWING_TANK_OPPONENT.getGreen() + " " + COLOR_SWING_TANK_OPPONENT.getBlue();
  public static final String COLOR_PPM3_WALL = "120 120 120";

  public static final float TANK_INFLUENCE = 1f;
  public static final float BULLET_INFLUENCE = 10f;
  public static final int FIRE_RANGE = 16;
  public static final int FRIENDLY_FIRE_RANGE = 20;
  public static final int MIN_TIME_TO_FACE_AND_DESTROY_INLINE_BULLET = 4;
  public static final int PATH_FIND_TANK_DIST = 10;
}

