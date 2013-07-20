package za.co.entelect.competition.ai.planning;

import za.co.entelect.competition.domain.GameState;

public interface GoapIdaHeuristic {
  int estimate(GameState gameState);
}
