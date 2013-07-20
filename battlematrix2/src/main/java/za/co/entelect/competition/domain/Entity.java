package za.co.entelect.competition.domain;

public abstract class Entity {
  protected String id;
  protected int x;
  protected int y;
  protected int w;
  protected int h;
  protected GameElement gameElement;

  protected Entity(String id, int x, int y, GameElement gameElement) {
    this(id, x, y, 1, 1, gameElement);
  }

  protected Entity(String id, int x, int y, int w, int h, GameElement gameElement) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.gameElement = gameElement;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public void setW(int w) {
    this.w = w;
  }

  public int getH() {
    return h;
  }

  public void setH(int h) {
    this.h = h;
  }

  public GameElement getGameElement() {
    return gameElement;
  }

  public void setGameElement(GameElement gameElement) {
    this.gameElement = gameElement;
  }

  public abstract void accept(GameElementVisitor visitor);

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
    return id.hashCode();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("id=").append(id);
    sb.append(", type=").append(gameElement);
    return sb.toString();
  }
}
