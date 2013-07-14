package za.co.entelect.competition.domain;

public abstract class Tank extends OwnedDirectedEntity {

  public static final int TANK_SIZE = 5;
  public static final int TANK_HALF_SIZE = 2;

  private int prevX;
  private int prevY;

  private TankAction lastAction;
  private TankId tankId;

  private boolean canFire = true;

  public Tank(TankId tankId) {
    this(0, 0, null, Direction.UP);
    this.tankId = tankId;
    if (tankId == TankId.Y1 || tankId == TankId.Y2) {
      owner = Player.YOU;
    } else {
      owner = Player.OPPONENT;
    }
    w = TANK_SIZE;
    h = TANK_SIZE;
  }

  public Tank(int x, int y, Player owner, Direction direction) {
    super(x, y, owner, direction);
  }

  public int getPrevX() {
    return prevX;
  }

  public int getPrevY() {
    return prevY;
  }

  public TankId getTankId() {
    return tankId;
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

  public TankAction performAction(GameState gameState) {
    lastAction = doGetAction(gameState);
    return lastAction;
  }

  public boolean isYourTank() {
    return owner == Player.YOU;
  }

  public boolean isOpponentTank() {
    return owner == Player.OPPONENT;
  }

  public boolean isCanFire() {
    return canFire;
  }

  public void setCanFire(boolean canFire) {
    this.canFire = canFire;
  }

  protected abstract TankAction doGetAction(GameState gameState);

  public Rectangle getRect() {
    return new Rectangle(y - TANK_HALF_SIZE, x + TANK_HALF_SIZE, y + TANK_HALF_SIZE, x - TANK_HALF_SIZE);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Tank{");
    sb.append("tankId='").append(tankId).append('\'');
    sb.append("entity='").append(super.toString()).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
