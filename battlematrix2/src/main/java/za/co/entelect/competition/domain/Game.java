package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.planning.Plan;

import java.util.ArrayList;
import java.util.Collection;

public class Game {

  private static final Logger logger = Logger.getLogger(Game.class);

  private GameState gameState;

  public Game(GameState gameState) {
    this.gameState = gameState;
  }

  /**
   * Game rules
   * 1) Bullets that have been fired are moved and collisions are checked for.
   * Looking at rules 1 and 2 bullets need to be moved twice per round
   * 2) Bullets and tanks are moved and collision are checked for.
   * 3) All tankoperator in the firing state are fired and their bullets are added to the field.
   * 4) Collisions are checked for.
   */
  public void update() {
    if (gameState.isGameOver()) {
      return;
    }

    performAi();
    for (int i = 0; i < 2; i++) {
      updateBullets();
    }
    updateTanks();
    fireTanks();
  }

  private void performAi() {
    for (Tank tank : gameState.getTanks().values()) {
      tank.performAction(gameState);
      Plan plan = tank.getPlan();
      if (plan == null)  {
        continue;
      }
      if (plan.isComplete()) {
        tank.setPlan(null);
        logger.debug("Done with plan for tank [" + tank.getId() + "]");
      } else if (!plan.isValid(gameState)) {
        tank.setPlan(null);
        logger.debug("Invalid plan [" + plan + "] for tank [" + tank.getId() + "]");
      } else {
        plan.run(gameState);
      }
    }
  }

  private void updateBullets() {
    Collection<Bullet> bulletsToRemove = new ArrayList<>();
    for (Bullet bullet : gameState.getBullets().values()) {
      int x = bullet.getX();
      int y = bullet.getY();
      switch (bullet.getDirection()) {
        case UP:
          y--;
          break;
        case RIGHT:
          x++;
          break;
        case DOWN:
          y++;
          break;
        case LEFT:
          x--;
          break;
      }
      if (x < 0 || y < 0 || x > gameState.getW() - 1 || y > gameState.getH() - 1) {
        bulletsToRemove.add(bullet);
      } else {
        bullet.setX(x);
        bullet.setY(y);
        Collision collision = checkEntityCollision(bullet);
        if (collision != null) {
          bulletsToRemove.add(bullet);
          switch (collision.type) {
            case BulletBase:
              gameState.removeBase((Base) collision.target);
              break;
            case BulletBullet:
              bulletsToRemove.add((Bullet) collision.target);
              break;
            case BulletTank:
              gameState.removeTank((Tank) collision.target);
              break;
            case BulletWall:
              gameState.getWalls().destroyWalls(x, y, bullet.getDirection());
              break;
          }
        } else {
          gameState.moveBullet(bullet);
        }
      }
    }
    for (Bullet bullet : bulletsToRemove) {
      gameState.moveBullet(bullet);
      gameState.removeBullet(bullet);
    }
  }

  private void updateTanks() {
    Collection<Tank> tanksToRemove = new ArrayList<>();
    for (Tank tank : gameState.getTanks().values()) {
      TankAction nextAction = tank.getNextAction();
      if (nextAction == TankAction.UP || nextAction == TankAction.RIGHT || nextAction == TankAction.DOWN || nextAction == TankAction.LEFT) {
        switch (nextAction) {
          case UP:
            if (tank.getDirection() != Direction.UP) {
              tank.setDirection(Direction.UP);
              continue;
            }
            break;
          case RIGHT:
            if (tank.getDirection() != Direction.RIGHT) {
              tank.setDirection(Direction.RIGHT);
              continue;
            }
            break;
          case DOWN:
            if (tank.getDirection() != Direction.DOWN) {
              tank.setDirection(Direction.DOWN);
              continue;
            }
            break;
          case LEFT:
            if (tank.getDirection() != Direction.LEFT) {
              tank.setDirection(Direction.LEFT);
              continue;
            }
            break;
        }
        int oldX = tank.getX();
        int oldY = tank.getY();

        int x = tank.getX();
        int y = tank.getY();
        Direction direction = tank.getDirection();
        Rectangle rect = tank.getRect();
        switch (tank.getNextAction()) {
          case UP:
            direction = Direction.UP;
            if (canMoveInDirection(tank, Direction.UP)) {
              y--;
              rect.traspose(0, -1);
            }
            break;
          case RIGHT:
            direction = Direction.RIGHT;
            if (canMoveInDirection(tank, Direction.RIGHT)) {
              x++;
              rect.traspose(1, 0);
            }

            break;
          case DOWN:
            direction = Direction.DOWN;
            if (canMoveInDirection(tank, Direction.DOWN)) {
              y++;
              rect.traspose(0, 1);
            }

            break;
          case LEFT:
            direction = Direction.LEFT;
            if (canMoveInDirection(tank, Direction.LEFT)) {
              x--;
              rect.traspose(-1, 0);
            }
            break;
        }

        tank.setDirection(direction);
        if (rect.getLeft() >= 0 && rect.getRight() < gameState.getW()
          && rect.getTop() >= 0 && rect.getBottom() < gameState.getH()) {
          if (x != oldX || y != oldY) {
            Rectangle oldRect = tank.getRect();
            tank.setX(x);
            tank.setY(y);
            Collision collision = checkEntityCollision(tank);
            if (collision != null) {
              switch (collision.type) {
                case TankBase:
                  tanksToRemove.add(tank);
                  gameState.removeBase((Base) collision.target);
                  break;
                case TankBullet:
                  tanksToRemove.add(tank);
                  gameState.removeBullet((Bullet) collision.target);
                  break;
              }
            } else {
              gameState.moveTank(tank, oldRect);
            }
          }
        }
      }
    }
    for (Tank tank : tanksToRemove) {
      gameState.removeTank(tank);
    }
  }

