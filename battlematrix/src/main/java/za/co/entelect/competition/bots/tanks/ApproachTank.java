package za.co.entelect.competition.bots.tanks;

import za.co.entelect.competition.bots.behavior.Approach;
import za.co.entelect.competition.bots.pathfinding.PathFinder;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Player;
import za.co.entelect.competition.domain.Tank;

import java.util.Stack;

public class ApproachTank/* extends Tank */{

  /*
  private Approach behavior;

  public ApproachTank(TankId id, Approach behavior) {
    super(id);
    this.behavior = behavior;
  } */

  /*
  public void setFollowTank(Tank target) {
    behavior = new Approach(this, target, 0);
  }

  @Override
  public TankAction doGetAction(GameState gameState) {
    if (behavior != null) {
      return behavior.getAction();
    }
    return TankAction.NONE;
  }

  public Stack<PathFinder.Node> getPath() {
    if (behavior != null) {
      return behavior.getPath();
    }
    return null;
  }
  */
}
