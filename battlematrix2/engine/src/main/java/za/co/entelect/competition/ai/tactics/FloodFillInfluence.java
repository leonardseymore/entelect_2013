package za.co.entelect.competition.ai.tactics;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.domain.*;

import java.util.*;

public class FloodFillInfluence {

  public static Collection<Node> getInfluence(GameState gameState, Tank tank) {
    Queue<Node> open = new PriorityQueue<>();
    open.add(new Node(tank.getX(), tank.getY()));
    Collection<Node> closed = new HashSet<>();
    while (!open.isEmpty()) {
      Node currentNode = open.poll();
      for (Node node : getAvailableNeighbors(gameState, tank, currentNode)) {
        int estRunningCost = currentNode.runningCost + node.runningCost;
        if (closed.contains(node)) {
          assert node.runningCost <= estRunningCost;
          continue;
        } else if (open.contains(node)) {
          if (node.runningCost <= estRunningCost) {
            continue;
          }
        } else {
          node.runningCost = estRunningCost;
          if (!open.contains(node)) {
            open.add(node);
          }
        }
      }
      open.remove(currentNode);
      closed.add(currentNode);
    }
    return closed;
  }

  private static Collection<Node> getAvailableNeighbors(GameState gameState, Tank tank, Node node) {
    Collection<Node> neighbors = new ArrayList<>();
    int x = node.getX();
    int y = node.getY();
    testNeighbor(gameState, tank, node, x, y - 1, neighbors);
    testNeighbor(gameState, tank, node, x + 1, y, neighbors);
    testNeighbor(gameState, tank, node, x, y + 1, neighbors);
    testNeighbor(gameState, tank, node, x - 1, y, neighbors);
    return neighbors;
  }

  private static void testNeighbor(GameState gameState, Tank tank, Node node, int x, int y, Collection<Node> neighbors) {
    boolean canMoveTo = canTankBeMovedTo(gameState, tank, x, y);
    if (canMoveTo) {
      Node toNode = new Node(x, y);
      toNode.parent = node;
      toNode.runningCost = 1;
      neighbors.add(toNode);
    }
  }

  private static boolean canTankBeMovedTo(GameState gameState, Tank tank, int x, int y) {
    Walls walls = gameState.getWalls();
    for (int j = y - Constants.TANK_HALF_SIZE; j <= y + Constants.TANK_HALF_SIZE; j++) {
      for (int i = x - Constants.TANK_HALF_SIZE; i <= x + Constants.TANK_HALF_SIZE; i++) {
        if (!gameState.isInBounds(i, j)) {
          return false;
        }
        if (walls.hasWall(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  public static class Node implements Comparable<Node> {
    int x;
    int y;
    int runningCost;
    Node parent;

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public void setX(int x) {
      this.x = x;
    }

    public int getY() {
      return y;
    }

    public void setY(int y) {
      this.y = y;
    }

    public int getRunningCost() {
      return runningCost;
    }

    @Override
    public int compareTo(Node o) {
      return runningCost > o.runningCost ? +1 : runningCost < o.runningCost ? -1 : 0;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Node)) return false;

      Node node = (Node) o;

      if (x != node.x) return false;
      if (y != node.y) return false;

      return true;
    }

    @Override
    public int hashCode() {
      int result = x;
      result = 31 * result + y;
      return result;
    }

    @Override
    public String toString() {
      final StringBuilder sb = new StringBuilder("{");
      sb.append("x=").append(x);
      sb.append(", y=").append(y);
      sb.append(", runningCost=").append(runningCost);
      sb.append('}');
      return sb.toString();
    }
  }
}
