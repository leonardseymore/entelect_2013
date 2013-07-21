package za.co.entelect.competition.ai.pathfinding;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.planning.Action;
import za.co.entelect.competition.ai.planning.GameModel;
import za.co.entelect.competition.ai.planning.GameModelProp;
import za.co.entelect.competition.ai.planning.Goal;
import za.co.entelect.competition.domain.Entity;
import za.co.entelect.competition.domain.GameState;
import za.co.entelect.competition.domain.Tank;
import za.co.entelect.competition.domain.Trackable;

import java.util.*;

public class PathFinderGoal {

  public static Queue<Node> closestPathAStar(GameModel startState, Goal goal, Collection<Action> actions) {
    Queue<Node> open = new PriorityQueue<>();
    Collection<Node> closed = new HashSet<>();

    GameModel requiredState = goal.requiredState();
    Node start = new Node(startState);
    start.goalCost = heuristic(requiredState, startState);
    open.add(start);

    Node closestNode = start;

    while (!open.isEmpty()) {
      Node currentNode = open.poll();

      if (currentNode.goalCost < closestNode.goalCost) {
        closestNode = currentNode;
      }

      if (requiredState.isSatisfiedBy(currentNode.gameModel)) {
        return pathToNode(currentNode);
      }

      open.remove(currentNode);
      closed.add(currentNode);
      for (Node toNode : getAvailableNeighbors(requiredState, currentNode, actions)) {
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

  private static Collection<Node> getAvailableNeighbors(GameModel requiredState, Node node, Collection<Action> actions) {
    Collection<Node> neighbors = new ArrayList<>();
    for (Action action : actions) {
      if (node.action == action) {
        continue;
      }
      GameModel newModel = node.getGameModel().clone();
      for (GameModelProp gameModelProp : action.getPreconditions()) {
        requiredState.addProp(gameModelProp);
      }
      for (GameModelProp gameModelProp : action.getEffects()) {
        newModel.addProp(gameModelProp);
      }
      Node neighbor = new Node(newModel, action);
      neighbor.parent = node;
      neighbor.goalCost = action.getCost();
      neighbors.add(neighbor);

    }
    return neighbors;
  }

  public static int heuristic(GameModel requiredState, GameModel nodeState) {
    int numUnsatisfiedProps = 0;
    for (GameModelProp gameModelProp : requiredState.getProps()) {
      Object value = nodeState.getProp(gameModelProp.key);
      if (!gameModelProp.value.equals(value)) {
        numUnsatisfiedProps++;
      }
    }
    return numUnsatisfiedProps;
  }

  private static Queue<Node> pathToNode(Node currentNode) {
    Queue<Node> path = new LinkedList<>();
    do {
      path.add(currentNode);
      currentNode = currentNode.parent;
    } while (currentNode != null);
    return path;
  }

  public static class Node implements Comparable<Node> {
    int goalCost;
    int runningCost;
    Action action;
    GameModel gameModel;

    Node parent;

    public Node(GameModel gameModel) {
      this.gameModel = gameModel;
    }

    public Node(GameModel gameModel, Action action) {
      this.gameModel = gameModel;
      this.action = action;
    }

    public GameModel getGameModel() {
      return gameModel;
    }

    public Action getAction() {
      return action;
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

      if (gameModel != null ? !gameModel.equals(node.gameModel) : node.gameModel != null) return false;

      return true;
    }

    @Override
    public int hashCode() {
      return gameModel != null ? gameModel.hashCode() : 0;
    }
  }
}