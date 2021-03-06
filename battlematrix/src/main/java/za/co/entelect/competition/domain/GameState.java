package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Constants;
import za.co.entelect.competition.ai.planning.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameState implements Cloneable {
  private static final Logger logger = Logger.getLogger(GameState.class);

  private boolean verbose = false;

  private Base yourBase;
  private Base opponentBase;

  private Map<TankId, Tank> tanks = new HashMap<>();
  private Collection<Bullet> bullets = new CopyOnWriteArrayList<>();
  private Collection<Wall> walls = new CopyOnWriteArrayList<>();

  protected int w;
  protected int h;

  protected Entity map[][];

  private long[][][] zobristHash;
  private long hash;
  private Random random = new Random();

  public GameState(int w, int h) {
    this.w = w;
    this.h = h;
    map = new Entity[w][h];
    zobristHash = new long[w][h][Constants.ZOBRIST_NUM_STATES];
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        for (int k = 0; k < Constants.ZOBRIST_NUM_STATES; k++) {
          zobristHash[i][j][k] = random.nextLong();
        }
      }
    }
  }

  public long[][][] getZobristHash() {
    return zobristHash;
  }

  public boolean canTankBeMovedTo(Tank tank, int x, int y) {
    for (int j = y - Tank.TANK_HALF_SIZE; j <= y + Tank.TANK_HALF_SIZE; j++) {
      for (int i = x - Tank.TANK_HALF_SIZE; i <= x + Tank.TANK_HALF_SIZE; i++) {
        if (!isInbounds(i, j)) {
          return false;
        }

        Entity entity = getEntityAt(i, j);
        if (entity != null && !tank.equals(entity) && (entity.getType() == Entity.Type.WALL || entity.getType() == Entity.Type.TANK)) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean isInbounds(int x, int y) {
    return x >= 0 && x < w && y >= 0 && y < h;
  }

  public Entity getEntityAt(int x, int y) {
    if (!isInbounds(x, y)) {
      return null;
    }
    return map[x][y];
  }

  public Entity setEntityAt(int x, int y, Entity entity) {
    if (!isInbounds(x, y)) {
      return null;
    }
    return map[x][y] = entity;
  }

  public void setOpponentBase(int x, int y) {
    opponentBase = new Base(x, y, Player.OPPONENT);
    map[opponentBase.getX()][opponentBase.getY()] = opponentBase;
    hash ^= zobristHash[opponentBase.getX()][opponentBase.getY()][opponentBase.getZobristIndex()];
  }

  public void setYourBase(int x, int y) {
    yourBase = new Base(x, y, Player.YOU);
    map[yourBase.getX()][yourBase.getY()] = yourBase;
    hash ^= zobristHash[yourBase.getX()][yourBase.getY()][yourBase.getZobristIndex()];
  }

  public Base getYourBase() {
    return yourBase;
  }

  public Base getOpponentBase() {
    return opponentBase;
  }

  public Tank getTank(TankId tankId) {
    return tanks.get(tankId);
  }

  public Collection<Tank> getEnemyTanks(Tank tank) {
    Collection<Tank> tanks = new ArrayList<>();
    Tank t;
    if (tank.isYourTank()) {
      t = getTank(TankId.O1);
      if (t != null) {
        tanks.add(t);
      }
      t = getTank(TankId.O2);
      if (t != null) {
        tanks.add(t);
      }
    } else {
      t = getTank(TankId.Y1);
      if (t != null) {
        tanks.add(t);
      }
      t = getTank(TankId.Y2);
      if (t != null) {
        tanks.add(t);
      }
    }
    return tanks;
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

  public void remove(Base base) {
    if (base == yourBase) {
      logger.debug("Your base was destroyed, you loose!");
      map[yourBase.getX()][yourBase.getY()] = null;
      hash = zobristHash[yourBase.getX()][yourBase.getY()][yourBase.getZobristIndex()] ^ hash;
      yourBase = null;
    } else {
      logger.debug("Opponent base was destroyed, you WIN!");
      map[opponentBase.getX()][opponentBase.getY()] = null;
      hash = zobristHash[opponentBase.getX()][opponentBase.getY()][opponentBase.getZobristIndex()] ^ hash;
      opponentBase = null;
    }
  }

  public void add(Bullet bullet) {
    bullets.add(bullet);
    map[bullet.getX()][bullet.getY()] = bullet;
    hash ^= zobristHash[bullet.getX()][bullet.getY()][bullet.getZobristIndex()];
    if (verbose) {
      logger.debug("Added bullet [" + bullet + "]");
    }
  }

  public void remove(Bullet bullet) {
    bullets.remove(bullet);
    bullet.getTank().setCanFire(true);
    map[bullet.getPrevX()][bullet.getPrevY()] = null;
    int zobristIndex = bullet.getZobristIndex();
    hash = zobristHash[bullet.getPrevX()][bullet.getPrevY()][zobristIndex] ^ hash;
    if (verbose) {
      logger.debug("Removed bullet [" + bullet + "]");
    }
  }

  public void add(Tank tank) {
    tanks.put(tank.getTankId(), tank);
    tank.setGameState(this);
    Rectangle r = tank.getRect();
    for (int x = r.getLeft(); x <= r.getRight(); x++) {
      for (int y = r.getTop(); y <= r.getBottom(); y++) {
        map[x][y] = tank;
        hash ^= zobristHash[x][y][tank.getZobristIndex()];
      }
    }
    if (verbose) {
      logger.debug("Added tank [" + tank + "]");
    }
  }

  public void remove(Tank tank) {
    tanks.remove(tank.getTankId());
    Rectangle r = tank.getRect();
    for (int x = r.getLeft(); x <= r.getRight(); x++) {
      for (int y = r.getTop(); y <= r.getBottom(); y++) {
        map[x][y] = null;
        hash = zobristHash[x][y][tank.getZobristIndex()] ^ hash;
      }
    }
    if (verbose) {
      logger.debug("Removed tank [" + tank + "]");
    }
  }

  public void add(Wall wall) {
    walls.add(wall);
    map[wall.getX()][wall.getY()] = wall;
    hash ^= zobristHash[wall.getX()][wall.getY()][wall.getZobristIndex()];
    if (verbose) {
      logger.debug("Added wall [" + wall + "]");
    }
  }

  public void remove(Wall wall) {
    walls.remove(wall);
    map[wall.getX()][wall.getY()] = null;
    hash = zobristHash[wall.getX()][wall.getY()][wall.getZobristIndex()] ^ hash;
    if (verbose) {
      logger.debug("Removed wall [" + wall + "]");
    }
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
    update(true);
  }

  private void update(boolean callActionManager) {
    if (verbose) {
      logger.debug("Update called");
    }

    if (callActionManager) {
      for (Tank tank : tanks.values()) {
        tank.performAction(this);
      }
      ActionManager.getInstance().execute(this);
    }

    // 1) Bullets that have been fired are moved and collisions are checked for.
    // Looking at rules 1 and 2 bullets need to be moved twice per round
    for (int i = 0; i < 2; i++) {
      for (Bullet bullet : bullets) {
        bullet.move(false);
        if (bullet.getX() < 0 || bullet.getY() < 0 || bullet.getX() > w - 1 || bullet.getY() > h - 1) {
          remove(bullet);
        } else {
          if (!checkEntityCollision(bullet)) {
            map[bullet.getPrevX()][bullet.getPrevY()] = null;
            hash = zobristHash[bullet.getPrevX()][bullet.getPrevY()][bullet.getZobristIndex()] ^ hash;
            map[bullet.getX()][bullet.getY()] = bullet;
            hash ^= zobristHash[bullet.getX()][bullet.getY()][bullet.getZobristIndex()];
          }
        }
      }
    }

    // 2) Bullets and tankoperator are moved and collision are checked for.
    Iterator<Tank> tankIterator = tanks.values().iterator();
    while (tankIterator.hasNext()) {
      Tank tank = tankIterator.next();
      TankAction nextAction = tank.getNextAction();
      if (nextAction == TankAction.UP || nextAction == TankAction.RIGHT || nextAction == TankAction.DOWN || nextAction == TankAction.LEFT) {
        int oldX = tank.getX();
        int oldY = tank.getY();

        int x = tank.getX();
        int y = tank.getY();
        Directed.Direction direction = tank.getDirection();
        int oldZobristKeyIndex = tank.getZobristIndex();
        Rectangle oldRect = tank.getRect();
        Rectangle rect = tank.getRect();
        switch (tank.getNextAction()) {
          case UP:
            direction = Directed.Direction.UP;
            if (tank.canMoveInDirection(Directed.Direction.UP)) {
              y--;
              rect.traspose(0, -1);
            }
            break;
          case RIGHT:
            direction = Directed.Direction.RIGHT;
            if (tank.canMoveInDirection(Directed.Direction.RIGHT)) {
              x++;
              rect.traspose(1, 0);
            }

            break;
          case DOWN:
            direction = Directed.Direction.DOWN;
            if (tank.canMoveInDirection(Directed.Direction.DOWN)) {
              y++;
              rect.traspose(0, 1);
            }

            break;
          case LEFT:
            direction = Directed.Direction.LEFT;
            if (tank.canMoveInDirection(Directed.Direction.LEFT)) {
              x--;
              rect.traspose(-1, 0);
            }
            break;
        }

        Directed.Direction oldDirection = tank.getDirection();
        tank.setDirection(direction);
        boolean directionChanged = direction != oldDirection;
        int zobristKeyIndex = tank.getZobristIndex();
        if (rect.getLeft() >= 0 && rect.getRight() < w && rect.getTop() >= 0 && rect.getBottom() < h) {
          if (x != oldX || y != oldY) {
            tank.setX(x);
            tank.setY(y);
            if (checkEntityCollision(tank)) {
              if (tanks.containsKey(tank.getTankId())) {
                tank.setX(oldX);
                tank.setY(oldY);
              } else {
//                tankIterator.remove();
 //               remove(tank);
              }
            } else {
              // coarse hash out the old cells
              if (directionChanged) {
                for (int i = oldRect.getLeft(); i <= oldRect.getRight(); i++) {
                  for (int j = oldRect.getTop(); j <= oldRect.getBottom(); j++) {
                    hash = zobristHash[i][j][oldZobristKeyIndex] ^ hash;
                  }
                }
              }
              switch (direction) {
                case UP:
                  for (int i = rect.getLeft(); i <= rect.getRight(); i++) {
                    map[i][oldRect.getBottom()] = null;
                    map[i][rect.getTop()] = tank;
                    if (!directionChanged) {
                      hash = zobristHash[i][oldRect.getBottom()][oldZobristKeyIndex] ^ hash;
                      hash ^= zobristHash[i][rect.getTop()][zobristKeyIndex];
                    }
                  }
                  break;
                case RIGHT:
                  for (int j = rect.getTop(); j <= rect.getBottom(); j++) {
                    map[oldRect.getLeft()][j] = null;
                    map[rect.getRight()][j] = tank;
                    if (!directionChanged) {
                      hash = zobristHash[oldRect.getLeft()][j][oldZobristKeyIndex] ^ hash;
                      hash ^= zobristHash[rect.getRight()][j][zobristKeyIndex];
                    }
                  }
                  break;
                case DOWN:
                  for (int i = rect.getLeft(); i <= rect.getRight(); i++) {
                    map[i][oldRect.getTop()] = null;
                    map[i][rect.getBottom()] = tank;
                    if (!directionChanged) {
                      hash = zobristHash[i][oldRect.getTop()][oldZobristKeyIndex] ^ hash;
                      hash ^= zobristHash[i][rect.getBottom()][zobristKeyIndex];
                    }
                  }
                  break;
                case LEFT:
                  for (int j = rect.getTop(); j <= rect.getBottom(); j++) {
                    map[oldRect.getRight()][j] = null;
                    map[rect.getLeft()][j] = tank;
                    if (!directionChanged) {
                      hash = zobristHash[oldRect.getRight()][j][oldZobristKeyIndex] ^ hash;
                      hash ^= zobristHash[rect.getLeft()][j][zobristKeyIndex];
                    }
                  }
                  break;
              }
              // coarse hash in the new cells
              if (directionChanged) {
                for (int i = rect.getLeft(); i <= rect.getRight(); i++) {
                  for (int j = rect.getTop(); j <= rect.getBottom(); j++) {
                    hash ^= zobristHash[i][j][zobristKeyIndex];
                  }
                }
              }
            }
          } else if (directionChanged) {
            for (int i = rect.getLeft(); i <= rect.getRight(); i++) {
              for (int j = rect.getTop(); j <= rect.getBottom(); j++) {
                hash = zobristHash[i][j][oldZobristKeyIndex] ^ hash;
                hash ^= zobristHash[i][j][zobristKeyIndex];
              }
            }
          }
        }
      }
    }

    // 3) All tankoperator in the firing state are fired and their bullets are added to the field.
    // 4) Collisions are checked for.
    Collection<Tank> tanksToRemove = new HashSet<>();
    for (Tank tank : tanks.values()) {
      if (tanksToRemove.contains(tank)) {
        continue;
      }
      if (tank.isCanFire() && tank.getNextAction() == TankAction.FIRE) {
        int[] bulletPos = tank.turretPos();
        Bullet bullet = new Bullet(bulletPos[0], bulletPos[1], tank.getOwner(), tank.getDirection(), tank);
        bullet.move(true);
        if (isInbounds(bullet.getX(), bullet.getY())) {
          Entity e = getEntityAt(bullet.getX(), bullet.getY());
          if (e != null && e != bullet) {
            switch (e.getType()) {
              case BASE:
                remove((Base)e);
                break;
              case BULLET:
                remove((Bullet)e);
                break;
              case TANK:
                tanksToRemove.add((Tank)e);
                break;
              case WALL:
                destroyWalls(bullet, (Wall)e);
                break;
            }
          } else {
            add(bullet);
            tank.setCanFire(false);
          }
        }
      }
      tank.setNextAction(TankAction.NONE);
    }

    for (Tank tank : tanksToRemove) {
      remove(tank);
    }
  }

  private boolean checkEntityCollision(Bullet bullet) {
    Entity e = getEntityAt(bullet.getX(), bullet.getY());
    if (e != null && e != bullet) {
      return handleCollision(bullet, e);
    }
    return false;
  }

  private boolean checkEntityCollision(Tank tank) {
    Rectangle rect = tank.getRect();
    switch (tank.getDirection()) {
      case UP:
        for (int i = rect.getLeft(); i <= rect.getRight(); i++) {
          Entity e = getEntityAt(i, rect.getTop());
          if (e != null && e != tank) {
            return handleCollision(tank, e);
          }
        }
        break;
      case RIGHT:
        for (int j = rect.getTop(); j <= rect.getBottom(); j++) {
          Entity e = getEntityAt(rect.getRight(), j);
          if (e != null && e != tank) {
            return handleCollision(tank, e);
          }
        }
        break;
      case DOWN:
        for (int i = rect.getLeft(); i <= rect.getRight(); i++) {
          Entity e = getEntityAt(i, rect.getBottom());
          if (e != null && e != tank) {
            return handleCollision(tank, e);
          }
        }
        break;
      case LEFT:
        for (int j = rect.getTop(); j <= rect.getBottom(); j++) {
          Entity e = getEntityAt(rect.getLeft(), j);
          if (e != null && e != tank) {
            return handleCollision(tank, e);
          }
        }
        break;
    }
    return false;
  }

  private boolean handleCollision(Entity s, Entity t) {
    if (verbose) {
      logger.debug("Collision " + s.getType() + " [" + s + "] --> " + t.getType() + " [" + t + "]");
    }

    if (s.getType() == Entity.Type.BULLET && t.getType() == Entity.Type.BASE) {
      Bullet bullet = (Bullet) s;
      Base base = (Base) t;
      remove(bullet);
      remove(base);
      if (verbose) {
        logger.debug("Base [" + t + "] destroyed by [" + bullet.getTank().getTankId() + "]");
      }
    } else if (s.getType() == Entity.Type.TANK && t.getType() == Entity.Type.BASE) {
      Tank tank = (Tank) s;
      Base base = (Base) t;
      remove(tank);
      remove(base);
      if (verbose) {
        logger.debug("TANKED Base [" + t + "] destroyed by [" + tank.getTankId() + "]");
      }
    } else if (s.getType() == Entity.Type.BULLET && t.getType() == Entity.Type.TANK) {
      Bullet bullet = (Bullet) s;
      Tank tank = (Tank) t;
      remove(bullet);
      remove(tank);
      if (verbose) {
        logger.debug("Tank [" + tank.getTankId() + "] destroyed by [" + bullet.getTank().getTankId() + "]");
      }
    } else if (s.getType() == Entity.Type.TANK && t.getType() == Entity.Type.BULLET) {
      Tank tank = (Tank) s;
      Bullet bullet = (Bullet) t;
      remove(tank);
      remove(bullet);
      if (verbose) {
        logger.debug("Tank [" + tank.getTankId() + "] destroyed by [" + bullet.getTank().getTankId() + "]");
      }
    } else if (s.getType() == Entity.Type.BULLET && t.getType() == Entity.Type.BULLET) {
      Bullet bulletS = (Bullet) s;
      Bullet bulletT = (Bullet) s;
      remove(bulletS);
      remove(bulletT);
      if (verbose) {
        logger.debug("Bullet [" + bulletS.getTank().getTankId() + "] destroyed by [" + bulletT.getTank().getTankId() + "]");
      }
    } else if (s.getType() == Entity.Type.BULLET && t.getType() == Entity.Type.WALL) {
      Bullet bullet = (Bullet) s;
      remove(bullet);
      Wall wall = (Wall) t;
      destroyWalls(bullet, wall);
      if (verbose) {
        logger.debug("Wall [" + wall.getX() + ":" + wall.getY() + "] destroyed by [" + bullet.getTank().getTankId() + "]");
      }
    }
    return true;
  }

  private void destroyWalls(Bullet b, Wall w) {
    remove(w);
    if (verbose) {
      logger.debug("Wall [" + w + "] destroyed by [" + b + "]");
    }

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
              buffer.append(((Tank) e).getTankId());
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
    buffer.append("255\n");
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
              buffer.append(((Tank) e).isYourTank() ? Constants.COLOR_PPM3_TANK_YOU : Constants.COLOR_PPM3_TANK_OPPONENT);
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

  public int getDiscontentment() {
    if (yourBase == null) {
      return Integer.MAX_VALUE;
    }

    if (opponentBase == null) {
      return 0;
    }

    int discontentment = 0;
    if (getTank(TankId.Y1) == null) {
      discontentment += Integer.MAX_VALUE / 4;
    }

    if (getTank(TankId.Y2) == null) {
      discontentment += Integer.MAX_VALUE / 4;
    }

    if (getTank(TankId.O1) != null) {
      discontentment += Integer.MAX_VALUE / 2;
    }

    if (getTank(TankId.O1) != null) {
      discontentment += Integer.MAX_VALUE / 2;
    }
    return discontentment;
  }

  public GameState clone() {
    try {
      GameState clone = (GameState) super.clone();
      clone.map = new Entity[w][h];
      // TODO: use faster copy method
      for (int i = 0; i < w; i++) {
        for (int j = 0; j < h; j++) {
          clone.map[i][j] = map[i][j];
        }
      }
      clone.tanks = new HashMap<>();
      for (Tank tank : tanks.values()) {
        Tank tankClone = tank.clone();
        tankClone.setGameState(clone);
        clone.tanks.put(tankClone.getTankId(), tankClone);
      }
      clone.walls = new CopyOnWriteArrayList<>();
      for (Wall wall : walls) {
        clone.walls.add(wall);
      }
      clone.bullets = new CopyOnWriteArrayList<>();
      for (Bullet bullet : bullets) {
        clone.bullets.add(bullet.clone());
      }
      return clone;
    } catch (CloneNotSupportedException ex) {
      throw new RuntimeException("Clone not supported", ex);
    }
  }

  public long hash() {
    return hash;
  }

  public long hashFirstPrincipal() {
    long hashFp = 0;

    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        Entity e = getEntityAt(i, j);
        if (e != null) {
          hashFp ^= zobristHash[i][j][e.getZobristIndex()];
        }
      }
    }
    return hashFp;
  }

  public void applyAction(Action nextAction) {
    while (!nextAction.isComplete() && nextAction.getExpiryTime() > 0) {
      nextAction.execute(this);
      update(false);
    }
  }

  private class EntityIterator implements Iterator<Entity> {

    private Entity.Type currentCollectionType = Entity.Type.BASE;
    private Iterator<? extends Entity> currentCollection;

    EntityIterator() {
      Collection<Base> bases = new ArrayList<>();
      bases.add(yourBase);
      bases.add(opponentBase);
      currentCollection = bases.iterator();
    }

    @Override
    public boolean hasNext() {
      if (currentCollection.hasNext()) {
        return true;
      } else {
        if (currentCollectionType == Entity.Type.BASE) {
          currentCollection = walls.iterator();
          currentCollectionType = Entity.Type.WALL;
        } else if (currentCollectionType == Entity.Type.WALL) {
          currentCollection = tanks.values().iterator();
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
