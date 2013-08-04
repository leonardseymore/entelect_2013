package za.co.entelect.competition.ai.tankoperator;

import za.co.entelect.competition.domain.Trackable;

import java.util.Stack;

public interface PathAware {

  Stack<Trackable> getPath();
}
