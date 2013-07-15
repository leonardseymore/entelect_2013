package za.co.entelect.competition.domain;

public class GameModel {

  protected int w;
  protected int h;

  protected Entity map[][];

  public GameModel(int w, int h) {
    this.w = w;
    this.h = h;
    map = new Entity[w][h];
  }

  public boolean canTankBeMovedTo(Tank tank, int x, int y) {
    for (int j = y - Tank.TANK_HALF_SIZE; j <= y + Tank.TANK_HALF_SIZE; j++) {
      for (int i = x - Tank.TANK_HALF_SIZE; i <= x + Tank.TANK_HALF_SIZE; i++) {
        if (!isInbounds(i, j)) {
          return false;
        }

        Entity entity = getEntityAt(i, j);
        if (entity != null && entity != tank) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean isInbounds(int x, int y) {
    return x >= 0 && x < w && y >= 0 && y < h;
  }

  public Entity getEntityAt(int x, int y) {
    if (!isInbounds(x, y)) {
      return null;
    }
    return map[x][y];
  }
}
