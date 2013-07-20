package za.co.entelect.competition.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class WallsTest {

  private int w;
  private int h;
  private Walls walls;

  @Before
  public void setup() {
    w = 5;
    h = 5;
    int[][] walls = {
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
    };
    this.walls = new Walls(w, h, walls);
  }

  @Test
  public void testDestroyWallsFromTop() {
    System.out.println("Destroy walls from top.");
    walls.destroyWalls(2, 0, Direction.DOWN);
    System.out.println("New Walls:");
    System.out.println(walls);

    int[][] walls = {
      {0, 1, 1, 1, 1},
      {0, 1, 1, 1, 1},
      {0, 1, 1, 1, 1},
      {0, 1, 1, 1, 1},
      {0, 1, 1, 1, 1}
    };
    System.out.println("Expected Walls:");
    Walls expectedWalls = new Walls(w, h, walls);
    System.out.println(expectedWalls);

    assertEquals(this.walls, expectedWalls);
  }

  @Test
  public void testDestroyWallsFromRight() {
    System.out.println("Destroy walls from right.");
    walls.destroyWalls(4, 2, Direction.LEFT);
    System.out.println("New Walls:");
    System.out.println(walls);

    int[][] walls = {
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {0, 0, 0, 0, 0}
    };
    System.out.println("Expected Walls:");
    Walls expectedWalls = new Walls(w, h, walls);
    System.out.println(expectedWalls);

    assertEquals(this.walls, expectedWalls);
  }

  @Test
  public void testDestroyWallsFromBottom() {
    System.out.println("Destroy walls from bottom.");
    walls.destroyWalls(2, 4, Direction.UP);
    System.out.println("New Walls:");
    System.out.println(walls);

    int[][] walls = {
      {1, 1, 1, 1, 0},
      {1, 1, 1, 1, 0},
      {1, 1, 1, 1, 0},
      {1, 1, 1, 1, 0},
      {1, 1, 1, 1, 0},
    };
    System.out.println("Expected Walls:");
    Walls expectedWalls = new Walls(w, h, walls);
    System.out.println(expectedWalls);

    assertEquals(this.walls, expectedWalls);
  }

  @Test
  public void testDestroyWallsFromLeft() {
    System.out.println("Destroy walls from left.");
    walls.destroyWalls(0, 2, Direction.RIGHT);
    System.out.println("New Walls:");
    System.out.println(walls);

    int[][] walls = {
      {0, 0, 0, 0, 0},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
    };
    System.out.println("Expected Walls:");
    Walls expectedWalls = new Walls(w, h, walls);
    System.out.println(expectedWalls);

    assertEquals(this.walls, expectedWalls);
  }

  @Test
  public void testDestroyWallsFromLeftOffCenter() {
    System.out.println("Destroy walls from left off-center.");
    walls.destroyWalls(0, 3, Direction.RIGHT);
    System.out.println("New Walls:");
    System.out.println(walls);

    int[][] walls = {
      {1, 0, 0, 0, 0},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
    };
    System.out.println("Expected Walls:");
    Walls expectedWalls = new Walls(w, h, walls);
    System.out.println(expectedWalls);

    assertEquals(this.walls, expectedWalls);
  }
}
