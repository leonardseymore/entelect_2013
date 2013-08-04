package za.co.entelect.competition.domain;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.ai.blackboard.Blackboard;

public class Tank extends Entity {

  private Blackboard blackboard;
  private Player owner;
  private Direction direction;
  private TankOperator tankOperator;
  private TankAction nextAction = TankAction.NONE;
  private boolean canFire = true;

  public Tank(String id, Player owner, Direction direction, TankOperator tankOperator) {
    this(id, 0, 0, owner, direction, tankOperator);
    this.blackboard = new Blackboard();
  }

  public Tank(String id, int x, int y, Player owner, Direction direction, TankOperator tankOperator) {
    super(id, x, y, Constants.TANK_SIZE, Constants.TANK_SIZE, GameElement.TANK);
    this.owner = owner;
    this.direction = direction;
    this.tankOperator = tankOperator;
  }

  public Blackboard getBlackboard() {
    return blackboard;
  }

  public void setBlackboard(Blackboard blackboard) {
    this.blackboard = blackboard;
  }

  public Player getOwner() {
    return owner;
  }

  public void setOwner(Player owner) {
    this.owner = owner;
  }

  public Direction getDirection() {
    return direction;
  }

  public TankOperator getTankOperator() {
    return tankOperator;
  }

  public void setTankOperator(TankOperator tankOperator) {
    this.tankOperator = tankOperator;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public Rectangle getRect() {
    return new Rectangle(y - Constants.TANK_HALF_SIZE, x + Constants.TANK_HALF_SIZE, y + Constants.TANK_HALF_SIZE, x - Constants.TANK_HALF_SIZE);
  }

  public boolean isYourTank() {
    return owner == Player.YOU;
  }

  public Point getTurretPos() {
    Point pos = new Point();
    switch (direction) {
      case UP:
        pos.setX(this.x);
        pos.setY(this.y - Constants.TANK_HALF_SIZE);
        break;
      case RIGHT:
        pos.setX(this.x + Constants.TANK_HALF_SIZE);
        pos.setY(this.y);
        break;
      case DOWN:
        pos.setX(this.x);
        pos.setY(this.y + Constants.TANK_HALF_SIZE);
        break;
      case LEFT:
        pos.setX(this.x - Constants.TANK_HALF_SIZE);
        pos.setY(this.y);
        break;
    }
    return pos;
  }

  public Point getInFrontPos() {
    Point pos = new Point();
    switch (direction) {
      case UP:
        pos.setX(this.x);
        pos.setY(this.y - Constants.TANK_HALF_SIZE - 1);
        break;
      case RIGHT:
        pos.setX(this.x + Constants.TANK_HALF_SIZE + 1);
        pos.setY(this.y);
        break;
      case DOWN:
        pos.setX(this.x);
        pos.setY(this.y + Constants.TANK_HALF_SIZE + 1);
        break;
      case LEFT:
        pos.setX(this.x - Constants.TANK_HALF_SIZE - 1);
        pos.setY(this.y);
        break;
    }
    return pos;
  }

  public TankAction getNextAction() {
    return nextAction;
  }

  public void setNextAction(TankAction nextAction) {
    this.nextAction = nextAction;
  }

  public boolean isCanFire() {
    return canFire;
  }

  public void setCanFire(boolean canFire) {
    this.canFire = canFire;
  }

  public Bullet createBullet() {
    Point bulletPos = getTurretPos();
    Bullet bullet = new Bullet("BULLET" + id, bulletPos.getX(), bulletPos.getY(), this, direction);
    int newX = bullet.getX();
    int newY = bullet.getY();
    switch (direction) {
      case UP:
        newY--;
        break;
      case RIGHT:
        newX++;
        break;
      case DOWN:
        newY++;
        break;
      case LEFT:
        newX--;
        break;
    }
    bullet.setX(newX);
    bullet.setY(newY);
    return bullet;
  }

  public void performAction(GameState gameState) {
    tankOperator.execute(gameState, this);
  }

  @Override
  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("id=").append(id);
    sb.append(", type=").append(gameElement);
    sb.append(", owner=").append(owner);
    sb.append(", direction=").append(direction);
    return sb.toString();
  }
}
