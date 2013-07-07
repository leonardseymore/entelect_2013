package za.co.entelect.competition.bots.movement;

import za.co.entelect.competition.domain.Entity;
import za.co.entelect.competition.domain.GameState;

import java.util.*;


public class PathFinder {

  // http://www.policyalmanac.org/games/aStarTutorial.htm
  public static Stack<Node> closestPathAStar(GameState gameState, int startX, int startY, int endX, int endY) {
    Queue<Node> open = new PriorityQueue<>();
    Collection<Node> closed = new ArrayList<>();

    Node start = new Node(startX, startY);
    start.goalCost = manhattanDist(start.x, start.y, endX, endY);
    open.add(start);

    while (!open.isEmpty()) {
      Node currentNode = open.poll();

      if (currentNode.x == endX && currentNode.y == endY) {
        return pathToNode(currentNode);
      }

      open.remove(currentNode);
      closed.add(currentNode);
      for (Node toNode : getAvailableNeighbors(gameState, currentNode)) {
        toNode.goalCost = manhattanDist(toNode.x, toNode.y, endX, endY);
        toNode.parent = currentNode;

        if (closed.contains(toNode)) {
          continue;
        }

        if (!open.contains(toNode)) {
          toNode.runningCost = currentNode.runningCost + toNode.goalCost;
          open.add(toNode);
        } else {
          if (toNode.runningCost < currentNode.runningCost + toNode.goalCost) {
            toNode.runningCost = currentNode.runningCost + toNode.goalCost;
            toNode.parent = currentNode;
          }
        }
      }
    }
    return null;
  }

  private static Collection<Node> getAvailableNeighbors(GameState gameState, Node node) {
    Collection<Node> neighbors = new ArrayList<>();

    for (int x = node.x - 1; x <= node.x + 1; x++) {
      for (int y = node.y - 1; y <= node.y + 1; y++) {
        if (x == node.x && y == node.y) {
          continue;
        }

        if (gameState.isInbounds(x, y)) {
          Entity entity = gameState.getEntityAt(x, y);
          if (entity == null || !entity.getType().equals(Entity.Type.WALL)) {
            neighbors.add(new Node(x, y));
          }
        }
      }
    }

    return neighbors;
  }

  private static Stack<Node> pathToNode(Node currentNode) {
    Stack<Node> path = new Stack<>();
    do {
      path.add(currentNode);
      currentNode = currentNode.parent;
    } while (currentNode != null);
    return path;
  }

  private static int manhattanDist(int startX, int startY, int endX, int endY) {
    return Math.abs(startX - endX) + Math.abs(startY - endY);
  }

  public static class Node implements Comparable<Node> {
    int x;
    int y;
    int goalCost;
    int runningCost;

    Node parent;

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
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
      final StringBuilder sb = new StringBuilder("Node{");
      sb.append("x=").append(x);
      sb.append(", y=").append(y);
      sb.append(", goalCost=").append(goalCost);
      sb.append(", runningCost=").append(runningCost);
      sb.append('}');
      return sb.toString();
    }
  }
}
