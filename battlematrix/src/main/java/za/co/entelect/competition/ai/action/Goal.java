package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.domain.GameState;

public interface Goal {

  boolean isFulfilled(GameState gameState);
}
