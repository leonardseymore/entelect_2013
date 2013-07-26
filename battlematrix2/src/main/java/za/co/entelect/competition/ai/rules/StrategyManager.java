package za.co.entelect.competition.ai.rules;

import za.co.entelect.competition.Util;
import za.co.entelect.competition.domain.*;

import java.util.*;

public class StrategyManager {

  public Strategy getStrategy(GameState gameState) {
    Map<String, Tank> yTanks = gameState.getYourTanks();
    Map<String, Tank> oTanks = gameState.getOpponentTanks();

    Strategy strategy = null;
    if (yTanks.size() == 2 && oTanks.size() == 2) {
      strategy = getBalancedStrategy(gameState);
    } else if (yTanks.size() == 2 && oTanks.size() == 1) {
      strategy = getAggressiveStrategy(gameState);
    } else if (yTanks.size() == 2 && oTanks.size() == 0) {
      strategy = getFatalityStrategy(gameState);
    } else if (yTanks.size() == 1 && oTanks.size() == 2) {
      strategy = getOffensiveStrategy(gameState);
    } else if (yTanks.size() == 1 && oTanks.size() == 1) {
      strategy = getWearyStrategy(gameState);
    }
    return strategy;
  }

  private Strategy getBalancedStrategy(GameState gameState) {
    Map<String, Tank> yTanks = gameState.getYourTanks();
    Tank y1 = yTanks.get(Ids.Y1);
    Tank y2 = yTanks.get(Ids.Y2);
    Base obase = gameState.getOpponentBase();
    int disty1 = Util.manhattanDist(y1.getX(), y1.getY(), obase.getX(), obase.getY());
    int disty2 = Util.manhattanDist(y2.getX(), y2.getY(), obase.getX(), obase.getY());
    Directive y1Directive;
    Directive y2Directive;
    if (disty1 < disty2) {
      y1Directive = getClosestTarget(gameState, y1);
      y2Directive = Directive.DefendYourBase;
    } else {
      y1Directive = Directive.DefendYourBase;
      y2Directive = getClosestTarget(gameState, y1);
    }
    Strategy strategy = new Strategy(y1Directive, y2Directive);
    return strategy;
  }

  private Strategy getAggressiveStrategy(GameState gameState) {
    Map<String, Tank> yTanks = gameState.getYourTanks();
    Tank y1 = yTanks.get(Ids.Y1);
    Tank y2 = yTanks.get(Ids.Y2);
    Strategy strategy = new Strategy(getClosestTarget(gameState, y1), getClosestTarget(gameState, y2));
    return strategy;
  }

  private Strategy getFatalityStrategy(GameState gameState) {
    Map<String, Tank> yTanks = gameState.getYourTanks();
    Tank y1 = yTanks.get(Ids.Y1);
    Tank y2 = yTanks.get(Ids.Y2);
    Base obase = gameState.getOpponentBase();
    int disty1 = Util.manhattanDist(y1.getX(), y1.getY(), obase.getX(), obase.getY());
    int disty2 = Util.manhattanDist(y2.getX(), y2.getY(), obase.getX(), obase.getY());
    Directive y1Directive;
    Directive y2Directive;
    if (disty1 < disty2) {
      y1Directive = Directive.AttackOpponentBase;
      y2Directive = null;
    } else {
      y1Directive = null;
      y2Directive = Directive.AttackOpponentBase;
    }
    Strategy strategy = new Strategy(y1Directive, y2Directive);
    return strategy;
  }

  private Strategy getOffensiveStrategy(GameState gameState) {
    Map<String, Tank> yTanks = gameState.getYourTanks();
    Tank y1 = yTanks.get(Ids.Y1);
    Tank y2 = yTanks.get(Ids.Y2);
    Directive y1Directive = y1 == null ? null : Directive.DefendYourBase;
    Directive y2Directive = y2 == null ? null : Directive.DefendYourBase;
    Strategy strategy = new Strategy(y1Directive, y2Directive);
    return strategy;
  }

  private Strategy getWearyStrategy(GameState gameState) {
    Map<String, Tank> yTanks = gameState.getYourTanks();
    Tank y1 = yTanks.get(Ids.Y1);
    Tank y2 = yTanks.get(Ids.Y2);
    Directive y1Directive = y1 == null ? null : Directive.DefendYourBase;
    Directive y2Directive = y2 == null ? null : Directive.DefendYourBase;
    Strategy strategy = new Strategy(y1Directive, y2Directive);
    return strategy;
  }

  private Directive getClosestTarget(GameState gameState, Tank yt) {
    Map<String, Tank> oTanks = gameState.getOpponentTanks();
    Tank o1 = oTanks.get(Ids.O1);
    Tank o2 = oTanks.get(Ids.O2);
    Base obase = gameState.getOpponentBase();
    Map<Integer, Entity> dists = new HashMap<>();
    int distobase = Util.manhattanDist(yt.getX(), yt.getY(), obase.getX(), obase.getY());
    dists.put(distobase, obase);
    if (o1 != null) {
      int distot1 = Util.manhattanDist(yt.getX(), yt.getY(), o1.getX(), o1.getY());
      dists.put(distot1, o1);
    }
    if (o2 != null) {
      int distot2 = Util.manhattanDist(yt.getX(), yt.getY(), o2.getX(), o2.getY());
      dists.put(distot2, o2);
    }
    SortedSet<Integer> keys = new TreeSet<>(dists.keySet());
    Entity closestTarget = dists.get(keys.first());
    if (closestTarget == obase) {
      return Directive.AttackOpponentBase;
    } else if (closestTarget == o1) {
      return Directive.AttackO1;
    } else {
      return Directive.AttackO2;
    }
  }
}
