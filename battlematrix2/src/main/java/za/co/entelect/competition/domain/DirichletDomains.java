package za.co.entelect.competition.domain;

import za.co.entelect.competition.Util;

public class DirichletDomains {

  private GameState gameState;
  private Base[][] dirichletDomains;

  public DirichletDomains(GameState gameState) {
    this.gameState = gameState;
    this.dirichletDomains = new Base[gameState.getW()][gameState.getH()];
  }

  public Base getBase(int x, int y) {
    return dirichletDomains[x][y];
  }

  public void regenerate() {
    long start = System.currentTimeMillis();

    int w = gameState.getW();
    int h = gameState.getH();
    Base ybase = gameState.getYourBase();
    Base obase = gameState.getOpponentBase();
    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {
        int disty = Util.manhattanDist(i, j, ybase.getX(), ybase.getY());
        int disto = Util.manhattanDist(i, j, obase.getX(), obase.getY());
        if (disty > disto) {
          dirichletDomains[i][j] = ybase;
        } else if (disty < disto) {
          dirichletDomains[i][j] = obase;
        }
      }
    }
    //logger.debug("Dirichlet domain generation took [" + (System.currentTimeMillis() - start) + "ms]");
  }
}
