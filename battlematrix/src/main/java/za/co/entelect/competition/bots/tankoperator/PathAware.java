package za.co.entelect.competition.bots.tankoperator;

import za.co.entelect.competition.bots.pathfinding.PathFinder;

import java.util.Stack;

public interface PathAware {

  Stack<PathFinder.Node> getPath();
}
