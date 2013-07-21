package za.co.entelect.competition.pathfinding;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.groovy.GameFactory;

import javax.script.ScriptException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

@RunWith(JUnit4.class)
public class PathFinderTest {

  private GameState gameState;
  private File testDir;

  @Before
  public void setup() throws ScriptException, NoSuchMethodException {
    gameState = GameFactory.fromFile("/map1.groovy");
    testDir = new File("/tmp/pathfinder_test");
    if (testDir.exists()) {
      testDir.delete();
    }
    testDir.mkdirs();
  }

  @Test
  public void testPathFinder() {
    Tank tank = (Tank) gameState.getEntity(Ids.Y1);
    System.out.println("Tank is at (" + tank.getX() + ":" + tank.getY() + ")");
    Stack<PathFinder.Node> path = PathFinder.closestPathAStar(gameState, tank, 50, 50, false);
    assertNotNull(path);
    System.out.println(path);
  }

  private void writeFile(String filename, String contents) {
    try (BufferedWriter out = new BufferedWriter(new FileWriter(new File(testDir, filename)))) {
      out.write(contents.toString());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
