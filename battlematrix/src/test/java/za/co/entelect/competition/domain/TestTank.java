package za.co.entelect.competition.domain;

public class TestTank extends Tank {
  private Tank.TankAction action;

  public TestTank(String name, int x, int y, GameState gameState, Player owner, Directed.Direction direction, Tank.TankAction action) {
    super(name, x, y, gameState, owner, direction);
    this.action = action;
  }

  public void setAction(Tank.TankAction action) {
    this.action = action;
  }

  @Override
  protected Tank.TankAction doGetAction() {
    return action;
  }
}