  private void fireTanks() {
    Collection<Tank> tanksToRemove = new ArrayList<>();
    for (Tank tank : gameState.getTanks().values()) {
      if (tank.isCanFire() && tank.getNextAction() == TankAction.FIRE) {
        Bullet bullet = tank.createBullet();
        if (gameState.isInBounds(bullet.getX(), bullet.getY())) {
          GameElement e = gameState.getElementAt(bullet.getX(), bullet.getY());
          if (e != null) {
            Entity entity = gameState.getEntityAt(bullet.getX(), bullet.getY());
            switch (e) {
              case BASE:
                gameState.removeBase((Base) entity);
                break;
              case BULLET:
                gameState.removeBullet((Bullet) entity);
                break;
              case TANK:
                tanksToRemove.add((Tank) entity);
                break;
              case WALL:
                gameState.getWalls().destroyWalls(bullet.getX(), bullet.getY(), bullet.getDirection());
                break;
            }
          } else {
            gameState.addBullet(bullet);
            tank.setCanFire(false);
          }
        }
      }
      tank.setNextAction(TankAction.NONE);
    }
    for (Tank tank : tanksToRemove) {
      gameState.removeTank(tank);
    }
  }

  private Collision checkEntityCollision(Bullet bullet) {
    GameElement gameElement = gameState.getElementAt(bullet.getX(), bullet.getY());
    if (gameElement == GameElement.WALL) {
      return new Collision(bullet, null, CollisionType.BulletWall);
    }

    Entity e = gameState.getEntityAt(bullet.getX(), bullet.getY());
    if (e != null && e != bullet) {
      return handleCollision(bullet, e);
    }
    return null;
  }

  private Collision checkEntityCollision(Tank tank) {
    Rectangle rect = tank.getRect();
    switch (tank.getDirection()) {
      case UP:
        for (int i = rect.getLeft(); i <= rect.getRight(); i++) {
          Entity e = gameState.getEntityAt(i, rect.getTop());
          if (e != null && e != tank) {
            return handleCollision(tank, e);
          }
        }
        break;
      case RIGHT:
        for (int j = rect.getTop(); j <= rect.getBottom(); j++) {
          Entity e = gameState.getEntityAt(rect.getRight(), j);
          if (e != null && e != tank) {
            return handleCollision(tank, e);
          }
        }
        break;
      case DOWN:
        for (int i = rect.getLeft(); i <= rect.getRight(); i++) {
          Entity e = gameState.getEntityAt(i, rect.getBottom());
          if (e != null && e != tank) {
            return handleCollision(tank, e);
          }
        }
        break;
      case LEFT:
        for (int j = rect.getTop(); j <= rect.getBottom(); j++) {
          Entity e = gameState.getEntityAt(rect.getLeft(), j);
          if (e != null && e != tank) {
            return handleCollision(tank, e);
          }
        }
        break;
    }
    return null;
  }

  private Collision handleCollision(Entity s, Entity t) {
    CollisionType type = CollisionType.NonDestructive;
    if (s.getGameElement() == GameElement.BULLET && t.getGameElement() == GameElement.BASE) {
      type = CollisionType.BulletBase;
    } else if (s.getGameElement() == GameElement.TANK && t.getGameElement() == GameElement.BASE) {
      type = CollisionType.TankBase;
    } else if (s.getGameElement() == GameElement.BULLET && t.getGameElement() == GameElement.TANK) {
      type = CollisionType.BulletTank;
    } else if (s.getGameElement() == GameElement.TANK && t.getGameElement() == GameElement.BULLET) {
      type = CollisionType.TankBullet;
    } else if (s.getGameElement() == GameElement.BULLET && t.getGameElement() == GameElement.BULLET) {
      type = CollisionType.BulletBullet;
    }
    return new Collision(s, t, type);
  }

  public boolean canMoveInDirection(Tank tank, Direction dir) {
    int x = tank.getX();
    int y = tank.getY();
    switch (dir) {
      case UP:
        return gameState.canTankBeMovedTo(tank, x, y - 1);
      case RIGHT:
        return gameState.canTankBeMovedTo(tank, x + 1, y);
      case DOWN:
        return gameState.canTankBeMovedTo(tank, x, y + 1);
      case LEFT:
        return gameState.canTankBeMovedTo(tank, x - 1, y);
    }
    return false;
  }
}
