package za.co.entelect.competition.domain;

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

  public void setYourBase(int x, int y) {
    yourBase = new Base(IdGenerator.YOUR_BASE, x, y, Player.YOU);
    addEntity(yourBase);
  }

  public void setOpponentBase(int x, int y) {
    opponentBase = new Base(IdGenerator.YOUR_BASE, x, y, Player.YOU);
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
}
