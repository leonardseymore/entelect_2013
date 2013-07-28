package za.co.entelect.competition.domain;

import org.apache.log4j.Logger;
import za.co.entelect.competition.Util;
import za.co.entelect.competition.ai.pathfinding.PathFinder;

import java.util.*;

public class TacticsModel {

  private static final Logger logger = Logger.getLogger(TacticsModel.class);

  private GameState gameState;

  private Map<String, Node> nodes;

  public TacticsModel(GameState gameState) {
    this.gameState = gameState;
  }

  public Node getNode(String entityId) {
    return nodes.get(entityId);
  }

  public void update() {
    long start = System.currentTimeMillis();
    nodes = new HashMap<>();

    for (Tank yt : gameState.getYourTanks().values()) {
      for (Tank ot : gameState.getOpponentTanks().values()) {
        Node nyt = new Node(yt.getId(), yt);
        Node not = new Node(ot.getId(), ot);

        int dist = Util.manhattanDist(yt.getX(), yt.getY(), ot.getX(), ot.getY());
        nyt.dists.put(ot.getId(), dist);
        not.dists.put(yt.getId(), dist);
        Stack<PathFinder.Node> path = PathFinder.closestPathAStar(gameState, yt, ot.getX(), ot.getY(), true);
        if (path != null) {
          nyt.paths.put(ot.getId(), path);
          Stack<PathFinder.Node> reversePath = new Stack<>();
          for (PathFinder.Node node : path) {
            reversePath.push(node);
          }
          not.paths.put(yt.getId(), path);
        }
      }
    }
    //logger.debug("Tactics model generation took [" + (System.currentTimeMillis() - start) + "ms]");
  }

  private static class Node {
    private String entityId;
    private Entity entity;

    private Map<String, Stack<PathFinder.Node>> paths = new HashMap<>();
    private Map<String, Integer> dists = new HashMap<>();

    private Node(String entityId, Entity entity) {
      this.entityId = entityId;
      this.entity = entity;
    }

    private String getEntityId() {
      return entityId;
    }

    private void setEntityId(String entityId) {
      this.entityId = entityId;
    }

    private Entity getEntity() {
      return entity;
    }

    private void setEntity(Entity entity) {
      this.entity = entity;
    }

    public Map.Entry<String, Stack<PathFinder.Node>> closestThreat() {
      Map.Entry<String, Stack<PathFinder.Node>> closestThreat = null;
      Stack<PathFinder.Node> shortestPath = null;
      for (Map.Entry<String, Stack<PathFinder.Node>> pathEntry : paths.entrySet()) {
        Stack<PathFinder.Node> path = pathEntry.getValue();
        if (shortestPath == null || path.size() < shortestPath.size()) {
          closestThreat = pathEntry;
        }
      }
      return closestThreat;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Node)) return false;

      Node node = (Node) o;

      if (entityId != null ? !entityId.equals(node.entityId) : node.entityId != null) return false;

      return true;
    }

    @Override
    public int hashCode() {
      return entityId != null ? entityId.hashCode() : 0;
    }
  }
}
