package za.co.entelect.competition.bots.movement;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.domain.GameState;

import java.util.*;

public class PathFinder {

  private static Logger logger = Logger.getLogger(PathFinder.class);

  private GameState gameState;

  public PathFinder(GameState gameState) {
    this.gameState = gameState;
  }

  public Stack<Node> closestPathAStar(int startX, int startY, int endX, int endY) {
    Queue<Node> open = new PriorityQueue<>();
    Collection<Node> closed = new HashSet<>();

    Node start = new Node(startX, startY, gameState.getMapNode(startX, startY));
    start.goalCost = heuristic(startX, startY, endX, endY);
    open.add(start);

    while (!open.isEmpty()) {
      Node currentNode = open.poll();

      if (currentNode.x == endX && currentNode.y == endY) {
        return pathToNode(currentNode);
      }

      open.remove(currentNode);
      closed.add(currentNode);
      for (Node toNode : getAvailableNeighbors(currentNode)) {
        toNode.goalCost = heuristic(toNode.x, toNode.y, endX, endY);
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

  private int heuristic(int startX, int startY, int endX, int endY) {
    int dist = Util.manhattanDist(startX, startY, endX, endY);
    return dist;
  }

  private Collection<Node> getAvailableNeighbors(Node node) {
    Collection<Node> neighbors = new ArrayList<>();

    for (int x = node.x - 1; x <= node.x + 1; x++) {
      for (int y = node.y - 1; y <= node.y + 1; y++) {
        if (x == node.x && y == node.y) {
          continue;
        }

        if (gameState.isInbounds(x, y)) {
          GameState.MapNode mapNode = gameState.getMapNode(x, y);
          if (mapNode.getClearance() > 0) {
            neighbors.add(new Node(x, y, mapNode));
          }
        }
      }
    }

    return neighbors;
  }

  private Stack<Node> pathToNode(Node currentNode) {
    Stack<Node> path = new Stack<>();
    do {
      path.add(currentNode);
      currentNode = currentNode.parent;
    } while (currentNode != null);
    return path;
  }

  public class Node implements Comparable<Node> {
    int x;
    int y;
    int goalCost;
    int runningCost;
    GameState.MapNode mapNode;

    Node parent;

    public Node(int x, int y, GameState.MapNode mapNode) {
      this.x = x;
      this.y = y;
      this.mapNode = mapNode;
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
      sb.append(", mapNode=").append(mapNode);
      sb.append('}');
      return sb.toString();
    }
  }
}
