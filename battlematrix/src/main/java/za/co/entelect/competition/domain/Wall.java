package za.co.entelect.competition.domain;

public class Wall extends Entity {
  public Wall(int x, int y, GameState gameState) {
    super(x, y, gameState);
  }

  @Override
  public TYPE getType() {
    return TYPE.WALL;
  }

  @Override
  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
  }
}
