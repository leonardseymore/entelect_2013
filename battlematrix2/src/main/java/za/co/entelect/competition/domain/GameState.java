package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;

import java.util.*;

public class GameState {

  public static final Logger logger = Logger.getLogger(GameState.class);

  private int w;
  private int h;
  private Base yourBase;
  private Base opponentBase;
  private Map<String, Tank> tanks = new HashMap<>();
  private Map<String, Bullet> bullets = new HashMap<>();
  private Map<String, Entity> idxId = new HashMap<>();
  private Map<String, Tank> yourTanks = new HashMap<>();
  private Map<String, Tank> opponentTanks = new HashMap<>();
  private Walls walls;
  private Entity[][] idxPos;
  private boolean gameOver = false;
  private InfluenceMap influenceMap;
  private DirichletDomains dirichletDomains;

  public GameState(int w, int h) {
    this.w = w;
    this.h = h;
    this.walls = new Walls(w, h);
    this.idxPos = new Entity[w][h];
    this.influenceMap = new InfluenceMap(this);
    this.dirichletDomains = new DirichletDomains(this);
  }

  public void setWalls(Walls walls) {
    this.walls = walls;
  }

  public void setYourBase(int x, int y) {
    yourBase = new Base(Ids.YOUR_BASE, x, y, Player.YOU);
    addEntity(yourBase);
  }

  public void setOpponentBase(int x, int y) {
    opponentBase = new Base(Ids.OPPONENT_BASE, x, y, Player.OPPONENT);
    addEntity(opponentBase);
  }

  public void addTank(Tank tank) {
    String id = tank.getId();
    tanks.put(id, tank);
    if (tank.getOwner() == Player.YOU) {
      yourTanks.put(id, tank);
    } else {
      opponentTanks.put(id, tank);
    }
    addEntity(tank);
  }

  public void removeTank(Tank tank) {
    String id = tank.getId();
    tanks.remove(id);
    if (tank.getOwner() == Player.YOU) {
      yourTanks.remove(id);
    } else {
      opponentTanks.remove(id);
    }
    removeEntity(tank);
  }

  public void addBullet(Bullet bullet) {
    bullets.put(bullet.getId(), bullet);
    addEntity(bullet);
  }

  public void removeBullet(Bullet bullet) {
    bullets.remove(bullet.getId());
    removeEntity(bullet);
    bullet.getTank().setCanFire(true);
  }

  public void moveBullet(Bullet bullet) {
    idxPos[bullet.getPrevX()][bullet.getPrevY()] = null;
    idxPos[bullet.getX()][bullet.getY()] = bullet;
  }

  public void moveTank(Tank tank, Rectangle oldRect) {
    for (int x = oldRect.getLeft(); x <= oldRect.getRight(); x++) {
      for (int y = oldRect.getTop(); y <= oldRect.getBottom(); y++) {
        idxPos[x][y] = null;
      }
    }

    Rectangle r = tank.getRect();
    for (int x = r.getLeft(); x <= r.getRight(); x++) {
      for (int y = r.getTop(); y <= r.getBottom(); y++) {
        idxPos[x][y] = tank;
      }
    }
  }

  public void removeBase(Base base) {
    if (base.isYourBase()) {
      yourBase = null;
      logger.debug("Game over, you lost!");
    } else {
      opponentBase = null;
      logger.debug("Game over, you WON!");
    }
    removeEntity(base);
    gameOver = true;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public Base getYourBase() {
    return yourBase;
  }

  public Base getPlayerBase(Player player) {
    return player == Player.YOU ? yourBase : opponentBase;
  }

  public Map<String, Tank> getYourTanks() {
    return yourTanks;
  }

  public Map<String, Tank> getPlayerTanks(Player player) {
    return player == Player.YOU ? yourTanks : opponentTanks;
  }

  public Map<String, Tank> getOpponentTanks() {
    return opponentTanks;
  }

  public Map<String, Tank> getEnemyTanks(Player player) {
    return player == Player.YOU ? opponentTanks : yourTanks;
  }

  public Base getOpponentBase() {
    return opponentBase;
  }

  public Base getEnemyBase(Player player) {
    return player == Player.YOU ? opponentBase : yourBase;
  }

  public Map<String, Tank> getTanks() {
    return tanks;
  }

  public Map<String, Bullet> getBullets() {
    return bullets;
  }

  public Walls getWalls() {
    return walls;
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
    if (!isInBounds(x, y)) {
      return null;
    }
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

  public int getH() {
    return h;
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

  public boolean canMoveInDirection(Tank tank, Direction dir) {
    int x = tank.getX();
    int y = tank.getY();
    switch (dir) {
      case UP:
        return canTankBeMovedTo(tank, x, y - 1);
      case RIGHT:
        return canTankBeMovedTo(tank, x + 1, y);
      case DOWN:
        return canTankBeMovedTo(tank, x, y + 1);
      case LEFT:
        return canTankBeMovedTo(tank, x - 1, y);
    }
    return false;
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

  public void updateInfluenceMap() {
    influenceMap.update();
  }

  public InfluenceMap getInfluenceMap() {
    return influenceMap;
  }

  public void regenerateDirichletDomains() {
    dirichletDomains.regenerate();
  }

  public DirichletDomains getDirichletDomains() {
    return dirichletDomains;
  }
}
