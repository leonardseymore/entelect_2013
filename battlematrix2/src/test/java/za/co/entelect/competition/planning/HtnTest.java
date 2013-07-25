package za.co.entelect.competition.planning;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.pathfinding.PathFinderGoal;
import za.co.entelect.competition.ai.planning.goap.*;
import za.co.entelect.competition.ai.planning.htn.Htn;
import za.co.entelect.competition.ai.planning.htn.Planner;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Ids;
import za.co.entelect.competition.domain.Point;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.groovy.GameFactory;

import javax.script.ScriptException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import static junit.framework.Assert.*;

@RunWith(JUnit4.class)
public class HtnTest {

  private GameState gameStateBasic;
  private File testDir;

  @Before
  public void setup() throws ScriptException, NoSuchMethodException {
    gameStateBasic = GameFactory.fromFile("/map_basic.groovy");
    testDir = new File("/tmp/htn_test");
    if (testDir.exists()) {
      testDir.delete();
    }
    testDir.mkdirs();
  }

  @Test
  public void testNoPlan() {
    GameModel model = gameStateBasic.toGameModel();
    model.addProp(new GameModelProp(Ids.OPPONENT_BASE, GameModelPropKey.Destroyed, true));

    Planner seq = new Planner(new GameModel(), "Set Y1 at x 1");
    seq.getEffects().add(new GameModelProp(Ids.Y1, GameModelPropKey.IsAtX, 1));

    List<Planner> plan = Htn.plan(model, seq);
    assertEquals(plan.size(), 0);
  }

  @Test
  public void testPlan() {
    GameModel model = new GameModel();
    model.addProp(new GameModelProp(Ids.Y1, GameModelPropKey.CanFire, true));
    model.addProp(new GameModelProp(Ids.OPPONENT_BASE, GameModelPropKey.IsAtY, 10));
    model.addProp(new GameModelProp(Ids.OPPONENT_BASE, GameModelPropKey.Destroyed, true));
    model.addProp(new GameModelProp(Ids.OPPONENT_BASE, GameModelPropKey.IsAtX, 1));

    Planner start = new Planner(gameStateBasic.toGameModel(), "START");
    Planner destroyBase = new Planner("DESTROY OPPONENT BASE");
    destroyBase.getEffects().add(new GameModelProp(Ids.OPPONENT_BASE, GameModelPropKey.Destroyed, true));
    start.addChild(destroyBase);
    Planner moveToX = new Planner("Move To X");
    destroyBase.getEffects().add(new GameModelProp(Ids.Y1, GameModelPropKey.IsAtX, 1));
    start.addChild(moveToX);
    Planner moveToY = new Planner("Move To Y");
    destroyBase.getEffects().add(new GameModelProp(Ids.Y1, GameModelPropKey.IsAtY, 10));
    start.addChild(moveToY);

    List<Planner> plan = Htn.plan(model, start);
    assertTrue(plan.size() > 0);

    String dot = Util.toDot(start);
    System.out.println(dot);
  }
}
