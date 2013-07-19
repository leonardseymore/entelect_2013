package za.co.entelect.competition.domain;

import za.co.entelect.competition.Constants;

public class Tank extends OwnedDirectedEntity implements Cloneable {

  public static final int TANK_SIZE = 5;
  public static final int TANK_HALF_SIZE = 2;

  private TankAction nextAction = TankAction.NONE;
  private TankId tankId;

  private boolean canFire = true;

  private TankOperator tankOperator;

  private GameState gameState;
  private int prevX;
  private int prevY;

  public Tank(TankId tankId, TankOperator tankOperator) {
    this(0, 0, null, Direction.UP);
    this.tankId = tankId;
    this.tankOperator = tankOperator;
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

  @Override
  public void setX(int x) {
    prevX = this.x;
    this.x = x;
  }

  @Override
  public void setY(int y) {
    prevY = this.y;
    this.y = y;
  }

  public TankOperator getTankOperator() {
    return tankOperator;
  }

  public void setTankOperator(TankOperator tankOperator) {
    this.tankOperator = tankOperator;
  }

  public GameState getGameState() {
    return gameState;
  }

  public void setGameState(GameState gameState) {
    this.gameState = gameState;
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
  public int getZobristIndex() {
    if (isYourTank()) {
      switch (direction) {
        case UP:
          return Constants.ZOBRIST_YT_UP;
        case RIGHT:
          return Constants.ZOBRIST_YT_RIGHT;
        case DOWN:
          return Constants.ZOBRIST_YT_DOWN;
        case LEFT:
          return Constants.ZOBRIST_YT_LEFT;
      }
    } else {
      switch (direction) {
        case UP:
          return Constants.ZOBRIST_OT_UP;
        case RIGHT:
          return Constants.ZOBRIST_OT_RIGHT;
        case DOWN:
          return Constants.ZOBRIST_OT_DOWN;
        case LEFT:
          return Constants.ZOBRIST_OT_LEFT;
      }
    }
    return 0;
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

  public boolean canMoveInDirection(Direction dir) {
    switch (dir) {
      case UP:
        return gameState.canTankBeMovedTo(this, x, y - 1);
      case RIGHT:
        return gameState.canTankBeMovedTo(this, x + 1, y);
      case DOWN:
        return gameState.canTankBeMovedTo(this, x, y + 1);
      case LEFT:
        return gameState.canTankBeMovedTo(this, x - 1, y);
    }
    return false;
  }

  public TankAction getNextAction() {
    return nextAction;
  }

  public void setNextAction(TankAction nextAction) {
    this.nextAction = nextAction;
  }

  public void performAction(GameState gameState) {
    tankOperator.execute(gameState, this);
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

  public Rectangle getRect() {
    return new Rectangle(y - TANK_HALF_SIZE, x + TANK_HALF_SIZE, y + TANK_HALF_SIZE, x - TANK_HALF_SIZE);
  }

  public Tank clone() {
    try {
      return (Tank) super.clone();
    } catch (CloneNotSupportedException ex) {
      throw new RuntimeException("Clone not supported", ex);
    }
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
