package za.co.entelect.competition.domain;

public class Point implements Trackable {

  private int x;
  private int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public int getW() {
    return 0;
  }

  @Override
  public int getH() {
    return 0;
  }
}
