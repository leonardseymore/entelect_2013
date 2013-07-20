package za.co.entelect.competition.domain;

public class Bullet extends Entity {

  private Tank tank;
  private Direction direction;

  public Bullet(String id, Tank tank, Direction direction) {
    this(id, 0, 0, tank, direction);
  }

  public Bullet(String id, int x, int y, Tank tank, Direction direction) {
    super(id, x, y, GameElement.BULLET);
    this.tank = tank;
    this.direction = direction;
  }

  public Tank getTank() {
    return tank;
  }

  public void setTank(Tank tank) {
    this.tank = tank;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
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
    sb.append(", tank=").append(tank.getId());
    sb.append(", direction=").append(direction);
    return sb.toString();
  }
}
