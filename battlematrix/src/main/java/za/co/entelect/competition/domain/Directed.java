package za.co.entelect.competition.domain;

public interface Directed {
  public static enum Direction {
    UP, RIGHT, DOWN, LEFT
  }

  Direction getDirection();

  void setDirection(Direction direction);
}
