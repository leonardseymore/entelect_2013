package za.co.entelect.competition.bots.movement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import za.co.entelect.competition.domain.*;

@RunWith(JUnit4.class)
public class SeekTest {

  private GameState gameState;
  private TestTank targetTank;

  @Before
  public void setup() {
    gameState = new GameState(100, 100);
    targetTank = new TestTank("TEST", 50, 50, gameState, gameState.getYou(), Directed.Direction.UP, Tank.TankAction.NONE);
  }

  @Test
  public void test( ){

  }
}
