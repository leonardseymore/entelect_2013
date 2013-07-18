package za.co.entelect.competition.ai.tankoperator;

import za.co.entelect.competition.ai.action.ActionFireTank;
import za.co.entelect.competition.ai.action.ActionManager;
import za.co.entelect.competition.ai.action.ActionMoveTank;
import za.co.entelect.competition.domain.*;

import java.util.Random;

public class RandomTankOperator implements TankOperator {

  private Random random = new Random(System.currentTimeMillis());

  @Override
  public void execute(GameState gameState, Tank tank) {
    switch (random.nextInt(20)) {
      case 1:
        ActionManager.getInstance().scheduleAction(new ActionMoveTank(tank, Directed.Direction.UP));
      case 2:
        ActionManager.getInstance().scheduleAction(new ActionMoveTank(tank, Directed.Direction.RIGHT));
      case 3:
        ActionManager.getInstance().scheduleAction(new ActionMoveTank(tank, Directed.Direction.DOWN));
      case 4:
        ActionManager.getInstance().scheduleAction(new ActionMoveTank(tank, Directed.Direction.LEFT));
      case 5:
        ActionManager.getInstance().scheduleAction(new ActionFireTank(tank));
    }
  }
}
