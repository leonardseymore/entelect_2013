package za.co.entelect.competition.ai.tankoperator;

import za.co.entelect.competition.ai.pathfinding.PathFinder;

import java.util.Stack;

public interface PathAware {

  Stack<PathFinder.Node> getPath();
}
