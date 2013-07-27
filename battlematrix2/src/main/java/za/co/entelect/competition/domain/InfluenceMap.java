package za.co.entelect.competition.domain;

import za.co.entelect.competition.domain.*;

public class InfluenceMap {

  private static final float BASE_INFLUENCE = 10f;
  private static final float TANK_INFLUENCE = 5f;
  private static final float BULLET_INFLUENCE = 2f;
  private static final float DECAY_RATE = 0.97f;

  private int w;
  private int h;
  private GameState gameState;
  private float[][] yInfluenceMap;
  private float[][] oInfluenceMap;
  private float[][] influenceMap;
  private float[][] tensionMap;
  private float[][] vulnerabilityMap;
  private float[][] filter = {
    {1, 4, 6, 4, 1},
    {4, 16, 24, 16, 4},
    {6, 24, 36, 24, 6},
    {4, 16, 24, 16, 4},
    {1, 4, 6, 4, 1},
  };
  float[][] ysource;
  float[][] osource;

  public InfluenceMap(GameState gameState) {
    this.gameState = gameState;
    w = gameState.getW();
    h = gameState.getH();
    yInfluenceMap = new float[w][h];
    oInfluenceMap = new float[w][h];
    influenceMap = new float[w][h];
    tensionMap = new float[w][h];
    vulnerabilityMap = new float[w][h];
    for (int j = 0; j < filter.length; j++) {
      for (int i = 0; i < filter.length; i++) {
        filter[i][j] *= 1/256f;
      }
    }

    ysource = new float[w][h];
    osource = new float[w][h];
  }

  public float[][] getyInfluenceMap() {
    return yInfluenceMap;
  }

  public float[][] getoInfluenceMap() {
    return oInfluenceMap;
  }

  public void update() {
    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {
        ysource[i][j] *= DECAY_RATE;
        osource[i][j] *= DECAY_RATE;
        GameElement gameElement = gameState.getElementAt(i, j);
        if (gameElement != null) {
          switch (gameElement) {
            case BASE:
              Base base = (Base) gameState.getEntityAt(i, j);
              if (base.isYourBase()) {
                ysource[i][j] = BASE_INFLUENCE;
              } else {
                osource[i][j] = BASE_INFLUENCE;
              }
              break;
            case TANK:
              Tank tank = (Tank) gameState.getEntityAt(i, j);
              if (tank.isYourTank()) {
                ysource[i][j] = TANK_INFLUENCE;
              } else {
                osource[i][j] = TANK_INFLUENCE;
              }
              break;
            case BULLET:
              Bullet bullet = (Bullet) gameState.getEntityAt(i, j);
              if (bullet.isYourBullet()) {
                ysource[i][j] = BULLET_INFLUENCE;
              } else {
                osource[i][j] = BULLET_INFLUENCE;
              }
              break;
          }
        }
      }
    }
    convolve(filter, ysource, yInfluenceMap);
    convolve(filter, osource, oInfluenceMap);
    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {
        ysource[i][j] = yInfluenceMap[i][j];
        osource[i][j] = oInfluenceMap[i][j];
        influenceMap[i][j] = yInfluenceMap[i][j] - oInfluenceMap[i][j];
        tensionMap[i][j] = yInfluenceMap[i][j] + oInfluenceMap[i][j];
        vulnerabilityMap[i][j] = tensionMap[i][j] - Math.abs(influenceMap[i][j]);
      }
    }
  }

  private void convolve(float[][] matrix, float[][] source, float[][] target) {
    int size = (matrix.length - 1) / 2;
    for (int j = size; j < h - size; j++) {
      for (int i = size; i < w - size; i++) {
        target[i][j] = 0;
        for (int l = 0; l < matrix.length; l++) {
          for (int k = 0; k < matrix.length; k++) {
            target[i][j] += source[i + k - size][j + l - size] * matrix[k][l];
          }
        }
      }
    }
  }
}
