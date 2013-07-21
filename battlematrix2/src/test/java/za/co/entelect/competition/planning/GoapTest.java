package za.co.entelect.competition.planning;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.pathfinding.PathFinderGoal;
import za.co.entelect.competition.ai.planning.*;
import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.groovy.GameFactory;

import javax.script.ScriptException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
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
    StringBuilder dot = new StringBuilder();
    dot.append("digraph GoapIda {\n");
    dot.append(" edge [fontsize=8];\n");
    Tank tank = (Tank)gameStateBasic.getEntity(Ids.Y1);
    int targetX = tank.getX() + 10;
    int targetY = tank.getY() + 5;
    Collection<Action> actions = new ArrayList<>();

    Stack<PathFinder.Node> movePath = PathFinder.closestPathAStar(gameStateBasic, tank, targetX, targetY, false);
    if (movePath != null) {
      actions.add(new ActionMoveToX(Ids.Y1, targetX, movePath));
      actions.add(new ActionDestroyEnemyBaseFire(Ids.Y1, Ids.OPPONENT_BASE, targetX, targetY));
      actions.add(new ActionMoveTo(Ids.Y1, targetX, targetY, movePath));
      actions.add(new ActionReload(Ids.Y1));
    }
    Goal goal = new GoalDestroyEnemyBase(Ids.OPPONENT_BASE);
    long start = System.currentTimeMillis();
    Queue<PathFinderGoal.Node> path = PathFinderGoal.closestPathAStar(new GameModel(), goal, actions);
    System.out.println("Plan took [" + (System.currentTimeMillis() - start) + "ms]");
    assertNotNull(path);
    for (PathFinderGoal.Node node : path) {
      Action action = node.getAction();
      if (action != null) {
        dot.append(action.getName());
        dot.append(" -> ");
      }
    }
    dot.append(goal.getName() + "\n");
    dot.append(goal.getName() + " [shape=\"box\", label=<" + goalToTable(goal) + ">]\n");
    for (PathFinderGoal.Node node : path) {
      Action action = node.getAction();
      if (action != null) {
        dot.append(action.getName() + " [label=<" + actionToTable(action) + ">]\n");
      }
    }
    dot.append("}");
    writeFile("testGoalDestroyEnemyBase.dot", dot.toString());
    System.out.println(dot.toString());
  }

  @Test
  public void testGoalDestroyEnemyBaseNoValidPlan() {
    Tank tank = (Tank)gameStateBasic.getEntity(Ids.Y1);
    int targetX = tank.getX() + 10;
    int targetY = tank.getY() + 5;
    Collection<Action> actions = new ArrayList<>();

    Stack<PathFinder.Node> movePath = PathFinder.closestPathAStar(gameStateBasic, tank, targetX, targetY, false);
    if (movePath != null) {
      actions.add(new ActionMoveToX(Ids.Y1, targetX, movePath));
      actions.add(new ActionDestroyEnemyBaseFire(Ids.Y1, Ids.OPPONENT_BASE, targetX, targetY));
      actions.add(new ActionMoveTo(Ids.Y1, targetX, targetY, movePath));
    }
    Goal goal = new GoalDestroyEnemyBase(Ids.OPPONENT_BASE);
    long start = System.currentTimeMillis();
    Queue<PathFinderGoal.Node> path = PathFinderGoal.closestPathAStar(new GameModel(), goal, actions);
    System.out.println("Exaustive search took [" + (System.currentTimeMillis() - start) + "ms]");
    assertNull(path);
  }

  private String goalToTable(Goal goal) {
    StringBuilder buffer = new StringBuilder();
    buffer.append("<table>");
    buffer.append("<tr><td>" + goal.getName() + "</td><td>Key</td><td>Value</td></tr>");
    for (GameModelProp gameModelProp : goal.requiredState().getProps()) {
      buffer.append("<tr><td>Requires</td><td>" + gameModelProp.key + "</td><td>" + gameModelProp.value + "</td></tr>");
    }
    buffer.append("</table>");
    return buffer.toString();
  }

  private String actionToTable(Action action) {
    StringBuilder buffer = new StringBuilder();
    buffer.append("<table>");
    buffer.append("<tr><td>" + action.getName() + "</td><td>Key</td><td>Value</td></tr>");
    for (GameModelProp gameModelProp : action.getEffects()) {
      buffer.append("<tr><td>Effect</td><td>" + gameModelProp.key + "</td><td>" + gameModelProp.value + "</td></tr>");
    }
    for (GameModelProp gameModelProp : action.getPreconditions()) {
      buffer.append("<tr><td>Precondition</td><td>" + gameModelProp.key + "</td><td>" + gameModelProp.value + "</td></tr>");
    }
    buffer.append("</table>");
    return buffer.toString();
  }

  private void writeFile(String filename, String contents) {
    try (BufferedWriter out = new BufferedWriter(new FileWriter(new File(testDir, filename)))) {
      out.write(contents.toString());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
