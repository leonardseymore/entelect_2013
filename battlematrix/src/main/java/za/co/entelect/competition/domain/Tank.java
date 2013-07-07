package za.co.entelect.competition.domain;

public abstract class Tank extends OwnedDirectedEntity {

  public static enum TankAction {
    UP, RIGHT, DOWN, LEFT, FIRE, NONE
  }

  private static final int TANK_WIDTH = 5;
  private static final int TANK_HALF_WIDTH = 2;
  private static final int TANK_HEIGHT = 5;
  private static final int TANK_HALF_HEIGHT = 2;

  private String name;

  private int prevX;
  private int prevY;

  public Tank(String name, int x, int y, GameState gameState, Player owner, Direction direction) {
    super(x, y, gameState, owner, direction);
    this.w = TANK_WIDTH;
    this.h = TANK_HEIGHT;
    this.name = name;
  }

  public int getPrevX() {
    return prevX;
  }

  public int getPrevY() {
    return prevY;
  }

  public int[] turretPos() {
    int[] bulletPos = new int[2];

    switch (direction) {
      case UP:
        bulletPos[0] = this.x + TANK_HALF_WIDTH;
        bulletPos[1] = this.y;
        break;
      case RIGHT:
        bulletPos[0] = this.x + TANK_WIDTH - 1;
        bulletPos[1] = this.y + TANK_HALF_HEIGHT;
        break;
      case DOWN:
        bulletPos[0] = this.x + TANK_HALF_WIDTH;
        bulletPos[1] = this.y + TANK_HEIGHT - 1;
        break;
      case LEFT:
        bulletPos[0] = this.x;
        bulletPos[1] = this.y + TANK_HALF_HEIGHT;
        break;
    }
    return bulletPos;
  }

  public String getName() {
    return name;
  }

  @Override
  public Direction getDirection() {
    return direction;
  }

  @Override
  public Type getType() {
    return Type.TANK;
  }

  @Override
  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
  }

  public void move() {
    prevX = x;
    prevY = y;
    switch (getAction()) {
      case UP:
        direction = Direction.UP;
        y--;
        break;
      case RIGHT:
        direction = Direction.RIGHT;
        x++;
        break;
      case DOWN:
        direction = Direction.DOWN;
        y++;
        break;
      case LEFT:
        direction = Direction.LEFT;
        x--;
        break;
    }
  }

  public abstract TankAction getAction();

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Tank{");
    sb.append("name='").append(name).append('\'');
    sb.append("entity='").append(super.toString()).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
