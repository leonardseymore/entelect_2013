package za.co.entelect.competition.bots.pathfinding;

import za.co.entelect.competition.Util;

public class HeuristicDistance implements Heuristic {
  @Override
  public int heuristic(PathFinder.Node node, int endX, int endY) {
    int dist = Util.manhattanDist(node.x, node.y, endX, endY);
    return dist;
  }
}
