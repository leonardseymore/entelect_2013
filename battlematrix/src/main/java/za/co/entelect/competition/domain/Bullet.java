package za.co.entelect.competition.domain;

public class Bullet extends OwnedDirectedEntity {

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
  public Type getType() {
    return Type.BULLET;
  }

  @Override
  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
  }

  public void move() {
    prevX = x;
    prevY = y;

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
}
