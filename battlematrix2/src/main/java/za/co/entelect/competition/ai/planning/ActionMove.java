package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.domain.Direction;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.TankAction;

import java.util.Collection;

public class ActionMove extends Action {

  private Tank tank;
  private int x;
  private int y;
  private Direction direction;

  public ActionMove(Tank tank, Direction direction) {
    this.tank = tank;
    this.direction = direction;
    switch (direction) {
      case UP:
        this.x = tank.getX();
        this.y = tank.getY() - 1;
        break;
      case RIGHT:
        this.x = tank.getX() + 1;
        this.y = tank.getY();
        break;
      case DOWN:
        this.x = tank.getX();
        this.y = tank.getY() + 1;
        break;
      case LEFT:
        this.x = tank.getX() - 1;
        this.y = tank.getY();
        break;
    }
    this.expiryTime = 1;
  }

  @Override
  public void doExecute(GameState gameState) {
    Tank clone = (Tank)gameState.getEntity(tank.getId());
    switch (direction) {
      case UP:
        clone.setNextAction(TankAction.UP);
        break;
      case RIGHT:
        clone.setNextAction(TankAction.RIGHT);
        break;
      case DOWN:
        clone.setNextAction(TankAction.DOWN);
        break;
      case LEFT:
        clone.setNextAction(TankAction.LEFT);
        break;
    }
  }

  @Override
  public int getCost() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Collection<GameModelProp> getPreconditions() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Collection<GameModelProp> getEffects() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String getName() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public boolean isComplete(GameState gameState) {
    return tank.getX() == x && tank.getY() == y;
  }

  public String getDescription() {
    return tank.getId() + " " + direction.name();// + " to (" + x +":" + y + ")";
  }
}
