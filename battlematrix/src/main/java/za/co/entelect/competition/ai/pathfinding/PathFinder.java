package za.co.entelect.competition.ai.pathfinding;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.domain.*;

import java.util.*;

public class PathFinder {

  private static Logger logger = Logger.getLogger(PathFinder.class);

  private GameModel gameModel;
  private Tank tank;

  public PathFinder(GameModel gameModel, Tank tank) {
    this.tank = tank;
    this.gameModel = gameModel;
  }

  public Stack<Node> closestPathAStar(int startX, int startY, int endX, int endY, boolean closest) {
    Queue<Node> open = new PriorityQueue<>();
    Collection<Node> closed = new HashSet<>();

    Node start = new Node(startX, startY, gameModel.getEntityAt(startX, startY));
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

  private Collection<Node> getAvailableNeighbors(Node node) {
    Collection<Node> neighbors = new ArrayList<>();

    int x = node.x;
    int y = node.y;

    for (int i = x - 1; i <= x + 1; i++) {
      for (int j = y - 1; j <= y + 1; j++) {
        if (i == x && j == y) {
          continue;
        }
        ifCanMoveToAdd(i, j, neighbors);
      }
    }
    return neighbors;
  }

  private boolean ifCanMoveToAdd(int x, int y, Collection<Node> neighbors) {
    boolean canMoveTo = gameModel.canTankBeMovedTo(tank, x, y);
    if (canMoveTo) {
      neighbors.add(new Node(x, y, gameModel.getEntityAt(x, y)));
    }
    return canMoveTo;
  }

  public int heuristic(PathFinder.Node node, int endX, int endY) {
    int dist = Util.manhattanDist(node.x, node.y, endX, endY);
    return dist;
  }

  private Stack<Node> pathToNode(Node currentNode) {
    Stack<Node> path = new Stack<>();
    do {
      path.add(currentNode);
      currentNode = currentNode.parent;
    } while (currentNode != null);
    return path;
  }

  public class Node implements Trackable, Comparable<Node> {
    int x;
    int y;
    int goalCost;
    int runningCost;
    Entity entity;

    Node parent;

    public Node(int x, int y, Entity entity) {
      this.x = x;
      this.y = y;
      this.entity = entity;
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
    public int getW() {
      return 1;
    }

    @Override
    public int getH() {
      return 1;
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
      sb.append(", entity=").append(entity);
      sb.append('}');
      return sb.toString();
    }
  }
}
