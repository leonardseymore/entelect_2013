package za.co.entelect.competition.domain;


import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameState {

  public static final long DEFAULT_TICK_INTERVAL = 3000; // 3 seconds
  private static final Logger logger = Logger.getLogger(GameState.class);

  private boolean verbose = false;

  private Player player1 = new Player("1");
  private Player player2 = new Player("2");

  private int w;
  private int h;
  private long tickInterval;
  private Timer timer;

  private Collection<Base> bases = new CopyOnWriteArrayList<>();
  private Collection<Bullet> bullets = new CopyOnWriteArrayList<>();
  private Collection<Tank> tanks = new CopyOnWriteArrayList<>();
  private Collection<Wall> walls = new CopyOnWriteArrayList<>();

  private Collection<GameStateListener> listeners = new ArrayList<>();

  private MapNode[][] map;

  public GameState(int w, int h, long tickInterval) {
    this.w = w;
    this.h = h;
    this.tickInterval = tickInterval;
  }

  public Player getPlayer1() {
    return player1;
  }

  public Player getPlayer2() {
    return player2;
  }

  public boolean isVerbose() {
    return verbose;
  }

  public void setVerbose(boolean verbose) {
    this.verbose = verbose;
  }

  public synchronized void addEventListener(GameStateListener listener) {
    listeners.add(listener);
  }

  public synchronized void removeEventListener(GameStateListener listener) {
    listeners.remove(listener);
  }

  public MapNode[][] getMap() {
    return map;
  }

  public void start() {
    map = new MapNode[w][h];
    for (int y = 0; y < h; y++) {
      nextCell:for (int x  = 0; x < w; x++) {
        Iterator<Entity> it = new EntityIterator();
        while (it.hasNext()) {
          Entity entity = it.next();
          if (entity.isAt(x, y)) {
            map[x][y] = new MapNode(entity);
            continue nextCell;
          }
        }
        map[x][y] = new MapNode();
      }
    }

    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        update();
      }
    }, 0, tickInterval);
  }

  public void stop() {
    timer.cancel();
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
    map[base.getX()][base.getY()].setEntity(null);
    logger.debug("Removed base [" + base + "]");
  }

  public void add(Bullet bullet) {
    bullets.add(bullet);
    logger.debug("Added bullet [" + bullet + "]");
  }

  public void remove(Bullet bullet) {
    bullets.remove(bullet);
    map[bullet.getPrevX()][bullet.getPrevY()].setEntity(null);
    logger.debug("Removed bullet [" + bullet + "]");
  }

  public void add(Tank tank) {
    tanks.add(tank);
    logger.debug("Added tank [" + tank + "]");
  }

  public void remove(Tank tank) {
    tanks.remove(tank);
    for (int y = tank.getPrevY(); y < tank.getPrevY() + tank.getH(); y++) {
      for (int x = tank.getPrevX(); x < tank.getPrevX() + tank.getW(); x++) {
        map[x][y].setEntity(null);
      }
    }
    logger.debug("Removed tank [" + tank + "]");
  }

  public void add(Wall wall) {
    walls.add(wall);
    logger.debug("Added wall [" + wall + "]");
  }

  public void remove(Wall wall) {
    walls.remove(wall);
    map[wall.getX()][wall.getY()].setEntity(null);
    logger.debug("Removed wall [" + wall + "]");
  }

  public Entity getEntityAt(int x, int y) {
    Iterator<Entity> it = new EntityIterator();
    while (it.hasNext()) {
      Entity entity = it.next();
      if (entity.isAt(x, y)) {
        return entity;
      }
    }
    return null;
  }

  public int getClearanceAt(int x, int y) {
    return getEntityAt(x, y) == null ? 1 : 0;
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

  public void update() {
    if (verbose) {
      logger.debug("Update called");
    }

    // Bullets that have been fired are moved and collisions are checked for.
    for (Bullet bullet : bullets) {
      map[bullet.getX()][bullet.getY()].setEntity(null);
      bullet.move();


      if (bullet.getX() < 0 || bullet.getY() < 0 || bullet.getX() > w - 1 || bullet.getY() > h - 1) {
        remove(bullet);
      } else {
        if (!checkEntityCollision(bullet)) {
          map[bullet.getX()][bullet.getY()].setEntity(bullet);
        }
      }
    }

    // Bullets and tanks are moved and collision are checked for.
    for (Tank tank : tanks) {
      int oldX = tank.getX();
      int oldY = tank.getY();
      tank.performAction();
      tank.move();
      if (tank.getX() < 0) {
        tank.setX(0);
      }

      if (tank.getX() + tank.getW() > w) {
        tank.setX(w - tank.getW());
      }

      if (tank.getY() < 0) {
        tank.setY(0);
      }

      if (tank.getY() + tank.getH() > h) {
        tank.setY(h - tank.getH());
      }

      if (checkEntityCollision(tank)) {
        if (tanks.contains(tank)) {
          tank.setX(oldX);
          tank.setY(oldY);
        } else {
          remove(tank);
        }
      } else {
        int newX = tank.getX();
        int newY = tank.getY();

        switch (tank.getLastAction()) {
          case UP:
            for (int x = tank.getX(); x < tank.getX() + tank.getW(); x++) {
              map[x][oldY + tank.getH() - 1].setEntity(null);
              map[x][newY].setEntity(tank);
            }
            break;
          case RIGHT:
            for (int y = tank.getY(); y < tank.getY() + tank.getH(); y++) {
              map[oldX][y].setEntity(null);
              map[newX + tank.getW() - 1][y].setEntity(tank);
            }
            break;
          case DOWN:
            for (int x = tank.getX(); x < tank.getX() + tank.getW(); x++) {
              map[x][oldY].setEntity(null);
              map[x][newY + tank.getH() - 1].setEntity(tank);
            }
            break;
          case LEFT:
            for (int y = tank.getY(); y < tank.getY() + tank.getH(); y++) {
              map[oldX + tank.getW() - 1][y].setEntity(null);
              map[newX][y].setEntity(tank);
            }
            break;
        }
      }
    }

    // All tanks in the firing state are fired and their bullets are added to the field.
    for (Tank tank : tanks) {
      if (tank.getLastAction() == Tank.TankAction.FIRE) {
        int [] bulletPos = tank.turretPos();
        Bullet bullet = new Bullet(bulletPos[0], bulletPos[1], this, tank.getOwner(), tank.getDirection(), tank);
        bullet.move();
        add(bullet);
      }
      checkEntityCollision(tank);
    }

    for (GameStateListener listener : listeners) {
      listener.updated();
    }
  }

  private boolean checkEntityCollision(Entity s) {
    Iterator<Entity> it = new EntityIterator();
    while (it.hasNext()) {
      Entity t = it.next();
      if (s != t) {
        if (s.getX() < (t.getX() + t.getW()) && (s.getX() + s.getW()) > t.getX() &&
          s.getY() < (t.getY() + t.getH()) && (s.getY() + s.getH()) > t.getY()) {
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
      destroyIfNeighborWall(w.getX() - 2, w.getY());
      destroyIfNeighborWall(w.getX() - 1, w.getY());
      destroyIfNeighborWall(w.getX() + 1, w.getY());
      destroyIfNeighborWall(w.getX() + 2, w.getY());
    } else {
      destroyIfNeighborWall(w.getX(), w.getY() - 2);
      destroyIfNeighborWall(w.getX(), w.getY() - 1);
      destroyIfNeighborWall(w.getX(), w.getY() + 1);
      destroyIfNeighborWall(w.getX(), w.getY() + 2);
    }
  }

  private void destroyIfNeighborWall(int x, int y) {
    Entity e = getEntityAt(x, y);
    if (e != null && e.getType() == Entity.Type.WALL) {
      remove((Wall)e);
    }
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

  public static class MapNode {
    private Entity entity;
    private int clearance = 1;

    private MapNode() {
      setEntity(null);
    }

    public MapNode(Entity entity) {
      setEntity(entity);
    }

    public void setEntity(Entity entity) {
      clearance = entity == null ? 1 : 0;
      this.entity = entity;
    }

    public Entity getEntity() {
      return entity;
    }

    public void setClearance(int clearance) {
      this.clearance = clearance;
    }

    public int getClearance() {
      return clearance;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("MapNode{");
      sb.append("entity=").append(entity);
      sb.append(", clearance=").append(clearance);
      sb.append('}');
      return sb.toString();
    }
  }
}
