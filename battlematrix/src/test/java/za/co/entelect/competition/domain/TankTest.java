package za.co.entelect.competition.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.*;

@RunWith(JUnit4.class)
public class TankTest {

  private GameState gameState;
  private TestTank testTank;
  private Bullet testBullet;

  @Before
  public void setup() {
    gameState = new GameState(100, 100, GameState.DEFAULT_TICK_INTERVAL);
    testTank = new TestTank("TEST", 50, 50, gameState, gameState.getPlayer1(), Directed.Direction.UP, Tank.TankAction.NONE);
    int [] bulletPos = testTank.turretPos();
    testBullet = new Bullet(bulletPos[0], bulletPos[1], gameState, testTank.getOwner(), testTank.getDirection(), testTank);
  }

  @Test
  public void testMoveTankUp() {
    testTank.setAction(Tank.TankAction.UP);
    int oldY = testTank.getY();
    testTank.performAction();
    testTank.move();
    assertEquals(testTank.getY(), oldY - 1);
  }

  @Test
  public void testMoveTankRight() {
    testTank.setAction(Tank.TankAction.RIGHT);
    int oldX = testTank.getX();
    testTank.performAction();
    testTank.move();
    assertEquals(testTank.getX(), oldX + 1);
  }

  @Test
  public void testMoveTankDown() {
    testTank.setAction(Tank.TankAction.DOWN);
    int oldY = testTank.getY();
    testTank.performAction();
    testTank.move();
    assertEquals(testTank.getY(), oldY + 1);
  }

  @Test
  public void testMoveTankLeft() {
    testTank.setAction(Tank.TankAction.LEFT);
    int oldX = testTank.getX();
    testTank.performAction();
    testTank.move();
    assertEquals(testTank.getX(), oldX - 1);
  }

  @Test
  public void testMoveTankNone() {
    testTank.setAction(Tank.TankAction.LEFT);
    assertNull(testTank.getLastAction());
    testTank.performAction();
    testTank.move();
    assertEquals(testTank.getLastAction(), Tank.TankAction.LEFT);
  }

  @Test
  public void testMoveBulletUp() {
    testBullet.setDirection(Directed.Direction.UP);
    int oldY = testBullet.getY();
    testBullet.move();
    assertEquals(testBullet.getY(), oldY - 1);
  }

  @Test
  public void testMoveBulletRight() {
    testBullet.setDirection(Directed.Direction.RIGHT);
    int oldX = testBullet.getX();
    testBullet.move();
    assertEquals(testBullet.getX(), oldX + 1);
  }

  @Test
  public void testMoveBulletDown() {
    testBullet.setDirection(Directed.Direction.DOWN);
    int oldY = testBullet.getY();
    testBullet.move();
    assertEquals(testBullet.getY(), oldY + 1);
  }

  @Test
  public void testMoveBulletLeft() {
    testBullet.setDirection(Directed.Direction.LEFT);
    int oldX = testBullet.getX();
    testBullet.move();
    assertEquals(testBullet.getX(), oldX - 1);
  }
}
