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

  private Collection<Entity> entities = new CopyOnWriteArrayList<>();

  private Collection<GameStateListener> listeners = new ArrayList<>();

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

  public synchronized void addEventListener(GameStateListener listener)  {
    listeners.add(listener);
  }

  public synchronized void removeEventListener(GameStateListener listener)   {
    listeners.remove(listener);
  }

  public void start() {
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
    addEntity(base);
    logger.debug("Added base [" + base + "]");
  }

  public void add(Bullet bullet) {
    addEntity(bullet);
    logger.debug("Added bullet [" + bullet + "]");
  }

  public void add(Tank tank) {
    addEntity(tank);
    logger.debug("Added tank [" + tank + "]");
  }

  public void add(Wall wall) {
    addEntity(wall);
    logger.debug("Added wall [" + wall + "]");
  }
  
  private void addEntity(Entity entity) {
    entities.add(entity);
  }

  private void removeEntity(Entity entity) {
    logger.debug("Removed entity [" + entity + "]");
    entities.remove(entity);
  }

  public Entity getEntityAt(int x, int y) {
    for (Entity entity : entities) {
      if (entity.isAt(x, y)) {
        return entity;
      }
    }

    return null;
  }

  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
    for (Entity entity : entities) {
      entity.accept(visitor);
    }
  }

  public void update() {
    if (verbose) {
      logger.debug("Update called");
    }

    for (Entity entity : entities) {
      entity.update();
      keepEntityInBounds(entity);

      int oldX = entity.getX();
      int oldY = entity.getY();
      checkEntityCollision(entity);
    }

    for (GameStateListener listener : listeners) {
      listener.updated();
    }
  }

  private void keepEntityInBounds(Entity entity) {
    Entity.BoundsAction boundsAction = entity.getBoundsAction();
    if (boundsAction == Entity.BoundsAction.BOUNCE) {
      if (entity.getX() < 0) {
        entity.setX(0);
      }

      if (entity.getX() + entity.getW() > w) {
        entity.setX(w - entity.getW());
      }

      if (entity.getY() < 0) {
        entity.setY(0);
      }

      if (entity.getY() + entity.getH() > h) {
        entity.setY(h - entity.getH());
      }
    } else if(boundsAction == Entity.BoundsAction.DIE) {
      if (entity.getX() < 0) {
        removeEntity(entity);
        return;
      }

      if (entity.getX() + entity.getW() > w) {
        removeEntity(entity);
        return;
      }

      if (entity.getY() < 0) {
        removeEntity(entity);
        return;
      }

      if (entity.getY() + entity.getH() > h) {
        removeEntity(entity);
        return;
      }
    }
  }

  private void checkEntityCollision(Entity entity) {

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
              buffer.append(((Tank)e).getOwner().getId());
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
}
