package za.co.entelect.competition;

public class Util {

  public static int manhattanDist(int startX, int startY, int endX, int endY) {
    return Math.abs(startX - endX) + Math.abs(startY - endY);
  }
}
