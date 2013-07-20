package za.co.entelect.competition.domain;

public class Base extends Entity {

  private Player owner;

  public Base(String id, Player owner) {
    this(id, 0, 0, owner);
  }

  public Base(String id, int x, int y, Player owner) {
    super(id, x, y, GameElement.BASE);
    this.owner = owner;
  }

  public Player getOwner() {
    return owner;
  }

  public void setOwner(Player owner) {
    this.owner = owner;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("id=").append(id);
    sb.append(", type=").append(gameElement);
    sb.append(", owner=").append(owner);
    return sb.toString();
  }
}
