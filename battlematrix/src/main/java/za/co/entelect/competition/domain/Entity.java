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

  protected Entity(int x, int y) {
    this.id = ID_GEN.incrementAndGet();
    this.x = x;
    this.y = y;
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

  public abstract Type getType();

  public abstract void accept(GameElementVisitor visitor);

  public Rectangle getRect() {
    return new Rectangle(y, x, y, x);
  }

  public abstract int getZobristIndex();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Entity)) return false;

    Entity entity = (Entity) o;

    if (id != entity.id) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }

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
