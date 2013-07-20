package za.co.entelect.competition.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class MapTest {

  private int w;
  private int h;
  private Map map;

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
    map = new Map(w, h, walls);
  }

  @Test
  public void testDestroyWallsFromTop() {
    System.out.println("Destroy walls from top.");
    map.destroyWalls(2, 0, Direction.DOWN);
    System.out.println("New Map:");
    System.out.println(map);

    int[][] walls = {
      {0, 1, 1, 1, 1},
      {0, 1, 1, 1, 1},
      {0, 1, 1, 1, 1},
      {0, 1, 1, 1, 1},
      {0, 1, 1, 1, 1}
    };
    System.out.println("Expected Map:");
    Map expectedMap = new Map(w, h, walls);
    System.out.println(expectedMap);

    assertEquals(map, expectedMap);
  }

  @Test
  public void testDestroyWallsFromRight() {
    System.out.println("Destroy walls from right.");
    map.destroyWalls(4, 2, Direction.LEFT);
    System.out.println("New Map:");
    System.out.println(map);

    int[][] walls = {
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {0, 0, 0, 0, 0}
    };
    System.out.println("Expected Map:");
    Map expectedMap = new Map(w, h, walls);
    System.out.println(expectedMap);

    assertEquals(map, expectedMap);
  }

  @Test
  public void testDestroyWallsFromBottom() {
    System.out.println("Destroy walls from bottom.");
    map.destroyWalls(2, 4, Direction.UP);
    System.out.println("New Map:");
    System.out.println(map);

    int[][] walls = {
      {1, 1, 1, 1, 0},
      {1, 1, 1, 1, 0},
      {1, 1, 1, 1, 0},
      {1, 1, 1, 1, 0},
      {1, 1, 1, 1, 0},
    };
    System.out.println("Expected Map:");
    Map expectedMap = new Map(w, h, walls);
    System.out.println(expectedMap);

    assertEquals(map, expectedMap);
  }

  @Test
  public void testDestroyWallsFromLeft() {
    System.out.println("Destroy walls from left.");
    map.destroyWalls(0, 2, Direction.RIGHT);
    System.out.println("New Map:");
    System.out.println(map);

    int[][] walls = {
      {0, 0, 0, 0, 0},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
    };
    System.out.println("Expected Map:");
    Map expectedMap = new Map(w, h, walls);
    System.out.println(expectedMap);

    assertEquals(map, expectedMap);
  }

  @Test
  public void testDestroyWallsFromLeftOffCenter() {
    System.out.println("Destroy walls from left off-center.");
    map.destroyWalls(0, 3, Direction.RIGHT);
    System.out.println("New Map:");
    System.out.println(map);

    int[][] walls = {
      {1, 0, 0, 0, 0},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
      {1, 1, 1, 1, 1},
    };
    System.out.println("Expected Map:");
    Map expectedMap = new Map(w, h, walls);
    System.out.println(expectedMap);

    assertEquals(map, expectedMap);
  }
}
