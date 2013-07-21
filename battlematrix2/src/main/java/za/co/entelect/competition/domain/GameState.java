package za.co.entelect.competition.domain;

import za.co.entelect.competition.Constants;

import java.util.HashMap;
import java.util.Map;

public class GameState {

  private int w;
  private int h;
  private Base yourBase;
  private Base opponentBase;
  private Map<String, Tank> tanks = new HashMap<>();
  private Map<String, Bullet> bullets = new HashMap<>();
  private Map<String, Entity> idxId = new HashMap<>();
  private Walls walls;
  private Entity[][] idxPos;

  public GameState(int w, int h) {
    this.w = w;
    this.h = h;
    this.walls = new Walls(w, h);
    this.idxPos = new Entity[w][h];
  }

  public void update() {

  }

  public void setWalls(Walls walls) {
    this.walls = walls;
  }

  public void setYourBase(int x, int y) {
    yourBase = new Base(IdGenerator.YOUR_BASE, x, y, Player.YOU);
    addEntity(yourBase);
  }

  public void setOpponentBase(int x, int y) {
    opponentBase = new Base(IdGenerator.OPPONENT_BASE, x, y, Player.OPPONENT);
    addEntity(opponentBase);
  }

  public void addTank(Tank tank) {
    tanks.put(tank.getId(), tank);
    addEntity(tank);
  }

  public void removeTank(Tank tank) {
    tanks.remove(tank.getId());
    removeEntity(tank);
  }

  public void addBullet(Bullet bullet) {
    bullets.put(bullet.getId(), bullet);
    addEntity(bullet);
  }

  public void removeBullet(Bullet bullet) {
    tanks.remove(bullet.getId());
    removeEntity(bullet);
  }

  private void addEntity(Entity entity) {
    idxId.put(entity.getId(), entity);
    if (entity.getGameElement() == GameElement.TANK) {
      Tank tank = (Tank)entity;
      Rectangle r = tank.getRect();
      for (int x = r.getLeft(); x <= r.getRight(); x++) {
        for (int y = r.getTop(); y <= r.getBottom(); y++) {
          idxPos[x][y] = tank;
        }
      }
    } else {
      idxPos[entity.getX()][entity.getY()] = entity;
    }
  }

  private void removeEntity(Entity entity) {
    idxId.remove(entity.getId());
    if (entity.getGameElement() == GameElement.TANK) {
      Tank tank = (Tank)entity;
      Rectangle r = tank.getRect();
      for (int x = r.getLeft(); x <= r.getRight(); x++) {
        for (int y = r.getTop(); y <= r.getBottom(); y++) {
          idxPos[x][y] = null;
        }
      }
    } else {
      idxPos[entity.getX()][entity.getY()] = null;
    }
  }

  public GameElement getElementAt(int x, int y) {
    Entity entity = idxPos[x][y];
    if (entity != null) {
      return entity.getGameElement();
    }
    if (walls.hasWall(x, y)) {
      return GameElement.WALL;
    }
    return null;
  }

  public Entity getEntityAt(int x, int y) {
    return idxPos[x][y];
  }

  public Entity getEntity(String id) {
    return idxId.get(id);
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

  public boolean canTankBeMovedTo(Tank tank, int x, int y) {
    for (int j = y - Constants.TANK_HALF_SIZE; j <= y + Constants.TANK_HALF_SIZE; j++) {
      for (int i = x - Constants.TANK_HALF_SIZE; i <= x + Constants.TANK_HALF_SIZE; i++) {
        if (!isInBounds(i, j)) {
          return false;
        }

        if (walls.hasWall(i, j)) {
          return false;
        }
        Entity entity = getEntityAt(i, j);
        if (entity != null
          && !tank.equals(entity)
          && entity.getGameElement() == GameElement.TANK) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean isInBounds(int x, int y) {
    return walls.isInBounds(x, y);
  }

  public void accept(GameElementVisitor visitor) {
    visitor.visit(this);
    walls.accept(visitor);
    for (Entity entity : idxId.values()) {
      entity.accept(visitor);
    }
  }
}