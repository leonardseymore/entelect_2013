package za.co.entelect.competition.bots;

import za.co.entelect.competition.bots.behavior.Approach;
import za.co.entelect.competition.bots.movement.PathFinder;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Player;
import za.co.entelect.competition.domain.Tank;

import java.util.Stack;

public class ApproachTank extends Tank {

  private Approach behavior;

  public ApproachTank(String name, int x, int y, GameState gameState, Player owner, Direction direction) {
    super(name, x, y, gameState, owner, direction);
  }

  public void setFollowTank(Tank target) {
    behavior = new Approach(this, target, 0);
  }

  @Override
  public TankAction doGetAction() {
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
}
