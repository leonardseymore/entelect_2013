package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.domain.GameState;

import java.util.*;

public class GoapIda {

  public Action planAction(GameState gameState, Goal goal, int maxDepth) {
    int cutoff = heuristic(gameState);
    TranspositionTable transpositionTable = new TranspositionTable();
    while (cutoff >= 0) {
      DFSResult result = doDepthFirst(gameState, goal, transpositionTable, maxDepth, cutoff);
      cutoff = result.cutoff;
      Action action = result.action;

      if (action != null) {
        return action;
      }
    }
    return null;
  }

  private DFSResult doDepthFirst(GameState gameState, Goal goal, TranspositionTable transpositionTable, int maxDepth, int cutoff) {
    GameState[] models = new GameState[maxDepth + 1];
    Action[] actions = new Action[maxDepth];
    int[] costs = new int[maxDepth];

    models[0] = gameState;
    int currentDepth = 0;
    int smallestCutoff = Integer.MAX_VALUE;

    while (currentDepth >= 0 ) {
      if (goal.isFulfilled(models[currentDepth])) {
        return new DFSResult(cutoff, actions[0]);
      }

      if (currentDepth >= maxDepth) {
        currentDepth--;
        continue;
      }

      int cost = heuristic(models[currentDepth]) + costs[currentDepth];
      if (cost > cutoff) {
        if (cutoff < smallestCutoff) {
          smallestCutoff = cutoff;
        }
        currentDepth--;
        continue;
      }

      Action nextAction = models[currentDepth].nextAction();
      if (nextAction != null) {
        models[currentDepth + 1] = models[currentDepth].clone();
        actions[currentDepth] = nextAction;
        models[currentDepth + 1].applyAction(nextAction);
        costs[currentDepth + 1] = costs[currentDepth] + nextAction.getCost();

        if (!transpositionTable.has(models[currentDepth + 1])) {
          currentDepth++;
          transpositionTable.add(models[currentDepth + 1], currentDepth);
        }
      } else {
        currentDepth--;
      }
    }
    return new DFSResult(smallestCutoff, null);
  }

  private int heuristic(GameState gameState) {
    return 0;
  }

  private static class TranspositionTable {
    Map<Long, TranspositionTableEntry> map = new HashMap<>();

    public boolean has(GameState gameState) {
      return map.containsKey(gameState.hash());
    }

    public void add(GameState gameState, int depth) {
      long hash = gameState.hash();
      if (map.containsKey(hash)) {
        TranspositionTableEntry entry = map.get(hash);
        if (entry.gameState == gameState) {
          if (depth < entry.depth) {
            entry.depth = depth;
          }
        } else {
          if (depth < entry.depth) {
            entry.gameState = gameState;
            entry.depth = depth;
          }
        }
      } else {
        map.put(hash, new TranspositionTableEntry(gameState, depth));
      }
    }
  }

  private static class TranspositionTableEntry {
    GameState gameState;
    int depth = Integer.MAX_VALUE;

    private TranspositionTableEntry(GameState gameState, int depth) {
      this.gameState = gameState;
      this.depth = depth;
    }
  }

  private static class DFSResult {
    int cutoff;
    Action action;

    private DFSResult(int cutoff, Action action) {
      this.cutoff = cutoff;
      this.action = action;
    }
  }
}
