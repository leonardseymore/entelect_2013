package za.co.entelect.competition.domain;

import java.util.Arrays;

public class Walls {

  private int w;
  private int h;
  private int walls[][];

  public Walls(int w, int h) {
    this.w = w;
    this.h = h;
    this.walls = new int[w][h];
  }

  public Walls(int w, int h, int[][] walls) {
    this.w = w;
    this.h = h;
    this.walls = walls;
  }

  public int[][] getWalls() {
    return walls;
  }

  public boolean hasWall(int x, int y) {
    return walls[x][y] == 1;
  }

  public void destroyWalls(int x, int y, Direction direction) {
    if (walls[x][y] == 1) {
      walls[x][y] = 0;
      if (direction == Direction.UP || direction == Direction.DOWN) {
        if (destroyIfNeighborWall(x - 1, y)) {
          destroyIfNeighborWall(x - 2, y);
        }
        if (destroyIfNeighborWall(x + 1, y)) {
          destroyIfNeighborWall(x + 2, y);
        }
      } else {
        if (destroyIfNeighborWall(x, y - 1)) {
          destroyIfNeighborWall(x, y - 2);
        }
        if (destroyIfNeighborWall(x, y + 1)) {
          destroyIfNeighborWall(x, y + 2);
        }
      }
    }
  }

  private boolean destroyIfNeighborWall(int x, int y) {
    if (!isInBounds(x, y)) {
      return false;
    }

    if (walls[x][y] == 1) {
      walls[x][y] = 0;
      return true;
    }
    return false;
  }

  private boolean isInBounds(int x, int y) {
    return x >= 0 && x < w && y >= 0 && y < h;
  }

  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    for (int j = 0; j < h; j++) {
      for (int i = 0; i < w; i++) {
        buffer.append(walls[i][j] + " ");
      }
      buffer.append("\n");
    }
    return buffer.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Walls)) return false;
    Walls owall = (Walls)o;
    for (int i = 0; i < w; i++) {
      int[] wall = walls[i];
      int[] owalls = owall.getWalls()[i];
      if (!Arrays.equals(wall, owalls)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return walls.hashCode();
  }
}
