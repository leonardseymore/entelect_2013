package za.co.entelect.competition.ai.action;

import za.co.entelect.competition.domain.GameState;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * for f in *.ppm; do convert -quality 100 $f  `basename $f .ppm`.png; done
 * dot -Tsvg -O goapida.dot
 */
public class GoapIda {

  public static Action planAction(GameState gameState, Goal goal, GoapIdaHeuristic heuristic, int maxDepth) {
    StringBuilder dot = new StringBuilder();
    dot.append("digraph GoapIda {\n");
    int cutoff = heuristic.estimate(gameState);
    TranspositionTable transpositionTable = new TranspositionTable();
    while (cutoff >= 0 && cutoff < Integer.MAX_VALUE) {
      DFSResult result = doDepthFirst(gameState, goal, heuristic, transpositionTable, maxDepth, cutoff, dot);
      cutoff = result.cutoff;
      if (result.action != null) {
        return result.action;
      }
    }
    dot.append("}");

    try (BufferedWriter out = new BufferedWriter(new FileWriter(new File(new File("/tmp/goap"), "goapida.dot")), 32768)) {
      out.write(dot.toString());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  private static DFSResult doDepthFirst(GameState gameState, Goal goal, GoapIdaHeuristic heuristic, TranspositionTable transpositionTable, int maxDepth, int cutoff, StringBuilder dot) {
    GameState[] models = new GameState[maxDepth + 1];
    Action[] actions = new Action[maxDepth + 1];
    int[] costs = new int[maxDepth + 1];

    models[0] = gameState;
    int currentDepth = 0;
    int smallestCutoff = Integer.MAX_VALUE;

    while (currentDepth >= 0) {
      if (goal.isFulfilled(models[currentDepth])) {
        return new DFSResult(cutoff, actions[0]);
      }

      if (currentDepth >= maxDepth) {
        currentDepth--;
        continue;
      }

      int cost = heuristic.estimate(models[currentDepth]) + costs[currentDepth];
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
        dot.append("  " + models[currentDepth].hash() + " -> " + models[currentDepth + 1].hash() + " [label=\"d=" + currentDepth + ", " + nextAction.getDescription() + "\"]\n");
        costs[currentDepth + 1] = costs[currentDepth] + nextAction.getCost();

        if (!transpositionTable.has(models[currentDepth + 1])) {
          String filename = "goapida-" + currentDepth + "-" + nextAction.getDescription().replaceAll(" ", "_") + ".ppm";
          String imgFilename = "goapida-" + currentDepth + "-" + nextAction.getDescription().replaceAll(" ", "_") + ".jpg";
          try (BufferedWriter out = new BufferedWriter(new FileWriter(new File(new File("/tmp/goap"), filename)))) {
            out.write(models[currentDepth + 1].toPpm());
          } catch (IOException ex) {
            ex.printStackTrace();
          }
          dot.append("  " + models[currentDepth + 1].hash() + " [shape=none, label=\"\", image=\"" + imgFilename + "\"]\n");
          gameState.toPpm();
          transpositionTable.add(models[currentDepth + 1], currentDepth);
          currentDepth++;
        }
      } else {
        currentDepth--;
      }
    }
    return new DFSResult(smallestCutoff, null);
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
