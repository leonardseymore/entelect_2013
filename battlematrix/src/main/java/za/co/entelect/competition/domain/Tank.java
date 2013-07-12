package za.co.entelect.competition.domain;

public abstract class Tank extends OwnedDirectedEntity {

  public static enum TankAction {
    UP, RIGHT, DOWN, LEFT, FIRE, NONE
  }

  public static final int TANK_SIZE = 5;
  public static final int TANK_HALF_SIZE = 2;

  private String name;

  private int prevX;
  private int prevY;

  private TankAction lastAction;

  public Tank(String name, int x, int y, GameState gameState, Player owner, Direction direction) {
    super(x, y, gameState, owner, direction);
    this.prevX = x;
    this.prevY = y;
    this.w = TANK_SIZE;
    this.h = TANK_SIZE;
    this.name = name;
  }

  public Tank(String name, GameState gameState, Player owner, Direction direction) {
    super(0, 0, gameState, owner, direction);
    this.prevX = x;
    this.prevY = y;
    this.w = TANK_SIZE;
    this.h = TANK_SIZE;
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
        bulletPos[0] = this.x;
        bulletPos[1] = this.y - TANK_HALF_SIZE;
        break;
      case RIGHT:
        bulletPos[0] = this.x + TANK_HALF_SIZE;
        bulletPos[1] = this.y;
        break;
      case DOWN:
        bulletPos[0] = this.x;
        bulletPos[1] = this.y + TANK_HALF_SIZE;
        break;
      case LEFT:
        bulletPos[0] = this.x - TANK_HALF_SIZE;
        bulletPos[1] = this.y;
        break;
    }
    return bulletPos;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
    switch (lastAction) {
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

  public TankAction getLastAction() {
    return lastAction;
  }

  public TankAction performAction() {
    lastAction = doGetAction();
    return lastAction;
  }

  public boolean canMoveUp() {
    int newY = y - 1;
    return gameState.canTankBeMovedTo(this, x, newY);
  }

  public boolean canMoveRight() {
    int newX = x + 1;
    return gameState.canTankBeMovedTo(this, newX, y);
  }

  public boolean canMoveDown() {
    int newY = y + 1;
    return gameState.canTankBeMovedTo(this, x, newY);
  }

  public boolean canMoveLeft() {
    int newX = x - 1;
    return gameState.canTankBeMovedTo(this, newX, y);
  }

  protected abstract TankAction doGetAction();

  public Rectangle getBoundingRect() {
    return new Rectangle(y - TANK_HALF_SIZE, x + TANK_HALF_SIZE, y + TANK_HALF_SIZE, x - TANK_HALF_SIZE);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Tank{");
    sb.append("name='").append(name).append('\'');
    sb.append("entity='").append(super.toString()).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
