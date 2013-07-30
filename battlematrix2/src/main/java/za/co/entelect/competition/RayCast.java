package za.co.entelect.competition;

import za.co.entelect.competition.domain.*;

import java.awt.*;

public class RayCast {

  public static interface RayCastTest {
    public boolean test(GameElement gameElement, Entity entity);
  }

  public static class RayCaseTestTarget implements RayCastTest {

    private Entity target;

    public RayCaseTestTarget(Entity target) {
      this.target = target;
    }

    public boolean test(GameElement gameElement, Entity entity) {
      if (entity == null) {
        return false;
      } else {
        return entity.getId().equals(target.getId());
      }
    }
  }
  
  public static boolean castRay(GameState gameState, RayCastTest rayCastTest, Direction direction, int startX, int startY) {
    switch (direction) {
      case UP:
        for (int i = startY - 1; i > startY - Constants.FIRE_RANGE && i >= 0; i--) {
          GameElement gameElement = gameState.getElementAt(startX, i);
          if (gameElement != null) {
            Entity entity = gameState.getEntityAt(startX, i);
            return rayCastTest.test(gameElement, entity);
          }
        }
        break;
      case RIGHT:
        for (int i = startX + 1; i < startX + Constants.FIRE_RANGE && i < gameState.getW() - 1; i++) {
          GameElement gameElement = gameState.getElementAt(i, startY);
          if (gameElement != null) {
            Entity entity = gameState.getEntityAt(i, startY);
            return rayCastTest.test(gameElement, entity);
          }
        }
        break;
      case DOWN:
        for (int i = startY + 1; i < startY + Constants.FIRE_RANGE && i < gameState.getH() - 1; i++) {
          GameElement gameElement = gameState.getElementAt(startX, i);
          if (gameElement != null) {
            Entity entity = gameState.getEntityAt(startX, i);
            return rayCastTest.test(gameElement, entity);
          }
        }
        break;
      case LEFT:
        for (int i = startX - 1; i > startX - Constants.FIRE_RANGE && i > 0; i--) {
          GameElement gameElement = gameState.getElementAt(i, startY);
          if (gameElement != null) {
            Entity entity = gameState.getEntityAt(i, startY);
            return rayCastTest.test(gameElement, entity);
          }
        }
        break;
    }
    return false;
  }
}
