package za.co.entelect.competition.domain;

public class Wall extends Entity {
  public Wall(int x, int y) {
    super(x, y);
  }

  @Override
  public Type getType() {
    return Type.WALL;
  }

  @Override
  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
  }
}
