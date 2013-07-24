package za.co.entelect.competition.ai.pathfinding;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.Trackable;

import java.util.*;

public class PathFinder {

  public static Stack<Node> closestPathAStar(GameState gameState, Tank tank, int endX, int endY, boolean closest) {
    Queue<Node> open = new PriorityQueue<>();
    Collection<Node> closed = new HashSet<>();

    Node start = new Node(tank.getX(), tank.getY());
    start.goalCost = heuristic(start, endX, endY);
    open.add(start);

    Node closestNode = start;

    while (!open.isEmpty()) {
      Node currentNode = open.poll();

      if (currentNode.goalCost < closestNode.goalCost) {
        closestNode = currentNode;
      }

      if (currentNode.x == endX && currentNode.y == endY) {
        return pathToNode(currentNode);
      }

      open.remove(currentNode);
      closed.add(currentNode);
      for (Node toNode : getAvailableNeighbors(gameState, tank, currentNode)) {
        toNode.goalCost = heuristic(toNode, endX, endY);
        if (currentNode.getX() != toNode.getX()) {
          toNode.goalCost += 1;
        }
        if (currentNode.getY() != toNode.getY()) {
          toNode.goalCost += 1;
        }
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

    if (closest) {
      return pathToNode(closestNode);
    }

    return null;
  }

  private static Collection<Node> getAvailableNeighbors(GameState gameState, Tank tank, Node node) {
    Collection<Node> neighbors = new ArrayList<>();

    int x = node.x;
    int y = node.y;

    for (int i = x - 1; i <= x + 1; i++) {
      for (int j = y - 1; j <= y + 1; j++) {
        if (i == x && j == y) {
          continue;
        }
        ifCanMoveToAdd(gameState, tank, i, j, neighbors);
      }
    }
    return neighbors;
  }

  private static boolean ifCanMoveToAdd(GameState gameState, Tank tank, int x, int y, Collection<Node> neighbors) {
    boolean canMoveTo = gameState.canTankBeMovedTo(tank, x, y);
    if (canMoveTo) {
      neighbors.add(new Node(x, y));
    }
    return canMoveTo;
  }

  public static int heuristic(PathFinder.Node node, int endX, int endY) {
    int dist = Util.manhattanDist(node.x, node.y, endX, endY);
    return dist;
  }

  private static Stack<Node> pathToNode(Node currentNode) {
    Stack<Node> path = new Stack<>();
    do {
      path.add(currentNode);
      currentNode = currentNode.parent;
    } while (currentNode != null);
    return path;
  }

  public static class Node implements Trackable, Comparable<Node> {
    int x;
    int y;
    int goalCost;
    int runningCost;

    Node parent;

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public int getX() {
      return x;
    }

    @Override
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
