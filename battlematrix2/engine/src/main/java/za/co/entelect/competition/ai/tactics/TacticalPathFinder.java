package za.co.entelect.competition.ai.tactics;

import za.co.entelect.competition.Constants;
import za.co.entelect.competition.domain.*;

import java.util.*;

public class TacticalPathFinder {

  public static Stack<Trackable> getBestPath(GameState gameState, Tank tank, int endX, int endY) {
    Queue<Node> open = new PriorityQueue<>();
    open.add(new Node(tank.getX(), tank.getY()));
    Collection<Node> closed = new HashSet<>();
    Node currentNode = null;
    while (!open.isEmpty()) {
      currentNode = open.poll();

      if (currentNode.getX() == endX && currentNode.getY() == endY) {
        break;
      }

      for (Node node : getAvailableNeighbors(gameState, tank, currentNode)) {
        float estRunningCost = currentNode.runningCost + node.runningCost;
        if (closed.contains(node)) {
          assert node.runningCost <= estRunningCost;
          continue;
        } else if (open.contains(node)) {
          if (node.runningCost <= estRunningCost) {
            continue;
          }
        } else {
          node.runningCost = estRunningCost;
          open.add(node);
        }
      }
      open.remove(currentNode);
      closed.add(currentNode);
    }

    if (currentNode != null && currentNode.getX() == endX && currentNode.getY() == endY) {
      return pathToNode(currentNode);
    }
    return null;
  }

  private static Stack<Trackable> pathToNode(Node currentNode) {
    Stack<Trackable> path = new Stack<>();
    do {
      path.add(currentNode);
      currentNode = currentNode.parent;
    } while (currentNode != null);
    return path;
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
    if (!gameState.isInBounds(x, y)) {
      return;
    }

    DirichletDomains dirichletDomains = gameState.getDirichletDomains();
    boolean yourRegion = dirichletDomains.getOwner(x, y) == tank.getOwner();

    boolean canMoveTo = canTankBeMovedTo(gameState, tank, x, y);
    Node toNode = new Node(x, y);
    toNode.parent = node;
    toNode.runningCost = canMoveTo ? 1 : (yourRegion ? 200 : 5);

    if (node.parent != null) {
      Node grandParent = node.parent;
      if (!((grandParent.getX() == node.getX() && grandParent.getX() == x)
         || (grandParent.getY() == node.getY() && grandParent.getY() == y))) {
         toNode.runningCost += 2;
      }
    }

    InfluenceMap influenceMap = gameState.getInfluenceMap();
    float[][] enemyInfluence = influenceMap.getEnemyInfluence(tank);
    toNode.runningCost += enemyInfluence[x][y] * 20;
    neighbors.add(toNode);
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

  public static class Node implements Trackable, Comparable<Node> {
    int x;
    int y;
    float runningCost;
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

    public float getRunningCost() {
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
