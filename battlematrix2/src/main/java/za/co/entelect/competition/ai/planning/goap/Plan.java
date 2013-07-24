package za.co.entelect.competition.ai.planning.goap;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.pathfinding.PathFinderGoal;
import za.co.entelect.competition.domain.GameState;

import java.util.Stack;

public class Plan implements Comparable<Plan> {

  private static final Logger logger = Logger.getLogger(Plan.class);

  private Goal goal;
  private Stack<PathFinderGoal.Node> steps;
  private PathFinderGoal.Node currentStep;

  public Plan(Goal goal, Stack<PathFinderGoal.Node> steps) {
    this.goal = goal;
    this.steps = steps;
    if (!steps.isEmpty()) {
      currentStep = steps.pop();
    }
  }

  public boolean isValid(GameState gameState) {
    if (currentStep == null) {
      return false;
    }
    return currentStep.getAction().isValid(gameState);
  }

  public void run(GameState gameState) {
    boolean executed = false;
    while (!executed && currentStep != null) {
      Action action = currentStep.getAction();
      if (action.isComplete(gameState)) {
        if (steps.isEmpty()) {
          currentStep = null;
        } else {
          currentStep = steps.pop();
        }
      } else {
        action.execute(gameState);
        executed = true;
      }
    }
  }

  public boolean isComplete() {
    return currentStep == null;
  }

  public Goal getGoal() {
    return goal;
  }

  public Stack<PathFinderGoal.Node> getSteps() {
    return steps;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Plan{");
    sb.append("steps=").append(steps);
    sb.append(", currentStep=").append(currentStep);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public int compareTo(Plan o) {
    return goal.compareTo(o.goal);
  }
}
