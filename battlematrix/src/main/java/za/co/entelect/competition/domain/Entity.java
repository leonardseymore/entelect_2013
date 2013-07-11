package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Entity implements Trackable {

  private static Logger logger = Logger.getLogger(Entity.class);

  private static final int DEFAULT_WIDTH = 1;
  private static final int DEFAULT_HEIGHT = 1;

  private static final AtomicLong ID_GEN = new AtomicLong();

  public static enum Type {
    BASE, BULLET, TANK, WALL
  }

  private long id;
  protected int x;
  protected int y;
  protected int w = DEFAULT_WIDTH;
  protected int h = DEFAULT_HEIGHT;

  protected GameState gameState;

  protected Entity(int x, int y, GameState gameState) {
    this.id = ID_GEN.incrementAndGet();
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

  public long getId() {
    return id;
  }

  public GameState getGameState() {
    return gameState;
  }

  public boolean isAt(int x, int y) {
    return x >= this.x && x < this.x + w
      && y >= this.y && y < this.y + h;
  }

  public abstract Type getType();

  public abstract void accept(GameElementVisitor visitor);

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Entity{");
    sb.append("id=").append(id);
    sb.append("x=").append(x);
    sb.append(", y=").append(y);
    sb.append(", w=").append(w);
    sb.append(", h=").append(h);
    sb.append(", type=").append(getType());
    sb.append('}');
    return sb.toString();
  }
}
