package za.co.entelect.competition;

import za.co.entelect.competition.domain.*;
import za.co.entelect.competition.domain.Rectangle;

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
  
  public static boolean castRay(GameState gameState, RayCastTest rayCastTest, Direction direction, int startX, int startY, int dist) {
    switch (direction) {
      case UP:
        for (int i = startY - 1; i > startY - dist && i >= 0; i--) {
          GameElement gameElement = gameState.getElementAt(startX, i);
          if (gameElement != null) {
            Entity entity = gameState.getEntityAt(startX, i);
            return rayCastTest.test(gameElement, entity);
          }
        }
        break;
      case RIGHT:
        for (int i = startX + 1; i < startX + dist && i < gameState.getW() - 1; i++) {
          GameElement gameElement = gameState.getElementAt(i, startY);
          if (gameElement != null) {
            Entity entity = gameState.getEntityAt(i, startY);
            return rayCastTest.test(gameElement, entity);
          }
        }
        break;
      case DOWN:
        for (int i = startY + 1; i < startY + dist && i < gameState.getH() - 1; i++) {
          GameElement gameElement = gameState.getElementAt(startX, i);
          if (gameElement != null) {
            Entity entity = gameState.getEntityAt(startX, i);
            return rayCastTest.test(gameElement, entity);
          }
        }
        break;
      case LEFT:
        for (int i = startX - 1; i > startX - dist && i > 0; i--) {
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

  public static boolean castRay(GameState gameState, Rectangle rect, Direction direction, int startX, int startY, int dist) {
    switch (direction) {
      case UP:
        for (int i = startY - 1; i > startY - dist && i >= 0; i--) {
          GameElement gameElement = gameState.getElementAt(startX, i);
          if (rect.contains(startX, i)) {
            return true;
          }
          if (gameElement != null) {
            return false;
          }
        }
        break;
      case RIGHT:
        for (int i = startX + 1; i < startX + dist && i < gameState.getW() - 1; i++) {
          GameElement gameElement = gameState.getElementAt(i, startY);
          if (rect.contains(i, startY)) {
            return true;
          }
          if (gameElement != null) {
            return false;
          }
        }
        break;
      case DOWN:
        for (int i = startY + 1; i < startY + dist && i < gameState.getH() - 1; i++) {
          GameElement gameElement = gameState.getElementAt(startX, i);
          if (rect.contains(startX, i)) {
            return true;
          }
          if (gameElement != null) {
            return false;
          }
        }
        break;
      case LEFT:
        for (int i = startX - 1; i > startX - dist && i > 0; i--) {
          GameElement gameElement = gameState.getElementAt(i, startY);
          if (rect.contains(i, startY)) {
            return true;
          }
          if (gameElement != null) {
            return false;
          }
        }
        break;
    }
    return false;
  }
}
