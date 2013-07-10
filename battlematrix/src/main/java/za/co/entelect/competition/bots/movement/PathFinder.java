package za.co.entelect.competition.bots.movement;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.domain.Entity;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.MapNode;

import java.util.*;

public class PathFinder {

  private static Logger logger = Logger.getLogger(PathFinder.class);

  private GameState gameState;
  private Entity entity;

  private int obstructionTypes = 1 | 2;

  public PathFinder(Entity entity, int obstructionTypes) {
    this.entity = entity;
    this.gameState = entity.getGameState();
    this.obstructionTypes = obstructionTypes;
  }

  public Stack<Node> closestPathAStar(int startX, int startY, int endX, int endY, boolean closest) {
    Queue<Node> open = new PriorityQueue<>();
    Collection<Node> closed = new HashSet<>();

    Node start = new Node(startX, startY, gameState.getMapNode(startX, startY));
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
      for (Node toNode : getAvailableNeighbors(currentNode)) {
        toNode.goalCost = heuristic(toNode, endX, endY);
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

    if (closest && closestNode != null) {
      return pathToNode(closestNode);
    }

    return null;
  }

  private int heuristic(Node node, int endX, int endY) {
    int dist = Util.manhattanDist(node.x, node.y, endX, endY);
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
          MapNode mapNode = gameState.getMapNode(x, y);
          if (mapNode.isClear()
            || mapNode.getClearanceEntity() == entity
            || (mapNode.getObstruction() & obstructionTypes) != mapNode.getObstruction()) {
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
    MapNode mapNode;

    Node parent;

    public Node(int x, int y, MapNode mapNode) {
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
