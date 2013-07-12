package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameState {
  private static final Logger logger = Logger.getLogger(GameState.class);

  private boolean verbose = false;

  private Player you = new Player("1");
  private Player opponent = new Player("2");

  private int w;
  private int h;

  private Collection<Base> bases = new CopyOnWriteArrayList<>();
  private Collection<Bullet> bullets = new CopyOnWriteArrayList<>();
  private Collection<Tank> tanks = new CopyOnWriteArrayList<>();
  private Collection<Wall> walls = new CopyOnWriteArrayList<>();

  private MapNode[][] map;

  private int yBaseX;
  private int yBaseY;
  private int oBaseX;
  private int oBaseY;

  public GameState(int w, int h) {
    this.w = w;
    this.h = h;
    map = new MapNode[w][h];
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        map[i][j] = new MapNode();
      }
    }
  }

  public Player getYou() {
    return you;
  }

  public Player getOpponent() {
    return opponent;
  }

  public void setOpponentBasePos(int x, int y) {
    oBaseX = x;
    oBaseY = y;
  }

  public void setYourBasePos(int x, int y) {
    yBaseX = x;
    yBaseY = y;
  }

  public Tank getTank(String tankName) {
    if (tankName == null) {
      return null;
    }

    for (Tank tank : tanks) {
      if (tank.getName().equals(tankName)) {
        return tank;
      }
    }
    return null;
  }

  public boolean isVerbose() {
    return verbose;
  }

  public void setVerbose(boolean verbose) {
    this.verbose = verbose;
  }

  public int getH() {
    return h;
  }

  public int getW() {
    return w;
  }

  public void add(Base base) {
    bases.add(base);
    logger.debug("Added base [" + base + "]");
  }

  public void remove(Base base) {
    bases.remove(base);
    logger.debug("Removed base [" + base + "]");
  }

  public void add(Bullet bullet) {
    bullets.add(bullet);
    logger.debug("Added bullet [" + bullet + "]");
  }

  public void remove(Bullet bullet) {
    bullets.remove(bullet);
    logger.debug("Removed bullet [" + bullet + "]");
  }

  public void add(Tank tank) {
    tanks.add(tank);
    logger.debug("Added tank [" + tank + "]");
  }

  public void remove(Tank tank) {
    tanks.remove(tank);
    logger.debug("Removed tank [" + tank + "]");
  }

  public void add(Wall wall) {
    if (getEntityAt(wall.getX(), wall.getY()) != null) {
      return;
    }
    walls.add(wall);
    logger.debug("Added wall [" + wall + "]");
  }

  public void remove(Wall wall) {
    walls.remove(wall);
    logger.debug("Removed wall [" + wall + "]");
  }

  public Entity getEntityAt(int x, int y) {
    return map[x][y].getEntity();
  }

  public MapNode getMapNode(int x, int y) {
    if (!isInbounds(x, y)) {
      return null;
    }
    return map[x][y];
  }

  public boolean isInbounds(int x, int y) {
    return x >= 0 && x < w && y >= 0 && y < h;
  }

  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
    Iterator<Entity> it = new EntityIterator();
    while (it.hasNext()) {
      Entity entity = it.next();
      entity.accept(visitor);
    }
  }

  public void init() {
    add(new Base(yBaseX, yBaseY, this, you));
    add(new Base(oBaseX, oBaseY, this, opponent));
  }

  private void updateTacticalMap() {
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        map[i][j].clear();
      }
    }

    EntityIterator it = new EntityIterator();
    while (it.hasNext()) {
      Entity entity = it.next();

      if (entity.getType() == Entity.Type.TANK) {
        Tank tank = (Tank)entity;
        Rectangle r = tank.getBoundingRect();
        for (int x = r.getLeft(); x <= r.getRight(); x++) {
          for (int y = r.getTop(); y <= r.getBottom(); y++) {
            map[x][y].setEntity(tank);
          }
        }
      } else {
        map[entity.getX()][entity.getY()].setEntity(entity);
      }
    }
  }

  public void update() {
    if (verbose) {
      logger.debug("Update called");
    }

    // 1) Bullets that have been fired are moved and collisions are checked for.
    // Looking at rules 1 and 2 bullets need to be moved twice per round
    for (int i = 0; i < 2; i++) {
      for (Bullet bullet : bullets) {
        bullet.move();

        if (bullet.getX() < 0 || bullet.getY() < 0 || bullet.getX() > w - 1 || bullet.getY() > h - 1) {
          remove(bullet);
        } else {
          checkEntityCollision(bullet);
        }
      }
    }
    updateTacticalMap();

    // 2) Bullets and tanks are moved and collision are checked for.
    for (Tank tank : tanks) {
      int oldX = tank.getX();
      int oldY = tank.getY();
      tank.performAction();
      tank.move();
      Rectangle rect = tank.getBoundingRect();
      if (rect.getLeft() < 0) {
        tank.setX(0);
      }

      if (rect.getRight() > w) {
        tank.setX(w - tank.getW());
      }

      if (rect.getTop() < 0) {
        tank.setY(0);
      }

      if (rect.getBottom() > h) {
        tank.setY(h - tank.getH());
      }

      if (checkEntityCollision(tank)) {
        if (tanks.contains(tank)) {
          tank.setX(oldX);
          tank.setY(oldY);
        } else {
          remove(tank);
        }
      }
    }

    // 3) All tanks in the firing state are fired and their bullets are added to the field.
    // 4) Collisions are checked for.
    for (Tank tank : tanks) {
      if (tank.getLastAction() == Tank.TankAction.FIRE) {
        int [] bulletPos = tank.turretPos();
        Bullet bullet = new Bullet(bulletPos[0], bulletPos[1], this, tank.getOwner(), tank.getDirection(), tank);
        bullet.move();
        if (isInbounds(bullet.getX(), bullet.getY())) {
          add(bullet);
          checkEntityCollision(bullet);
        }
      }
    }
  }

  private boolean checkEntityCollision(Entity s) {
    Iterator<Entity> it = new EntityIterator();
    while (it.hasNext()) {
      Entity t = it.next();
      if (s != t) {
        if (s.getBoundingRect().intersects(t.getBoundingRect())) {
          return handleCollision(s, t);
        }
      }
    }
    return false;
  }

  private boolean handleCollision(Entity s, Entity t) {
    if (verbose) {
      logger.debug("Collision " + s.getType() + " [" + s + "] --> " + t.getType() + " [" + t + "]");
    }

    if (s.getType() == Entity.Type.BULLET && t.getType() == Entity.Type.BASE) {
      Bullet bullet = (Bullet)s;
      Base base = (Base)t;
      remove(bullet);
      remove(base);
      logger.debug("Base [" + t + "] destroyed by [" + bullet.getTank().getName() + "]");
    } else if (s.getType() == Entity.Type.TANK && t.getType() == Entity.Type.BASE) {
      Tank tank = (Tank)s;
      Base base = (Base)t;
      remove(tank);
      remove(base);
      logger.debug("TANKED Base [" + t + "] destroyed by [" + tank.getName() + "]");
    } else if (s.getType() == Entity.Type.BULLET && t.getType() == Entity.Type.TANK) {
      Bullet bullet = (Bullet)s;
      Tank tank = (Tank)t;
      remove(bullet);
      remove(tank);
      logger.debug("Tank [" + tank.getName() + "] destroyed by [" + bullet.getTank().getName() + "]");
    } else if (s.getType() == Entity.Type.TANK && t.getType() == Entity.Type.BULLET) {
      Tank tank = (Tank)s;
      Bullet bullet = (Bullet)t;
      remove(tank);
      remove(bullet);
      logger.debug("Tank [" + tank.getName() + "] destroyed by [" + bullet.getTank().getName() + "]");
    } else if (s.getType() == Entity.Type.BULLET && t.getType() == Entity.Type.BULLET) {
      Bullet bulletS = (Bullet)s;
      Bullet bulletT = (Bullet)s;
      remove(bulletS);
      remove(bulletT);
      logger.debug("Bullet [" + bulletS.getTank().getName() + "] destroyed by [" + bulletT.getTank().getName() + "]");
    } else if (s.getType() == Entity.Type.BULLET && t.getType() == Entity.Type.WALL) {
      Bullet bullet = (Bullet) s;
      Wall wall = (Wall) t;
      destroyWalls(bullet, wall);
      logger.debug("Wall [" + wall.getX() + ":" + wall.getY() + "] destroyed by [" + bullet.getTank().getName() + "]");
    }
    return true;
  }

  private void destroyWalls(Bullet b, Wall w) {
    remove(b);
    remove(w);
    logger.debug("Wall [" + w + "] destroyed by [" + b + "]");

    if (b.getDirection() == Directed.Direction.UP || b.getDirection() == Directed.Direction.DOWN) {
      if (destroyIfNeighborWall(w.getX() - 1, w.getY())) {
        destroyIfNeighborWall(w.getX() - 2, w.getY());
      }
      if (destroyIfNeighborWall(w.getX() + 1, w.getY())) {
        destroyIfNeighborWall(w.getX() + 2, w.getY());
      }
    } else {
      if (destroyIfNeighborWall(w.getX(), w.getY() - 1)) {
        destroyIfNeighborWall(w.getX(), w.getY() - 2);
      }
      if (destroyIfNeighborWall(w.getX(), w.getY() + 1)) {
        destroyIfNeighborWall(w.getX(), w.getY() + 2);
      }
    }
  }

  private boolean destroyIfNeighborWall(int x, int y) {
    Entity e = getEntityAt(x, y);
    if (e != null && e.getType() == Entity.Type.WALL) {
      remove((Wall) e);
      return true;
    }
    return false;
  }

  public boolean canTankBeMovedTo(Tank tank, int x, int y) {
    for (int j = y - Tank.TANK_HALF_SIZE; j <= y + Tank.TANK_HALF_SIZE; j++) {
      for (int i = x - Tank.TANK_HALF_SIZE; i <= x + Tank.TANK_HALF_SIZE; i++) {
        if (!isInbounds(i, j)) {
          return false;
        }

        Entity entity = getEntityAt(i, j);
        if (entity != null && entity != tank) {
          return false;
        }
      }
    }
    return true;
  }

  public String toAscii() {
    StringBuilder buffer = new StringBuilder();
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        Entity e = getEntityAt(x, y);
        if (e == null) {
          buffer.append(Constants.SYMBOL_BLANK);
        } else {
          switch (e.getType()) {
            case BASE:
              buffer.append(Constants.SYMBOL_BASE);
              break;
            case BULLET:
              buffer.append(Constants.SYMBOL_BULLET);
              break;
            case TANK:
              buffer.append(((Tank) e).getOwner().getId());
              break;
            case WALL:
              buffer.append(Constants.SYMBOL_WALL);
              break;
          }
        }
      }
      buffer.append("\n");
    }

    return buffer.toString();
  }

  public String toPpm() {
    StringBuilder buffer = new StringBuilder();
    buffer.append("P3\n");
    buffer.append(w + " " + h + "\n");
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        Entity e = getEntityAt(x, y);
        if (e == null) {
          buffer.append(Constants.COLOR_PPM3_BLANK);
        } else {
          switch (e.getType()) {
            case BASE:
              buffer.append(Constants.COLOR_PPM3_BASE);
              break;
            case BULLET:
              buffer.append(Constants.COLOR_PPM3_BULLET);
              break;
            case TANK:
              buffer.append(Constants.COLOR_PPM3_TANK);
              break;
            case WALL:
              buffer.append(Constants.COLOR_PPM3_WALL);
              break;
          }
        }
        buffer.append(" ");
      }
      buffer.append("\n");
    }

    return buffer.toString();
  }

  private class EntityIterator implements Iterator<Entity> {

    private Entity.Type currentCollectionType = Entity.Type.BASE;
    private Iterator<? extends Entity> currentCollection = bases.iterator();

    @Override
    public boolean hasNext() {
      if (currentCollection.hasNext()) {
        return true;
      } else {
        if (currentCollectionType == Entity.Type.BASE) {
          currentCollection = walls.iterator();
          currentCollectionType = Entity.Type.WALL;
        } else if (currentCollectionType == Entity.Type.WALL) {
          currentCollection = tanks.iterator();
          currentCollectionType = Entity.Type.TANK;
        } else if (currentCollectionType == Entity.Type.TANK) {
          currentCollection = bullets.iterator();
          currentCollectionType = Entity.Type.BULLET;
        } else {
          return false;
        }
        return currentCollection.hasNext();
      }
    }

    @Override
    public Entity next() {
      return currentCollection.next();
    }

    @Override
    public void remove() {
      currentCollection.remove();
    }
  }
}
