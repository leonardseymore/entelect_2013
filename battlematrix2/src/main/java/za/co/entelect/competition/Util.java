package za.co.entelect.competition;

import za.co.entelect.competition.ai.pathfinding.PathFinderGoal;
import za.co.entelect.competition.ai.planning.goap.Action;
import za.co.entelect.competition.ai.planning.goap.GameModelProp;
import za.co.entelect.competition.ai.planning.goap.Goal;
import za.co.entelect.competition.ai.planning.goap.Plan;
import za.co.entelect.competition.domain.*;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class Util {

  public static int manhattanDist(int startX, int startY, int endX, int endY) {
    return Math.abs(startX - endX) + Math.abs(startY - endY);
  }

  public static Color getColor(Entity entity) {
    switch (entity.getGameElement()) {
      case BASE:
        Base base = (Base)entity;
        return base.isYourBase() ? Constants.COLOR_SWING_TANK_YOU : Constants.COLOR_SWING_TANK_OPPONENT;
      case BULLET:
        return Constants.COLOR_SWING_BULLET;
      case TANK:
        Tank tank = (Tank)entity;
        Color tankColor = tank.isYourTank() ? Constants.COLOR_SWING_TANK_YOU : Constants.COLOR_SWING_TANK_OPPONENT;
        return tankColor;
      case WALL:
        return Constants.COLOR_SWING_WALL;
    }
    return Constants.COLOR_SWING_BLANK;
  }

  public static String toPpm(GameState gameState) {
    int w = gameState.getW();
    int h = gameState.getH();
    StringBuilder buffer = new StringBuilder();
    buffer.append("P3\n");
    buffer.append(w + " " + h + "\n");
    buffer.append("255\n");
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        GameElement e = gameState.getElementAt(x, y);
        if (e == null) {
          buffer.append(Constants.COLOR_PPM3_BLANK);
        } else {
          switch (e) {
            case BASE:
              buffer.append(Constants.COLOR_PPM3_BASE);
              break;
            case BULLET:
              buffer.append(Constants.COLOR_PPM3_BULLET);
              break;
            case TANK:
              Tank tank = (Tank)gameState.getEntityAt(x, y);
              buffer.append(tank.isYourTank() ? Constants.COLOR_PPM3_TANK_YOU : Constants.COLOR_PPM3_TANK_OPPONENT);
              break;
            case WALL:
              buffer.append(Constants.COLOR_PPM3_WALL);
              break;
          }
        }
        buffer.append(" ");
      }
      buffer.append("\n");
    }
    return buffer.toString();
  }

  public static String toDot(Plan plan) {
    StringBuilder dot = new StringBuilder();

    dot.append("digraph GoalMoveTo {\n");
    dot.append(" edge [fontsize=8];\n");
    Stack<PathFinderGoal.Node> steps = plan.getSteps();
    for (PathFinderGoal.Node node : steps) {
      Action action = node.getAction();
      if (action != null) {
        dot.append(action.getName());
        dot.append(" -> ");
      }
    }
    Goal goal = plan.getGoal();
    dot.append(goal.getName() + "\n");
    dot.append(goal.getName() + " [shape=\"box\", label=<" + goalToTable(goal) + ">]\n");
    for (PathFinderGoal.Node node : steps) {
      Action action = node.getAction();
      if (action != null) {
        dot.append(action.getName() + " [label=<" + actionToTable(action) + ">]\n");
      }
    }
    dot.append("}");
    return dot.toString();
  }

  private static String goalToTable(Goal goal) {
    StringBuilder buffer = new StringBuilder();
    buffer.append("<table>");
    buffer.append("<tr><td>" + goal.getName() + "</td><td>Key</td><td>Value</td></tr>");
    for (GameModelProp gameModelProp : goal.requiredState().getProps()) {
      buffer.append("<tr><td>Requires</td><td>" + gameModelProp.key + "</td><td>" + gameModelProp.value + "</td></tr>");
    }
    buffer.append("</table>");
    return buffer.toString();
  }

  private static String actionToTable(Action action) {
    StringBuilder buffer = new StringBuilder();
    buffer.append("<table>");
    buffer.append("<tr><td>" + action.getName() + "</td><td>Key</td><td>Value</td></tr>");
    for (GameModelProp gameModelProp : action.getEffects()) {
      buffer.append("<tr><td>Effect</td><td>" + gameModelProp.key + "</td><td>" + gameModelProp.value + "</td></tr>");
    }
    for (GameModelProp gameModelProp : action.getPreconditions()) {
      buffer.append("<tr><td>Precondition</td><td>" + gameModelProp.key + "</td><td>" + gameModelProp.value + "</td></tr>");
    }
    buffer.append("</table>");
    return buffer.toString();
  }

  public static void writeFile(File dir, String filename, String contents) {
    try (BufferedWriter out = new BufferedWriter(new FileWriter(new File(dir, filename)))) {
      out.write(contents.toString());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
