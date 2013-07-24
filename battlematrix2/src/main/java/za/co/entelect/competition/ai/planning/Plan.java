package za.co.entelect.competition.ai.planning;

import org.apache.log4j.Logger;
import za.co.entelect.competition.ai.pathfinding.PathFinderGoal;
import za.co.entelect.competition.domain.GameState;

import java.util.Stack;

public class Plan {

  private static final Logger logger = Logger.getLogger(Plan.class);

  private Stack<PathFinderGoal.Node> steps;
  private PathFinderGoal.Node currentStep;

  public Plan(Stack<PathFinderGoal.Node> steps) {
    this.steps = steps;
    currentStep = steps.pop();
  }

  public boolean isValid(GameState gameState) {
    if (currentStep == null) {
      return false;
    }
    return currentStep.getAction().isValid(gameState);
  }

  public void run(GameState gameState) {
    Action action = currentStep.getAction();
    //logger.debug("Executing step "+ action.getName());
    if (action.isComplete(gameState)) {
      if (steps.isEmpty()) {
        currentStep = null;
      } else {
        currentStep = steps.pop();
      }
    } else {
      action.execute(gameState);
    }
  }

  public boolean isComplete() {
    return currentStep == null;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Plan{");
    sb.append("steps=").append(steps);
    sb.append(", currentStep=").append(currentStep);
    sb.append('}');
    return sb.toString();
  }
}
