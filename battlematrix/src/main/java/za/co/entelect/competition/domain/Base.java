package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;

public class Base extends Entity implements Owned {

  private static final Logger logger = Logger.getLogger(Base.class);

  protected Player owner;

  public Base(int x, int y, GameState gameState, Player owner) {
    super(x, y, gameState);
    this.owner = owner;
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  @Override
  public TYPE getType() {
    return TYPE.BASE;
  }

  @Override
  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
  }
}
