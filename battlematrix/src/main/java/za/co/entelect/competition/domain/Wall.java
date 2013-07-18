package za.co.entelect.competition.domain;

import za.co.entelect.competition.Constants;

public class Wall extends Entity implements Cloneable {
  public Wall(int x, int y) {
    super(x, y);
  }

  @Override
  public int getZobristIndex() {
    return Constants.ZOBRIST_WALL;
  }

  @Override
  public Type getType() {
    return Type.WALL;
  }

  @Override
  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
  }

  public Wall clone() {
    try {
      return (Wall) super.clone();
    } catch (CloneNotSupportedException ex) {
      throw new RuntimeException("Clone not supported", ex);
    }
  }
}
