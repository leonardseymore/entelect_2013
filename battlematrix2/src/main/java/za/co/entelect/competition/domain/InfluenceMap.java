package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.pathfinding.PathFinder;
import za.co.entelect.competition.ai.tactics.FloodFillInfluence;

import java.util.Collection;

public class InfluenceMap {

  private static final Logger logger = Logger.getLogger(InfluenceMap.class);

  private static final float TANK_INFLUENCE = 20f;
  private static final float BULLET_INFLUENCE = 8f;

  private int w;
  private int h;
  private GameState gameState;
  private float[][] yInfluenceMap;
  private float[][] oInfluenceMap;
  private float[][] influenceYMap;
  private float[][] influenceOMap;

  public InfluenceMap(GameState gameState) {
    this.gameState = gameState;
    w = gameState.getW();
    h = gameState.getH();
    yInfluenceMap = new float[w][h];
    oInfluenceMap = new float[w][h];
    influenceYMap = new float[w][h];
    influenceOMap = new float[w][h];
  }

  public float[][] getyInfluenceMap() {
    return yInfluenceMap;
  }

  public float[][] getoInfluenceMap() {
    return oInfluenceMap;
  }

  public float[][] getInfluenceYMap() {
    return influenceYMap;
  }

  public float[][] getInfluenceOMap() {
    return influenceOMap;
  }

  public float[][] getInfluenceMap(Player player) {
    return player == Player.YOU ? getInfluenceYMap() : getInfluenceOMap();
  }

  public void update() {
    long start = System.currentTimeMillis();
    // decay old values
    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {
        yInfluenceMap[i][j] = 0;
        oInfluenceMap[i][j] = 0;
      }
    }
    // apply new influence
    for (Tank tank : gameState.getTanks().values()) {
      Collection<FloodFillInfluence.Node> influence = FloodFillInfluence.getInfluence(gameState, tank);
      for (FloodFillInfluence.Node node : influence) {
        if (tank.isYourTank()) {
          yInfluenceMap[node.getX()][node.getY()] += TANK_INFLUENCE / node.getRunningCost();
        } else {
          oInfluenceMap[node.getX()][node.getY()] += TANK_INFLUENCE / node.getRunningCost();
        }
      }
    }
    for (Bullet bullet : gameState.getBullets().values()) {
      inf: for (int i = 1; i < BULLET_INFLUENCE; i++) {
        float influence = BULLET_INFLUENCE / i;
        int x = bullet.getX();
        int y = bullet.getY();
        int target;
        switch (bullet.getDirection()) {
          case UP:
            target = y - i;
            if (!gameState.isInBounds(x, target) || gameState.getElementAt(x, target) != null) {
              break inf;
            }

            if (bullet.isYourBullet()) {
              yInfluenceMap[x][target] += influence;
            } else {
              oInfluenceMap[x][target] += influence;
            }
            break;
          case RIGHT:
            target = x + i;
            if (!gameState.isInBounds(target, y) || gameState.getElementAt(target, y) != null) {
              break inf;
            }

            if (bullet.isYourBullet()) {
              yInfluenceMap[target][y] += influence;
            } else {
              oInfluenceMap[target][y] += influence;
            }
            break;
          case DOWN:
            target = y + i;
            if (!gameState.isInBounds(x, target) || gameState.getElementAt(x, target) != null) {
              break inf;
            }

            if (bullet.isYourBullet()) {
              yInfluenceMap[x][target] += influence;
            } else {
              oInfluenceMap[x][target] += influence;
            }
            break;
          case LEFT:
            target = x - i;
            if (!gameState.isInBounds(target, y) || gameState.getElementAt(target, y) != null) {
              break inf;
            }

            if (bullet.isYourBullet()) {
              yInfluenceMap[target][y] += influence;
            } else {
              oInfluenceMap[target][y] += influence;
            }
            break;
        }
      }
    }

    // calculate influence maps
    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {
        influenceYMap[i][j] = yInfluenceMap[i][j] - oInfluenceMap[i][j];
        influenceOMap[i][j] = oInfluenceMap[i][j] - yInfluenceMap[i][j];
      }
    }
    //logger.debug("Influence map generation took [" + (System.currentTimeMillis() - start) + "ms]");
  }

  public float[][] getEnemyInfluence(Tank tank) {
    return tank.isYourTank() ? getoInfluenceMap() : getyInfluenceMap();
  }
}
