package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;

public abstract class Entity {

  private static Logger logger = Logger.getLogger(Entity.class);

  private static final int DEFAULT_WIDTH = 1;
  private static final int DEFAULT_HEIGHT = 1;

  public static enum Type {
    BASE, BULLET, TANK, WALL
  }

  public static enum BoundsAction {
    BOUNCE, DIE
  }

  protected int x;
  protected int y;
  protected int w = DEFAULT_WIDTH;
  protected int h = DEFAULT_HEIGHT;

  protected GameState gameState;

  protected Entity(int x, int y, GameState gameState) {
    this.x = x;
    this.y = y;
    this.gameState = gameState;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getW() {
    return w;
  }

  public int getH() {
    return h;
  }

  public GameState getGameState() {
    return gameState;
  }

  public void update() {
    // default do nothing
  };

  public boolean isAt(int x, int y) {
    return x >= this.x && x < this.x + w
      && y >= this.y && y < this.y + h;
  }

  public BoundsAction getBoundsAction() {
    return BoundsAction.BOUNCE;
  }

  public abstract Type getType();

  public abstract void accept(GameElementVisitor visitor);

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Entity{");
    sb.append("x=").append(x);
    sb.append(", y=").append(y);
    sb.append(", w=").append(w);
    sb.append(", h=").append(h);
    sb.append(", type=").append(getType());
    sb.append('}');
    return sb.toString();
  }
}
