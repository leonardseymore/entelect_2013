package za.co.entelect.competition.ai.rules;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.domain.*;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class TacticsManager {

  private GameState gameState;

  public TacticsManager(GameState gameState) {
    this.gameState = gameState;
  }

  public void setTankOrders(Strategy strategy) {
    Tank y1 = (Tank)gameState.getEntity(Ids.Y1);
    if (y1 != null) {
    }
  }
}
