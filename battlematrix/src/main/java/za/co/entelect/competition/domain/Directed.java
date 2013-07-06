package za.co.entelect.competition.domain;

/**
 * Created with IntelliJ IDEA.
 * User: leonardseymore
 * Date: 2013/07/05
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Directed {
  public static enum Direction {
    UP, RIGHT, DOWN, LEFT
  }

  Direction getDirection();

  void setDirection(Direction direction);
}
