package za.co.entelect.competition.domain;

public class Bullet extends OwnedDirectedEntity {

  private Tank tank;

  public Bullet(int x, int y, GameState gameState, Player owner, Direction direction, Tank tank) {
    super(x, y, gameState, owner, direction);
    this.tank = tank;
  }

  public Tank getTank() {
    return tank;
  }

  @Override
  public BoundsAction getBoundsAction() {
    return BoundsAction.DIE;
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
