package za.co.entelect.competition.planning;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.pathfinding.PathFinderGoal;
import za.co.entelect.competition.ai.planning.*;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Ids;
import za.co.entelect.competition.domain.Point;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.groovy.GameFactory;

import javax.script.ScriptException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

@RunWith(JUnit4.class)
public class GoapTest {

  private GameState gameStateBasic;
  private File testDir;

  @Before
  public void setup() throws ScriptException, NoSuchMethodException {
    gameStateBasic = GameFactory.fromFile("/map_basic.groovy");
    testDir = new File("/tmp/goap_test");
    if (testDir.exists()) {
      testDir.delete();
    }
    testDir.mkdirs();
  }

  @Test
  public void testGoalDestroyEnemyBase() {
    Tank tank = (Tank)gameStateBasic.getEntity(Ids.Y1);
    int targetX = tank.getX() + 10;
    int targetY = tank.getY() + 5;
    tank.getBlackboard().setTargetLocation(new Point(targetX, targetY));
    tank.getBlackboard().setTargetId(Ids.OPPONENT_BASE);
    Collection<Action> actions = new ArrayList<>();

    Stack<PathFinder.Node> movePath = PathFinder.closestPathAStar(gameStateBasic, tank, targetX, targetY, false);
    if (movePath != null) {
      actions.add(new ActionMoveTo(gameStateBasic, tank));
      actions.add(new ActionDestroyTarget(gameStateBasic, tank));
      actions.add(new ActionLookAtTarget(gameStateBasic, tank));
      actions.add(new ActionAlignToTarget(gameStateBasic, tank));
    }
    Goal goal = new GoalDestroyEnemyBase(Ids.OPPONENT_BASE);
    long start = System.currentTimeMillis();
    Plan plan = PathFinderGoal.getPlan(gameStateBasic.toGameModel(), goal, actions);
    System.out.println("Plan took [" + (System.currentTimeMillis() - start) + "ms]");
    assertNotNull(plan);
    String dot = Util.toDot(plan);
    Util.writeFile(testDir, "testGoalDestroyEnemyBase.dot", dot);
    System.out.println(dot);
  }

  @Test
  public void testGoalMoveTo() {
    Tank tank = (Tank)gameStateBasic.getEntity(Ids.Y1);
    int targetX = tank.getX() + 10;
    int targetY = tank.getY() + 5;
    tank.getBlackboard().setTargetLocation(new Point(targetX, targetY));
    Collection<Action> actions = new ArrayList<>();

    Stack<PathFinder.Node> movePath = PathFinder.closestPathAStar(gameStateBasic, tank, targetX, targetY, false);
    if (movePath != null) {
      actions.add(new ActionMoveTo(gameStateBasic, tank));
    }
    Goal goal = new GoalMoveTo(tank.getId(), targetX, targetY);
    long start = System.currentTimeMillis();
    Plan plan = PathFinderGoal.getPlan(gameStateBasic.toGameModel(), goal, actions);
    System.out.println("Plan took [" + (System.currentTimeMillis() - start) + "ms]");
    assertNotNull(plan);
    String dot = Util.toDot(plan);
    Util.writeFile(testDir, "testGoalMoveTo.dot", dot);
    System.out.println(dot);
  }

  @Test
  public void testGoalDestroyEnemyBaseNoValidPlan() {
    Tank tank = (Tank)gameStateBasic.getEntity(Ids.Y1);
    int targetX = tank.getX() + 10;
    int targetY = tank.getY() + 5;
    Collection<Action> actions = new ArrayList<>();

    Stack<PathFinder.Node> movePath = PathFinder.closestPathAStar(gameStateBasic, tank, targetX, targetY, false);
    if (movePath != null) {
      actions.add(new ActionMoveTo(gameStateBasic, tank));
    }
    Goal goal = new GoalDestroyEnemyBase(Ids.OPPONENT_BASE);
    long start = System.currentTimeMillis();
    Stack<PathFinderGoal.Node> path = PathFinderGoal.closestPathAStar(gameStateBasic.toGameModel(), goal, actions);
    System.out.println("Exaustive search took [" + (System.currentTimeMillis() - start) + "ms]");
    assertNull(path);
  }
}
