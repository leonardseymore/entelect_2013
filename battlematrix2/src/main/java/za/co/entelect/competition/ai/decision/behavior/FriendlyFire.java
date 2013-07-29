package za.co.entelect.competition.ai.decision.behavior;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.ai.blackboard.Blackboard;
import za.co.entelect.competition.domain.*;

public class FriendlyFire extends Task {

  public boolean run(GameState gameState, Tank tank) {
    Direction direction = tank.getDirection();

    int x = tank.turretPos()[0];
    int y = tank.turretPos()[1];
    switch (direction) {
      case UP:
        for (int i = y - 1; i > y - Constants.FIRE_RANGE && i >= 0; i--) {
          GameElement gameElement = gameState.getElementAt(x, i);
          if (gameElement != null) {
            Entity entity = gameState.getEntityAt(x, i);
            if (entity == null) {
              return false;
            } else {
              if (entity.getGameElement() == GameElement.TANK) {
                if (((Tank)entity).getOwner() == tank.getOwner()) {
                  return true;
                }
              }
            }
          }
        }
        break;
      case RIGHT:
        for (int i = x + 1; i < x + Constants.FIRE_RANGE && i < gameState.getW() - 1; i++) {
          GameElement gameElement = gameState.getElementAt(i, y);
          if (gameElement != null) {
            Entity entity = gameState.getEntityAt(i, y);
            if (entity == null) {
              return false;
            } else {
              if (entity.getGameElement() == GameElement.TANK) {
                if (((Tank)entity).getOwner() == tank.getOwner()) {
                  return true;
                }
              }
            }
          }
        }
        break;
      case DOWN:
        for (int i = y + 1; i < y + Constants.FIRE_RANGE && i < gameState.getH() - 1; i++) {
          GameElement gameElement = gameState.getElementAt(x, i);
          if (gameElement != null) {
            Entity entity = gameState.getEntityAt(x, i);
            if (entity == null) {
              return false;
            } else {
              if (entity.getGameElement() == GameElement.TANK) {
                if (((Tank)entity).getOwner() == tank.getOwner()) {
                  return true;
                }
              }
            }
          }
        }
        break;
      case LEFT:
        for (int i = x - 1; i > x - Constants.FIRE_RANGE && i > 0; i--) {
          GameElement gameElement = gameState.getElementAt(i, y);
          if (gameElement != null) {
            Entity entity = gameState.getEntityAt(i, y);
            if (entity == null) {
              return false;
            } else {
              if (entity.getGameElement() == GameElement.TANK) {
                if (((Tank)entity).getOwner() == tank.getOwner()) {
                  return true;
                }
              }
            }
          }
        }
        break;
    }

    return false;
  }

  @Override
  protected String getLabel() {
    return "FriendlyFire?";
  }
}
