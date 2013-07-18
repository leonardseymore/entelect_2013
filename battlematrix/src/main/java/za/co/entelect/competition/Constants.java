package za.co.entelect.competition;

import java.awt.*;

public class Constants {

  public static final long DEFAULT_TICK_INTERVAL = 3000;

  public static final String APP_TITLE = "<<b.a.t.t.l.e m.a.t.r.i.x>>";

  public static final String SYMBOL_BLANK = ".";
  public static final String SYMBOL_BASE = "$";
  public static final String SYMBOL_BULLET = "*";
  public static final String SYMBOL_TANK = "T";
  public static final String SYMBOL_WALL = "#";

  public static final Color COLOR_SWING_BLANK = Color.black;
  public static final Color COLOR_SWING_BOARD = Color.darkGray;
  public static final Color COLOR_SWING_BASE = Color.magenta;
  public static final Color COLOR_SWING_BULLET = Color.white;
  public static final Color COLOR_SWING_TANK_YOU = Color.blue;
  public static final Color COLOR_SWING_TANK_OPPONENT = Color.orange;
  public static final Color COLOR_SWING_WALL = Color.lightGray;

  public static final String COLOR_PPM3_BLANK = "0 0 0";
  public static final String COLOR_PPM3_BASE = "255 255 0";
  public static final String COLOR_PPM3_BULLET = "255 255 255";
  public static final String COLOR_PPM3_TANK_YOU = "0 0 255";
  public static final String COLOR_PPM3_TANK_OPPONENT = COLOR_SWING_TANK_OPPONENT.getRed() + " " + COLOR_SWING_TANK_OPPONENT.getGreen() + " " + COLOR_SWING_TANK_OPPONENT.getBlue();
  public static final String COLOR_PPM3_WALL = "120 120 120";

  public static final int ZOBRIST_WALL = 0;
  public static final int ZOBRIST_YT_UP = 1;
  public static final int ZOBRIST_YT_RIGHT = 2;
  public static final int ZOBRIST_YT_DOWN = 3;
  public static final int ZOBRIST_YT_LEFT = 4;
  public static final int ZOBRIST_OT_UP = 5;
  public static final int ZOBRIST_OT_RIGHT = 6;
  public static final int ZOBRIST_OT_DOWN = 7;
  public static final int ZOBRIST_OT_LEFT = 8;
  public static final int ZOBRIST_BU_UP = 9;
  public static final int ZOBRIST_BU_RIGHT = 10;
  public static final int ZOBRIST_BU_DOWN = 11;
  public static final int ZOBRIST_BU_LEFT = 12;
  public static final int ZOBRIST_YBASE = 13;
  public static final int ZOBRIST_OBASE = 14;
  public static final int ZOBRIST_NUM_STATES = 15;
}
