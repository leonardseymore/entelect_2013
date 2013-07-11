package za.co.entelect.competition.bots.pathfinding;

public interface Heuristic {
  int heuristic(PathFinder.Node node, int endX, int endY);
}
