package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;

public class Base extends Entity implements Owned {

  private static final Logger logger = Logger.getLogger(Base.class);

  protected Player owner;

  public Base(int x, int y, Player owner) {
    super(x, y);
    this.owner = owner;
  }

  @Override
  public int getZobristIndex() {
    return owner == Player.YOU ? Constants.ZOBRIST_YBASE : Constants.ZOBRIST_OBASE;
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  @Override
  public Type getType() {
    return Type.BASE;
  }

  public boolean isYourBase() {
    return owner == Player.YOU;
  }

  @Override
  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
  }
}
