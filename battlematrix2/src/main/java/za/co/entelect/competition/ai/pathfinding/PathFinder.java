package za.co.entelect.competition.ai.pathfinding;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.domain.*;

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
      for (Node toNode : getAvailableNeighbors(gameState, tank, currentNode, endX, endY)) {
        int goalCost = currentNode.goalCost;

        // its cheaper to keep moving in the same direction than to rotate
        if (toNode.y < currentNode.getY()) {
          toNode.tankAction = TankAction.UP;
          toNode.direction = Direction.UP;
          if (toNode.direction != Direction.UP) {
            toNode.y = currentNode.getY();
            goalCost += 1;
          }
        }
        if (toNode.x > currentNode.getX()) {
          toNode.tankAction = TankAction.RIGHT;
          toNode.direction = Direction.RIGHT;
          if (toNode.direction != Direction.RIGHT) {
            toNode.x = currentNode.getX();
            goalCost += 1;
          }
        }
        if (toNode.y > currentNode.getY()) {
          toNode.tankAction = TankAction.DOWN;
          toNode.direction = Direction.DOWN;
          if (toNode.direction != Direction.DOWN) {
            toNode.y = currentNode.getY();
            goalCost += 1;
          }
        }
        if (toNode.x < currentNode.getX()) {
          toNode.tankAction = TankAction.LEFT;
          toNode.direction = Direction.LEFT;
          if (toNode.direction != Direction.LEFT) {
            toNode.x = currentNode.getX();
            goalCost += 1;
          }
        }

        int estGoalCost = goalCost + heuristic(toNode, endX, endY);
        if (closed.contains(toNode)) {
          if (toNode.runningCost > estGoalCost) {
            closed.remove(toNode);
            toNode.parent = currentNode;
          } else {
            continue;
          }
        }

        if (!open.contains(toNode)) {
          toNode.runningCost = estGoalCost;
          open.add(toNode);
        } else {
          if (toNode.runningCost < estGoalCost) {
            toNode.runningCost = estGoalCost;
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

  private static Collection<Node> getAvailableNeighbors(GameState gameState, Tank tank, Node node, int endX, int endY) {
    Collection<Node> neighbors = new ArrayList<>();
    testNeighbor(gameState, tank, node, endX, endY, node.getX(), node.getY() - 1, neighbors);
    testNeighbor(gameState, tank, node, endX, endY, node.getX() + 1, node.getY(), neighbors);
    testNeighbor(gameState, tank, node, endX, endY, node.getX(), node.getY() + 1, neighbors);
    testNeighbor(gameState, tank, node, endX, endY, node.getX() - 1, node.getY(), neighbors);
    return neighbors;
  }

  private static void testNeighbor(GameState gameState, Tank tank, Node node, int endX, int endY, int x, int y, Collection<Node> neighbors) {
    boolean canMoveTo = gameState.canTankBeMovedTo(tank, x, y);
    if (canMoveTo) {
      Node toNode = new Node(x, y);
      toNode.parent = node;
      neighbors.add(toNode);
    }
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
    TankAction tankAction;
    Direction direction;

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

    public TankAction getTankAction() {
      return tankAction;
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
