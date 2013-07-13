package za.co.entelect.competition.domain;

public abstract class OwnedDirectedEntity extends Entity implements Owned, Directed {
  protected Player owner;
  protected Direction direction;

  public OwnedDirectedEntity(int x, int y, Player owner, Direction direction) {
    super(x, y);
    this.owner = owner;
    this.direction = direction;
  }

  public Player getOwner() {
    return owner;
  }

  public void setOwner(Player owner) {
    this.owner = owner;
  }

  public Direction getDirection() {
    return direction;
  }

  @Override
  public void setDirection(Direction direction) {
    this.direction = direction;
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
