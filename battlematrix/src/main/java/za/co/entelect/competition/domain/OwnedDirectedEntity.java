package za.co.entelect.competition.domain;

public abstract class OwnedDirectedEntity extends Entity implements Owned, Directed {
  protected Player owner;
  protected Direction direction;

  public OwnedDirectedEntity(int x, int y, GameState gameState, Player owner, Direction direction) {
    super(x, y, gameState);
    this.owner = owner;
    this.direction = direction;
  }

  public Player getOwner() {
    return owner;
  }

  public Direction getDirection() {
    return direction;
  }

  @Override
  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  protected void updatePos() {
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

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("OwnedDirectedEntity{");
    sb.append("x=").append(x);
    sb.append(", y=").append(y);
    sb.append(", w=").append(w);
    sb.append(", h=").append(h);
    sb.append(", type=").append(getType());
    sb.append(", owner=").append(owner);
    sb.append(", direction=").append(direction);
    sb.append('}');
    return sb.toString();
  }
}
