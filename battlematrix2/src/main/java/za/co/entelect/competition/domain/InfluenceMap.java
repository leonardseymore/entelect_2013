package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Util;

public class InfluenceMap {

  private static final Logger logger = Logger.getLogger(InfluenceMap.class);

  private static final float TANK_INFLUENCE = 20f;
  private static final float BULLET_INFLUENCE = 4f;
  private static final float DECAY_RATE = 0.25f;

  private int w;
  private int h;
  private GameState gameState;
  private float[][] yInfluenceMap;
  private float[][] oInfluenceMap;
  private float[][] influenceMap;
  private float[][] tensionMap;
  private float[][] normalizedTensionMap;
  private float[][] vulnerabilityMap;

  public InfluenceMap(GameState gameState) {
    this.gameState = gameState;
    w = gameState.getW();
    h = gameState.getH();
    yInfluenceMap = new float[w][h];
    oInfluenceMap = new float[w][h];
    influenceMap = new float[w][h];
    tensionMap = new float[w][h];
    normalizedTensionMap = new float[w][h];
    vulnerabilityMap = new float[w][h];
  }

  public float[][] getyInfluenceMap() {
    return yInfluenceMap;
  }

  public float[][] getoInfluenceMap() {
    return oInfluenceMap;
  }

  public float[][] getInfluenceMap() {
    return influenceMap;
  }

  public float[][] getTensionMap() {
    return tensionMap;
  }

  public float[][] getVulnerabilityMap() {
    return vulnerabilityMap;
  }

  public void update() {
    long start = System.currentTimeMillis();
    // decay old values
    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {
        yInfluenceMap[i][j] *= DECAY_RATE;
        oInfluenceMap[i][j] *= DECAY_RATE;
      }
    }
    // apply new influence
    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {
        GameElement gameElement = gameState.getElementAt(i, j);
        if (gameElement == GameElement.WALL) {
          continue;
        }
        for (Tank tank : gameState.getTanks().values()) {
          int dist = Util.manhattanDist(i, j, tank.getX(), tank.getY());
          float influence = TANK_INFLUENCE;
          if (dist > 0) {
            influence /= dist;
          }
          if (tank.isYourTank()) {
            yInfluenceMap[i][j] += influence;
          } else {
            oInfluenceMap[i][j] += influence;
          }
        }
        for (Bullet bullet : gameState.getBullets().values()) {
          int dist = Util.manhattanDist(i, j, bullet.getX(), bullet.getY());
          float influence = BULLET_INFLUENCE;
          if (dist > 0) {
            influence /= dist;
          }
          if (bullet.isYourBullet()) {
            yInfluenceMap[i][j] += influence;
          } else {
            oInfluenceMap[i][j] += influence;
          }
        }
      }
    }
    // calculate influence maps
    float maxTension = 0;
    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {
        influenceMap[i][j] = yInfluenceMap[i][j] - oInfluenceMap[i][j];
        tensionMap[i][j] = yInfluenceMap[i][j] + oInfluenceMap[i][j];
        if (tensionMap[i][j] > maxTension) {
          maxTension = tensionMap[i][j];
        }
        vulnerabilityMap[i][j] = tensionMap[i][j] - Math.abs(influenceMap[i][j]);
      }
    }
    //logger.debug("Influence map generation took [" + (System.currentTimeMillis() - start) + "ms]");
  }
}
