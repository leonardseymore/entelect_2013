package za.co.entelect.competition.domain;

public class Bullet extends OwnedDirectedEntity {

  public Bullet(int x, int y, GameState gameState, Player owner, Direction direction) {
    super(x, y, gameState, owner, direction);
  }

  @Override
  public TYPE getType() {
    return TYPE.BULLET;
  }

  @Override
  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void update() {
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
