package za.co.entelect.competition.domain;

import za.co.entelect.competition.Constants;

public class Tank extends Entity {

  private Player owner;
  private Direction direction;
  private TankOperator tankOperator;

  public Tank(String id, Player owner, Direction direction, TankOperator tankOperator) {
    this(id, 0, 0, owner, direction, tankOperator);
  }

  public Tank(String id, int x, int y, Player owner, Direction direction, TankOperator tankOperator) {
    super(id, x, y, Constants.TANK_SIZE, Constants.TANK_SIZE, GameElement.TANK);
    this.owner = owner;
    this.direction = direction;
    this.tankOperator = tankOperator;
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

  public TankOperator getTankOperator() {
    return tankOperator;
  }

  public void setTankOperator(TankOperator tankOperator) {
    this.tankOperator = tankOperator;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Rectangle getRect() {
    return new Rectangle(y - Constants.TANK_HALF_SIZE, x + Constants.TANK_HALF_SIZE, y + Constants.TANK_HALF_SIZE, x - Constants.TANK_HALF_SIZE);
  }

  public boolean isYourTank() {
    return owner == Player.YOU;
  }

  public int[] turretPos() {
    int[] bulletPos = new int[2];

    switch (direction) {
      case UP:
        bulletPos[0] = this.x;
        bulletPos[1] = this.y - Constants.TANK_HALF_SIZE;
        break;
      case RIGHT:
        bulletPos[0] = this.x + Constants.TANK_HALF_SIZE;
        bulletPos[1] = this.y;
        break;
      case DOWN:
        bulletPos[0] = this.x;
        bulletPos[1] = this.y + Constants.TANK_HALF_SIZE;
        break;
      case LEFT:
        bulletPos[0] = this.x - Constants.TANK_HALF_SIZE;
        bulletPos[1] = this.y;
        break;
    }
    return bulletPos;
  }

  @Override
  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
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
