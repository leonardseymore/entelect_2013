package za.co.entelect.competition.domain;

import za.co.entelect.competition.Constants;

public class Tank extends Entity {

  private Player owner;
  private Direction direction;

  public Tank(String id, Player owner, Direction direction) {
    this(id, 0, 0, owner, direction);
  }

  public Tank(String id, int x, int y, Player owner, Direction direction) {
    super(id, x, y, Constants.TANK_SIZE, Constants.TANK_SIZE, GameElement.TANK);
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

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Rectangle getRect() {
    return new Rectangle(y - Constants.TANK_HALF_SIZE, x + Constants.TANK_HALF_SIZE, y + Constants.TANK_HALF_SIZE, x - Constants.TANK_HALF_SIZE);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("id=").append(id);
    sb.append(", type=").append(gameElement);
    sb.append(", owner=").append(owner);
    sb.append(", direction=").append(direction);
    return sb.toString();
  }
}
