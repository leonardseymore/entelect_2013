package za.co.entelect.competition.domain;

import za.co.entelect.competition.Constants;

public class Bullet extends OwnedDirectedEntity implements Cloneable {

  private Tank tank;

  private int prevX;
  private int prevY;

  public Bullet(int x, int y, Player owner, Direction direction, Tank tank) {
    super(x, y, owner, direction);
    this.tank = tank;
  }

  public int getPrevX() {
    return prevX;
  }

  public int getPrevY() {
    return prevY;
  }

  public Tank getTank() {
    return tank;
  }

  @Override
  public int getZobristIndex() {
    switch (direction) {
      case UP:
        return Constants.ZOBRIST_BU_UP;
      case RIGHT:
        return Constants.ZOBRIST_BU_RIGHT;
      case DOWN:
        return Constants.ZOBRIST_BU_DOWN;
      case LEFT:
        return Constants.ZOBRIST_BU_LEFT;
    }
    return 0;
  }

  @Override
  public Type getType() {
    return Type.BULLET;
  }

  @Override
  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
  }

  public void move(boolean isNew) {
    if (!isNew) {
      prevX = x;
      prevY = y;
    }

    switch (direction) {
      case UP:
        y--;
        break;
      case RIGHT:
        x++;
        break;
      case DOWN:
        y++;
        break;
      case LEFT:
        x--;
        break;
    }
  }

  public Bullet clone() {
    try {
      return (Bullet) super.clone();
    } catch (CloneNotSupportedException ex) {
      throw new RuntimeException("Clone not supported", ex);
    }
  }
}
